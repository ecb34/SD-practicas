#! bin/bash

export CLASSPATH=$CLASSPATH:~/Descargas/PRACTICA1SD/Controller/estacionCliente.jar

javac Controller.java

java -Djava.security.policy=registrar.policy $0 $1 $2
