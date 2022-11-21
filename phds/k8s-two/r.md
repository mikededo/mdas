1.a: 3 nodes  
1.b: No (1 control pane - 2 workers)  
1.c: None  

2. With the `k get configmap` command  

3.a: Principal advantage is that is easier to setup and modify + portable since
it is a simple file  
3.b: It can be created from a file (`--from-file`) or from an env file
(`--from-env-file`)
3.c: 

6: We can create a configmap from multiple files by passing the folder to the
`--from-file` param  
6.a: There are two keys with multiple values  
6.b: The output of `k describe configmaps all-configs` is:

```sh
Name:         all-configs
Namespace:    default
Labels:       <none>
Annotations:  <none>

Data
====
game.properties:
----
enemies=aliens
lives=3
enemies.cheat=true
enemies.cheat.level=noGoodRotten
secret.code.passphrase=UUDDLRLRBABAS
secret.code.allowed=true
secret.code.lives=30
ui.properties:
----
color.good=purple
color.bad=yellow
allow.textmode=true
how.nice.to.look=fairlyNice


BinaryData
====

Events:  <none>
```

7.a: There is one key with multiple values  
7.b: The output of `k describe configmaps game-config` is:

```sh
Name:         game-configs
Namespace:    default
Labels:       <none>
Annotations:  <none>

Data
====
game.properties:
----
enemies=aliens
lives=3
enemies.cheat=true
enemies.cheat.level=noGoodRotten
secret.code.passphrase=UUDDLRLRBABAS
secret.code.allowed=true
secret.code.lives=30

BinaryData
====

Events:  <none>
```

9.a: 7 variables have been added to the pod  

11.a: Yes, configmaps can be updated  
11.b: Nothing, pods will not restart when a configuration has changed  

12.
