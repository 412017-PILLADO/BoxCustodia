# Sistema de Gestión de Tareas - API REST

API REST desarrollada con **Spring Boot 3.3.7** para la gestión de tareas con autenticación JWT, búsqueda avanzada y KPIs.

## Características

- Autenticación JWT (implementada pero no requerida - todas las rutas son públicas)
- CRUD de Tareas (crear, editar, eliminar, consultar)
- Estados y Prioridades (flujo: TODO → DOING → DONE)
- Búsqueda avanzada con filtros y paginación
- KPIs de tareas
- Documentación Swagger/OpenAPI 3.0

## Requisitos Previos

- Java 17 o superior
- Maven 3.6+ (incluido en el proyecto con Maven Wrapper)
- MySQL 8.0 (o Docker)

---

## Inicialización del Proyecto

### Opción 1: Con Docker (Recomendado)

Docker Compose levanta automáticamente la base de datos MySQL y el backend de la aplicación.

```bash
docker-compose up --build
```

Esto iniciará:
- **MySQL 8.0** en el puerto 3306
- **Backend Spring Boot** en el puerto 8080

La aplicación estará disponible en: http://localhost:8080

### Opción 2: Sin Docker (MySQL local)

1. Crear la base de datos:

```sql
CREATE DATABASE apptecnica;
```

2. Modificar `src/main/resources/application.properties` si tu MySQL tiene configuración diferente:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/apptecnica?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=appuser
spring.datasource.password=apppass
```

3. Ejecutar la aplicación:
```bash
.\mvnw.cmd spring-boot:run
```

---

## Nota sobre JWT

El proyecto tiene JWT completamente implementado (generación de tokens, filtros, etc.), pero la configuración de seguridad está establecida en `permitAll()` para todas las rutas. Esto significa que **no es necesario enviar tokens JWT** para acceder a los endpoints.

---

## Documentación de la API

Una vez iniciada la aplicación:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

---

## Endpoints Principales

### Autenticación
- `POST /api/v1/auth/register` - Registrar usuario
- `POST /api/v1/auth/login` - Iniciar sesión (genera JWT)

### Tareas
- `GET /api/v1/tasks` - Listar tareas (paginado, filtros disponibles)
- `POST /api/v1/tasks` - Crear tarea
- `GET /api/v1/tasks/{id}` - Obtener tarea por ID
- `PUT /api/v1/tasks/{id}` - Actualizar tarea
- `DELETE /api/v1/tasks/{id}` - Eliminar tarea (soft delete)
- `PATCH /api/v1/tasks/{id}/state` - Avanzar al siguiente estado
- `GET /api/v1/tasks/kpis` - Obtener KPIs

### Usuarios
- `GET /api/v1/users` - Listar usuarios
- `DELETE /api/v1/users/{id}` - Eliminar usuario

### Búsqueda
- `GET /api/v1/search?q=término` - Búsqueda global en tareas y usuarios

### Utilidades
- `GET /api/v1/utils/tasks/priorities` - Prioridades disponibles
- `GET /api/v1/utils/tasks/states` - Estados disponibles

---

## Ejemplos de Uso

### Registrar un usuario

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Juan",
    "lastName": "Pérez",
    "email": "juan@example.com",
    "password": "password123"
  }'
```

### Crear una tarea

```bash
curl -X POST http://localhost:8080/api/v1/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Completar documentación",
    "description": "Crear README.md",
    "priority": "HIGH",
    "deadline": "2026-02-20"
  }'
```

### Listar tareas con filtros

```bash
curl "http://localhost:8080/api/v1/tasks?page=0&size=10&sort=creationDate,desc&priority=HIGH&state=TODO"
```

---

## Estructura del Proyecto

```
src/main/java/example/tecnica/
├── config/          # Configuración de seguridad y JWT
├── controller/      # Controladores REST
├── dto/             # Data Transfer Objects
├── entity/          # Entidades JPA
├── error/           # Manejo de excepciones
├── repository/      # Repositorios JPA
└── service/         # Lógica de negocio
```
