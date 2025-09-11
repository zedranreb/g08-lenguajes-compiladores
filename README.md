# Grupo 08 - Lenguajes y Compiladores 

2do Cuatrimestre 2025
## Profesor:
* Carrizo, Daniel Nestor

## Integrantes:
* Bernardez, Juan Ignacio
* Garcia Riveros,Agustin Ignacio
* Giardelli, Alan Nahuel
* Rodriguez, Mariano Lionel
* Rodriguez Bustos, Mariano 

## Temas especiales asignados:
* triangleAreaMaximum 
* convDate

## Prerequisitos.

Java empleado:
1.  [JDK 18](https://www.oracle.com/java/technologies/javase/jdk18-archive-downloads.html)

## Herramientas:
Hemos tenido diferentes inconvenientes con los plugins del **pom.xml**
en especial con **maven-assembly-plugin**.
Sea libre de actualizar la versión a `2.5.0`

```xml
<artifactId>maven-assembly-plugin</artifactId>
<version>3.1.1</version>
```

## Compilación.

Abrir una terminal en el directorio raíz del proyecto y correr el siguiente comando:

Para Linux/Mac:
```bash
./mvnw clean install
```

Para Windows:
```powershell
./mvnw.cmd clean install
```

El mismo generará los ejecutables y correrá los tests.

## Ejecución.

Abrir una terminal en el directorio raíz del proyecto y correr el siguiente comando:

Para Linux/Mac:
```
./mvnw clean install -Drun-compiler
```

Para Windows:
```
./mvnw.cmd clean install -Drun-compiler
```

Dicho comando compilará el proyecto y luego correrá el script run.sh o run.bat (Para Unix o Windows respectivamente) presente en el directorio raíz.
Dichos scripts se pueden correr directamente desde la terminal siempre y cuando el proyecto se haya compilado primero.

## Archivo de prueba:

El mismo se encuentra en `target/input/test.txt` y es copiado del código fuente presente en `src/main/resources/input/test.txt`.
Agregar en este archivo todos los casos de prueba detallados en la consigna.

## Archivos Generados

En la carpeta target/output se generarán automáticamente los siguientes archivos pertenecientes a la entrega **1.0.0**:

- symbol-table.txt

