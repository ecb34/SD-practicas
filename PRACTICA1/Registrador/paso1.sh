#! bin/bash

javac InterfazRegistrador.java
export CLASSPATH=$CLASSPATH:~/Descargas/PRACTICA1SD/Registrador/estacionCliente.jar

javac Registrador.java

rmic Registrador
jar registrarCliente.jar InterfazRegistrador.class Registrador_Stub.class
