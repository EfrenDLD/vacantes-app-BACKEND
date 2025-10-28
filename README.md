# Vacantes App - Backend

Backend de la aplicación **Vacantes App**, desarrollado con **Java** y **Spring Boot**.  
Este servicio maneja la lógica de negocio, persistencia de datos y exposiciones de **API REST** para la gestión de vacantes y usuarios.

---

## Tecnologías

- **Java 17**  
- **Spring Boot 3.x**  
- **Spring Web (REST APIs)**  
- **Spring Data JPA**  
- **Base de datos:** H2 (desarrollo) / MySQL o PostgreSQL (producción)  
- **Maven** para gestión de dependencias  
- **Lombok** para reducir boilerplate  

## Estructura del proyecto
```
vacantes-app-backend/
│
├─ src/main/java/com/unsij/backend/vacantes_app/
│ ├─ controller/ # Endpoints REST
│ ├─ service/ # Lógica de negocio
│ ├─ model/ # Entidades y modelos
│ └─ repository/ # Acceso a base de datos
│
├─ src/main/resources/
│ ├─ application.properties # Configuración del proyecto
│
├─ src/test/java/ # Pruebas unitarias
│
└─ pom.xml # Dependencias y configuración de Maven
yaml
```

