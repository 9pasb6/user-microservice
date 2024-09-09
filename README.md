

# User Management Microservice

## Overview

Este microservicio gestiona los usuarios de la aplicación, proporcionando funcionalidades de autenticación y autorización mediante **JWT (JSON Web Tokens)**. Está desarrollado utilizando la **Arquitectura Hexagonal** para asegurar una clara separación de responsabilidades y una fácil mantenibilidad.

## Architecture

### Arquitectura Hexagonal

Este microservicio sigue la **Arquitectura Hexagonal** (también conocida como **Ports and Adapters**), la cual promueve la separación de la lógica de negocio de los detalles técnicos como la persistencia o los controladores web. Esto asegura que la lógica central se mantenga independiente y testeable de manera aislada.

### Capas Principales

- **Capa de Dominio**: Esta es el núcleo de la aplicación, donde reside la lógica de negocio y los modelos de dominio. El código en esta capa es puro y no depende de frameworks externos.
  - **Modelos**: `User`, `Role` representan las principales entidades del dominio.
  - **Puertos (Interfaces)**: Definen las operaciones que el dominio requiere del mundo exterior.
  - **Casos de Uso**: `UserUseCase`, `RoleUseCase` implementan las reglas de negocio y gestionan el flujo de datos entre los modelos de dominio y los componentes externos.

- **Capa de Aplicación**: Actúa como un puente entre el dominio y el mundo exterior.
  - **DTOs (Data Transfer Objects)**: Objetos usados para transferir datos entre las capas.
  - **Handlers**: `UserHandler`, `RoleHandler` manejan las solicitudes del exterior (como solicitudes HTTP) e invocan los casos de uso correspondientes.
  - **Mappers**: Convierte entre modelos de dominio y DTOs.

- **Capa de Infraestructura**: Contiene los detalles técnicos y las implementaciones específicas de la infraestructura, como la persistencia y los controladores web.
  - **Adaptadores**: Implementan los puertos de persistencia usando repositorios JPA.
  - **Entidades**: `UserEntity`, `RoleEntity` representan las entidades de la base de datos mapeadas a los modelos de dominio.
  - **Configuración**: Administra las dependencias externas, como las conexiones a bases de datos, la configuración de seguridad y los filtros JWT.

## Entidad Usuario

La entidad `User` representa a un usuario en el sistema y contiene los siguientes atributos:

```java
private Long id;
private String name;
private String lastName;
private String identityDocument;
private String phoneNumber;
private LocalDate birthDate;
private String email;
private String password;
private List<Role> roles;
```

### Descripción de Atributos

- **id**: Identificador único del usuario.
- **name**: Nombre del usuario.
- **lastName**: Apellido del usuario.
- **identityDocument**: Documento de identidad del usuario (DNI, pasaporte, etc.).
- **phoneNumber**: Número de teléfono del usuario.
- **birthDate**: Fecha de nacimiento del usuario.
- **email**: Dirección de correo electrónico del usuario.
- **password**: Contraseña encriptada del usuario.
- **roles**: Lista de roles asociados al usuario.

## JWT y Flujo de Autenticación

Este microservicio utiliza **JWT** para la autenticación y autorización. El proceso de autenticación se gestiona a través de la creación y validación de tokens JWT. A continuación, se detalla el flujo de trabajo del inicio de sesión y autenticación con JWT.

### Flujo de Trabajo de JWT y Login

1. **Solicitud de inicio de sesión (Login)**
   - El usuario envía una solicitud `POST /login` al controlador, que delega la lógica de autenticación al `UserHandler`.

2. **Validación de credenciales**
   - El `UserHandler` recibe las credenciales y utiliza el servicio de usuario (`userServicePort`) para validar el correo electrónico y la contraseña usando `BCryptPasswordEncoder`.

3. **Generación de JWT**
   - Si las credenciales son correctas, se genera un token JWT que incluye el correo electrónico y los roles del usuario. Este token se firma con una clave secreta.

4. **Respuesta con JWT**
   - El controlador devuelve el JWT junto con los detalles del usuario. El cliente usará este token en las solicitudes subsecuentes.

5. **Autenticación con JWT**
   - Las solicitudes que requieren autenticación deben incluir el JWT en el encabezado `Authorization: Bearer <token>`.

6. **Validación del token JWT**
   - El filtro de validación (`JwtValidationFilter`) intercepta las solicitudes y valida el token. Si el token es válido, se extraen los roles y se establece la autenticación en el contexto de seguridad de Spring.

7. **Acceso a recursos protegidos**
   - Si la autenticación es exitosa, se permite el acceso a los recursos protegidos basados en las políticas de autorización configuradas en `SecurityConfig`.

8. **Expiración y Seguridad del Token**
   - El token tiene una expiración definida, tras la cual el usuario deberá autenticarse nuevamente.

###Ejemplo

![image](https://github.com/user-attachments/assets/b3ef6ce9-5671-4e34-a4f6-e158ad321496)


## Testing

Las pruebas unitarias están implementadas utilizando **Mockito** para simular dependencias y asegurar la integridad de la lógica de negocio en la capa de dominio.

## Documentación

### Swagger

El microservicio incluye Swagger para la documentación de la API, permitiendo que los desarrolladores interactúen y prueben los endpoints fácilmente.

- **Swagger UI**: Puede accederse en `http://localhost:8080/swagger-ui.html`.

## Cómo Ejecutar el Proyecto

1. **Clonar el repositorio**:
   ```bash
   git clone <repository-url>
   ```

2. **Construir el proyecto**:
   ```bash
   ./mvnw clean install
   ```

3. **Ejecutar la aplicación**:
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Acceder a la documentación Swagger**:
   - Visite `http://localhost:8080/swagger-ui.html` para explorar la documentación de la API.


