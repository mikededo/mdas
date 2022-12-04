# Kubernetes

### 1

**a).** `etcd` pertenece al master node  
**b).** un ingress controller pertenece al master node  
**c).** un volumen de tipo `hostPath` pertenece al worker node  
**d).** l'API de k8s pertenece al master node  
**e).** el cluster de MongoDB consiste de un master node y sus workers, por lo
que la base de datos se encontraría repartida entre el master y sus workers  

### 2

**a).** el nodo se añade al cluster, por lo tanto, se encuentra dentro  
**b).** el registro de imágenesse encuentra fuera del cluster  
**c).** el `PersistentVolume` se encuentra dentro del cluster  
**d).** el componente de red de un service se encuentra fuera del cluster  
**e).** el volumen `emptyDir` se econtraría en el disco externo, fuera del
cluster  

### 3 

> Se entiende que `k` es un alias para `kubectl`

**a).** se puede utilizar la comanda `k get pods`  
**b).** se puede utilizar la comanda `k get services`. El código de ejemplo es el siguiente (se han utilizado los fichero de `./e3`):

```sh
» k describe services
Name:              backend-svc
Namespace:         backend-nmsp
Labels:            <none>
Annotations:       <none>
Selector:          app=nginx
Type:              ClusterIP
IP Family Policy:  SingleStack
IP Families:       IPv4
IP:                10.96.199.179
IPs:               10.96.199.179
Port:              <unset>  80/TCP
TargetPort:        8080/TCP
Endpoints:         10.244.0.5:8080,10.244.0.6:8080,10.244.0.7:8080
Session Affinity:  None
Events:            <none>
```

**c).** tenemos que validar que los pods tengan las `labels` definidas y que el
servicio tenga como target la label en cuestión. En el caso de los pods,
definiríamos la prop: `metadata.labels.<label-name>` (por ejemplo
`metadata.labels.app: backend`); luego en en el servicio definiríamos la prop:
`spec.selector.matchLabels.<label-name>` (siguiendo el ejemplo:
`spec.selector.matchLabes.app: backed`).  
**d).** supongamos que tenemos el namespace `backend-nmsp` y el servicio
levantado se encuentra en deploy se encuentra en este namespace. Entonces,
podemos utilizar el dns de este namespace para poder acceder al DNS:
`<service-name>.<namespace-name>.svc.cluster.local` donde
`service-name=backend-svc` y `namespace-name=backend-nmsp`.  
**e).** podemos utilizar el comando `kubectl scale deploy -n backend-nmsp
--replicas=1 --all` donde `backend-nmsp` es el nombre del namespace con el que
hemos configurado nuestro service.

### 4

**a).** el fichero se encuentra el la carpeta `./e4`.  
**b).** se utiliza el comando `k logs --tail=10 nginx-pod`.  
**c).** se utiliza el comando `k get pods -o=wide`. El output es: 

```sh
» k get pod -o=wide
NAME        READY   STATUS    RESTARTS   AGE    IP           NODE                 
NOMINATED NODE   READINESS GATES
nginx-pod   1/1     Running   0          5m5s   10.244.0.5   kind-control-plane   
<none>           <none>
```

**d).** se utiliza el comando `k exec --stdin --tty nginx-pod` -- /bin/bash  
**e).** la forma de hacerlo es a través del _port forwarding_ del mismo pod:

```sh
# primero obtener el port a partir del cual poder comunicarnos con el pod
kubectl get pod nginx-pod \
  --template='{{(index (index .spec.containers 0).ports 0).containerPort}}{{"\n"}}'
# en nuestro pod, obtenemos el 80

# luego ejecutamos el comando de port-forward al puerto local que queremos
# k port-forward <pod-name> <local-port>:<pod-port>
k port-forward nginx-pod 8080:80

# ejemplo output
» curl http://localhost:8080
<!DOCTYPE html>
<html>
<head>
<title>Welcome to nginx!</title>
<style>
html { color-scheme: light dark; }
body { width: 35em; margin: 0 auto;
font-family: Tahoma, Verdana, Arial, sans-serif; }
</style>
</head>
<body>
<h1>Welcome to nginx!</h1>
<p>If you see this page, the nginx web server is successfully installed and
working. Further configuration is required.</p>

<p>For online documentation and support please refer to
<a href="http://nginx.org/">nginx.org</a>.<br/>
Commercial support is available at
<a href="http://nginx.com/">nginx.com</a>.</p>

<p><em>Thank you for using nginx.</em></p>
</body>
</html>
```

**f).** el pod no podría estar en el primer nodo porqué no podría alocar más
memoria de 256M. En el caso de la cpu no habría problema ya que se toleran picos
de cpu, pero, en caso de memoria el pod pararía el proceso que está intentando
alocar la memoria y puede llegar a resetearse. En el caso del pod, necesitamos
mínimo 256, es decir, que seguramente necesitaremos más, por lo tanto
superaríamos el límite de memoria del nodo 1. Por otro lado, el nodo 2 podríamos
alocar el máximo de memoria que nos seguría sobrando el doble (máx del pod es
256M), y, aunque llegaríamos al tope de cpu, no habría problema.

### 5

**a).** los ficheros se encuentran en el directorio `e5`. Los comandos
utilizados son los siguientes:

```sh
# todos estos comandos se ejecutan des de dentro del directorio e5

# creamos el config map a partir del ficher config.properties
k create configmap config config.properties
# podemos comprovar que se ha creado correctamente con el comando
# k describe configmaps config

# el valor de los secretos tiene que estar en base64, por lo que convertimos los
# valores a base64
echo "by-backed" -n | base64
echo "9090" -n | base64

# los secretos los guardadomos en el fichero y luego lo utilizamos en el pod
# los creamos con
k apply -f secrets.yaml

# finalmente creamos el deploumeny que, en su propio fichero, utiliza los secretos y el
# configmap
k apply -f pod.yaml
```

Los secretos se encuentran en el volumen que hemos montado. Podemos comprobar
que los secretos existen en el pod con:

```sh
# accedemos al contenedor
k exec --stdin --tty http-echo-pod -- /bin/sh
# una vez dentro, hacemos un ls donde hemos montado el volumen
ls -la /etc/s-volume
total 4
drwxrwxrwt   3 root   root    140 Nov 27 16:17 .
drwxr-xr-x   1 root   root   4096 Nov 27 16:17 ..
drwxr-xr-x   2 root   root    100 Nov 27 16:17 ..2022_11_27_16_17_38.1266233344
lrwxrwxrwx   1 root   root     32 Nov 27 16:17 ..data -> ..2022_11_27_16_17_38.1266233344
lrwxrwxrwx   1 root   root     27 Nov 27 16:17 BACKEND_ACCESS_TOKEN -> ..data/BACKEND_ACCESS_TOKEN
lrwxrwxrwx   1 root   root     19 Nov 27 16:17 BACKEND_HOST -> ..data/BACKEND_HOST
lrwxrwxrwx   1 root   root     19 Nov 27 16:17 BACKEND_PORT -> ..data/BACKEND_PORT
```

**b).** los datos no sensibles los he guardado en un `configmap` mientras que los
datos no sensibles los he guardado en un secreto.  
**c).** el fichero se encuentra en `e5/service.yaml`. Podemos comprovar que el servicio es accesible siguiendo estos pasos:

```sh
# obtenemos la ip y el puerto del servicio
k get svc http-echo-service -o json | jq '.spec.clusterIP,.spect.ports[].port'
# obtenemos los nombres de los pods
# (este paso no lo he hecho ya que tengo el autocompletado activo, solo
# lo pongo para que no parezca que los nombres caen del cielo)
k get pods
# nos conectamos a una de las dos replicas del deployment
# (los nombres pueden variar)
k exec --stdin --tty http-echo-64f466db9f-z68c5 -- /bin/sh
# una vez dentro del port, accedemos al host y puerto del service
# (la ip y el puerto pueden variar)
curl 10.96.251.198:8080
# el output debería ser: hello
```

### 6

> Para poder realizar el ejercicio, he seguido cada uno de los pasos que se encuentra en la documentación de kind para poder utilizar el ingress.

**a).**

Para crear el certificado, se han seguido los siguientes pasos:

```sh
# los pasos se pueden encontrar en:
#   kubernetes.io/docs/tasks/administer-cluster/certificates
# generamos una ca.key (llave)
openssl genrsa -out ca.key 2048
# generamos un ca.cert (certificado)
openssl req -x509 -new -nodes -key ca.key -subj "/CN=my-app.domain" -days 10000 -out ca.crt
# generamos la llave del servidor que se usará en el secret
openssl genrsa -out server.key 2048
# creamos un fichero de configuración (csr.conf) con la información requerida
# se puede encontrar el fichero en e6/certificate/csr.conf
#
# generamos el signado del certificado 
openssl req -new -key server.key -out server.csr -config csr.conf
# generamos el certificado que se usará en el secret
openssl x509 -req -in server.csr -CA ca.crt -CAkey ca.key \
    -CAcreateserial -out server.crt -days 10000 \
    -extfile csr.conf -sha256
# apunte: en la documentación se añade el argumento -extensions v3_ext.
# En mi caso no lo he utilizado porqué me generaba un error y no he
# conseguido solucionarlo
```

> Todos los pasos se han ejecutado dentro de la carpeta `e6/certificate`, donde se encuentran los ficheros generados.

El siguiente paso es "popular" el cluster:

```sh
# añadimos el secret que contiene el certificado 
k create secret tls tls-secret \
  --cert ./certificate/server.crt \
  --key ./certificate/server.key
# añadimos los secretos que necesitan los pods (igual que en el ej5)
k apply -f secrets.yaml
# finalmente añadimos el ingress
k apply -f service-ingress.yaml
# he prefereido matener toda la configuración dentro de el mismo fichero para no tener que hacer
# un montón de apply, aunque supongo que debe ser más recomendable mantenerlo en varios ficheros.
# También lo he hecho de esta forma porqué he econtrado varios tutoriales/repositorios donde se 
# hace en el mismo fichero
```

Una vez tengamos el cluster _ready_, podemos ejecutar:

```sh
# en http
curl my-app.domain/users
# nos saldrá un error 308 donde se nos dice que el endpoint ha sido permanente mente redireccionado,
# en este caso a http://my-app.domain/users

# si accedemos con https
curl https://my-app.domain/users --insecure
# output: users
curl https://my-app.domain/orders --insecure
# output: orders
# si no voy equivocado se tiene que utilizar el flag or argumento --insecure para que no se haga
# la validación del certificado, ya que este se ha generado manualmente
```

Para ver el _output_ completo:

```sh
» curl https://my-app.domain/orders --insecure -v
*   Trying 127.0.0.1:443...
* Connected to my-app.domain (127.0.0.1) port 443 (#0)
* ALPN: offers h2
* ALPN: offers http/1.1
* (304) (OUT), TLS handshake, Client hello (1):
* (304) (IN), TLS handshake, Server hello (2):
* (304) (IN), TLS handshake, Unknown (8):
* (304) (IN), TLS handshake, Certificate (11):
* (304) (IN), TLS handshake, CERT verify (15):
* (304) (IN), TLS handshake, Finished (20):
* (304) (OUT), TLS handshake, Finished (20):
* SSL connection using TLSv1.3 / AEAD-AES256-GCM-SHA384
* ALPN: server accepted h2
* Server certificate:
*  subject: C=US; ST=California; L=San Fransisco; O=MyApp; OU=MyApp; CN=my-app.domain
*  start date: Dec  4 09:59:52 2022 GMT
*  expire date: Apr 21 09:59:52 2050 GMT
*  issuer: CN=my-app.domain
*  SSL certificate verify result: unable to get local issuer certificate (20), continuing anyway.
* Using HTTP2, server supports multiplexing
* Copying HTTP/2 data in stream buffer to connection buffer after upgrade: len=0
* h2h3 [:method: GET]
* h2h3 [:path: /orders]
* h2h3 [:scheme: https]
* h2h3 [:authority: my-app.domain]
* h2h3 [user-agent: curl/7.84.0]
* h2h3 [accept: */*]
* Using Stream ID: 1 (easy handle 0x11f00ce00)
> GET /orders HTTP/2
> Host: my-app.domain
> user-agent: curl/7.84.0
> accept: */*
> 
* Connection state changed (MAX_CONCURRENT_STREAMS == 128)!
< HTTP/2 200 
< date: Sun, 04 Dec 2022 10:30:55 GMT
< content-type: text/plain; charset=utf-8
< content-length: 7
< x-app-name: 
< x-app-version: 0.2.4
< strict-transport-security: max-age=15724800; includeSubDomains
< 
orders
* Connection #0 to host my-app.domain left intact
```
