#! bin/bash

export CLASSPATH=$CLASSPATH:~/Descargas/PRACTICA1SD/station/registrarCliente.jar
javac EstacionMain.java
java -Djava.security.policy=registrar.policy EstacionMain $1 $2 $3

