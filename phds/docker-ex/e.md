# Ejercicio 1 - Docker

### 1.

`COPY` es un comando de docker que copia los ficheros del host (_local source_)
dentro un **contenedor**. `ADD`, sirve para copiar también los ficheros del host
pero en este caso a una imagen de Docker.

### 2.

**a)**: al no especificar la versión de la imagen, por defecto se utiliza la
`latest`.  
**b)**: se está utilizando el `entrypoint`, al pasar el parámetro
`--entrypoint`.
**c)**: creo que la opción más fácil es utilizar variables de entorno (`env`).
El uso de estas variables nos permitiría customizar al máximo la creación de la
imagen. Además, pueden tener valores predefinidos en caso de que estas variables
de entorno no sean proporcionadas. Un ejemplo de ejecución de con variables de
entorno podría ser:

```sh
# Comando previo
docker run \
  --entrypoint 'script.sh dbhost.domain.org 4242 db-name user-admin 12345678' \
  db-monitor db-tool

# Comando con variables de entorno
docker run \
  -e ENTRTYPOINT_SCRIPT='./script.sh' \
  -e DB_DOMAIN='dbhost.domain.org' \
  -e DB_PORT='4242' \
  -e DB_NAME='db-name' \
  -e DB_USER='user-admin' \
  -e DB_PASSWORD='12345678' \
  -e SERVICE_NAME=db-monitor \
  db-tool
# Cuando hay tantas variables se puede cambiar los diferentes -e por un
# --env-file con el path al fichero .env
```

**d)**: se puede utilizar el parámetro `--rm` para eliminar el contenedor
después de cada ejecución.

### 3.

El código para crear el contenedor es (se encuentra en la carpeta `e3`):

```sh
# creamos primero el volumen
docker volume create static_content
# generamos la imagen a partir del Dockerfile que se encuentra en ./e2/Dockerfile
# la imagen tendrá el nombre de nginx-one-i
docker build -t nginx-one-i .
# una vez generada la imagen, podemos ejectuar el run
docker run --rm -d \ # eliminar el cont. en stop y eje. en background
  --name nginx-one \ # nombre del contenedor
  -p 8080:80 \ # exponemos el puerto para poder acceder al cont. des del host
  -v static_content:/usr/share/nginx/html/ \ # assig. del volumen al contenedor
  nginx-one-i # finalmente la imagen del contenedor
``` 

Cuando el contenedor ya se encuentre construido, si se accede a
`http://localhost:8080` veremos el contenido del `index.html`.

### 4.

El código para crear el nuevo contenedor es prácticamente igual al anterior. El
_healthcheck_ se ha añadido al `Dockerfile`. El contenedor se levantaría con:

```sh
# generamos la image
docker build -t nginx-two-i .
# ejecutamos el run
docker rundocker run --rm -d \
  --name nginx-two \ 
  -p 8080:80 \ 
  nginx-one-i 
```

Una vez el conenedor está levantado, podemos comprobar que se está ejecutando
con la comanda `docker logs`. También podemos utilizar la comanda `docker
status` para poder ver el estado en el que se encuetra el _healthcheck_.

```sh
# output de los logs
» docker logs -f nginx-two
/docker-entrypoint.sh: /docker-entrypoint.d/ is not empty, will attempt to perform configuration
/docker-entrypoint.sh: Looking for shell scripts in /docker-entrypoint.d/
/docker-entrypoint.sh: Launching /docker-entrypoint.d/10-listen-on-ipv6-by-default.sh
10-listen-on-ipv6-by-default.sh: info: Getting the checksum of /etc/nginx/conf.d/default.conf
10-listen-on-ipv6-by-default.sh: info: Enabled listen on IPv6 in /etc/nginx/conf.d/default.conf
/docker-entrypoint.sh: Launching /docker-entrypoint.d/20-envsubst-on-templates.sh
/docker-entrypoint.sh: Launching /docker-entrypoint.d/30-tune-worker-processes.sh
/docker-entrypoint.sh: Configuration complete; ready for start up
2022/11/09 17:44:52 [notice] 1#1: using the "epoll" event method
2022/11/09 17:44:52 [notice] 1#1: nginx/1.23.2
2022/11/09 17:44:52 [notice] 1#1: built by gcc 10.2.1 20210110 (Debian 10.2.1-6) 
2022/11/09 17:44:52 [notice] 1#1: OS: Linux 5.15.49-linuxkit
2022/11/09 17:44:52 [notice] 1#1: getrlimit(RLIMIT_NOFILE): 1048576:1048576
2022/11/09 17:44:52 [notice] 1#1: start worker processes
2022/11/09 17:44:52 [notice] 1#1: start worker process 29
2022/11/09 17:44:52 [notice] 1#1: start worker process 30
2022/11/09 17:44:52 [notice] 1#1: start worker process 31
2022/11/09 17:44:52 [notice] 1#1: start worker process 32
127.0.0.1 - - [09/Nov/2022:17:45:37 +0000] "GET /index.html HTTP/1.1" 200 133 "-" "curl/7.74.0" "-"
127.0.0.1 - - [09/Nov/2022:17:46:22 +0000] "GET /index.html HTTP/1.1" 200 133 "-" "curl/7.74.0" "-"
127.0.0.1 - - [09/Nov/2022:17:47:08 +0000] "GET /index.html HTTP/1.1" 200 133 "-" "curl/7.74.0" "-"
127.0.0.1 - - [09/Nov/2022:17:47:53 +0000] "GET /index.html HTTP/1.1" 200 133 "-" "curl/7.74.0" "-"

# output para ver el status, una vez ha pasado un healthcheck
» docker inspect --format "{{ .State.Health }}" nginx-two
{healthy 0 [0x140007120a0 0x140007120f0 0x14000712140 0x14000712190]}
```

### 5.

**a)**: en mi caso he utilizado una red de tipo `bridge`, ya que de esta forma
los dos contenedores estan en la misma red local, compartiendo network y de esta
forma poder comunicarse entre ellos.
**b)**: primero es necesario ejectuar el contenedor de _elasticsearch_ y luego
ejecutar el contenedor de _kibana_, ya que sino _kibana_ no se podrá connectar a
la base de datos. Se puede conseguir con la propiedad `depends_on` del servicio
de _kibana_. Esta propiedad es una lista de nombre de servicios, que no asegura
que nuestro contenedor no se inciará hasta que los necesarios se hayan iniciado.

