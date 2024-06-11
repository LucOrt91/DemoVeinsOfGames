# API de Autenticación con Spring Boot y Spring Security

Este proyecto implementa una API de autenticación utilizando Spring Boot y Spring Security. Permite el registro de usuarios, inicio de sesión, obtención y actualización del perfil de usuario.

## Requisitos

- Java 17
- Maven
- Base de datos MySQL en Clever Cloud

## Configuración

1. Clona el repositorio desde GitHub:

    ```bash
    git clone https://github.com/LucOrt91/DemoVeinsOfGames.git
    ```

2. Abre el proyecto en tu IDE preferido.

3. Verifica que tengas configurado Java 17 en tu entorno de desarrollo.

4. Configura el archivo `application.properties` con la configuración de conexión a la base de datos MySQL proporcionada por Clever Cloud.

## Ejecución

1. Desde la terminal, navega hasta el directorio del proyecto y ejecuta el siguiente comando para compilar el proyecto:

    ```bash
    mvn clean install
    ```

2. Después de la compilación, ejecuta el siguiente comando para iniciar la aplicación:

    ```bash
    mvn spring-boot:run
    ```

3. Una vez que la aplicación esté en ejecución, puedes acceder a la documentación de la API a través de Swagger en la siguiente URL:

    ```
    http://localhost:8080/doc/swagger-ui.html
    ```

## Uso de la API

### Registro de Usuario

- **Endpoint:** `POST /auth/register`
- **Descripción:** Registra un nuevo usuario en el sistema.
- **Cuerpo de la solicitud:** Debe contener los datos del usuario a registrar.
- **Respuesta:** Retorna un mensaje de éxito o error.

### Inicio de Sesión

- **Endpoint:** `POST /auth/login`
- **Descripción:** Inicia sesión en el sistema.
- **Cuerpo de la solicitud:** Debe contener el nombre de usuario y la contraseña.
- **Respuesta:** Retorna un token JWT en caso de éxito.

### Obtener Perfil de Usuario

- **Endpoint:** `GET /user/profile`
- **Descripción:** Obtiene el perfil del usuario autenticado.
- **Encabezados:** Debe incluir el token JWT en el encabezado de la solicitud.
- **Respuesta:** Retorna el perfil del usuario o un mensaje de error si el token es inválido.

### Actualizar Perfil de Usuario

- **Endpoint:** `PUT /user/profile`
- **Descripción:** Actualiza el perfil del usuario autenticado.
- **Encabezados:** Debe incluir el token JWT en el encabezado de la solicitud.
- **Cuerpo de la solicitud:** Debe contener los datos actualizados del usuario.
- **Respuesta:** Retorna un mensaje de éxito o error.

## Pruebas

Se han incluido pruebas unitarias para los servicios utilizando JUnit 5 y Mockito. Para ejecutar las pruebas, puedes utilizar el siguiente comando:

```bash
mvn test
