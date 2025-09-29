# AlquilaTusVehiculos_ServerEnjoyers

---

Aplicación **back-end** desarrollada en **Java 21** con **Spring Boot** y gestionada con **Maven**, como parte del proyecto académico del ciclo DAW/DAM (UOC).

El objetivo del sistema es implementar un servicio de alquiler de vehículos (**¡AlquilaTusVehiculos!**) en el que los usuarios puedan gestionar el alquiler de uno o varios vehículos.

El proyecto está preparado para ejecutarse de manera local o dentro de un contenedor **Docker**, asegurando portabilidad y un entorno homogéneo para todo el equipo.

---

## 🛠️ Tecnologías utilizadas

* Java 21 (JDK 21)
* Spring Boot
* Maven
* Docker / Docker Compose
* GitHub (control de versiones)

---

## 📂 Estructura básica del proyecto

```
src/main/java/com/alquilatusvehiculos/serverenjoyers
 ├─ AlquilaTusVehiculosServerEnjoyersApplication.java
 └─ controller/
     └─ HomeController.java
```

---

## ▶️ Ejecución local

### 1. Clonar el repositorio

```bash
git clone https://github.com/<tu-usuario>/<tu-repo>.git
cd <tu-repo>
```

### 2. Compilar y ejecutar con Maven

```bash
mvn spring-boot:run
```

La aplicación estará disponible en:
👉 [http://localhost:8080](http://localhost:8080)

---

## 🐳 Ejecución con Docker

### 1. Construir la imagen

```bash
docker build -t alquilatusvehiculos .
```

### 2. Ejecutar el contenedor

```bash
docker run --rm -p 8080:8080 --name alquila-app alquilatusvehiculos
```

La aplicación estará disponible en:
👉 [http://localhost:8080](http://localhost:8080)

---

## 📦 (Opcional) Ejecución con Docker Compose

Primero se debe compilar el proyecto

```bash
mvn clean install -DskipTests
```

```bash
docker compose up --build
```

👉 [http://localhost:8080](http://localhost:8080)

---

## 👨‍💻 Equipo

Este proyecto ha sido desarrollado de manera colaborativa por el equipo **Server Enjoyers**, dentro de la asignatura *Aplicación Back-end con Java en servidores de aplicaciones (FP065)* de la UOC.

---
