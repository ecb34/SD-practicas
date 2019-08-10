#! bin/bash

javac Interfaz.java
javac Estacion.java

rmic Estacion

jar cvf estacionCliente.jar Interfaz.class Estacion_Stub.class
