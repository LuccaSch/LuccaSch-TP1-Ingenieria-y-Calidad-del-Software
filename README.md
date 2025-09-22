# TP1 — Ingeniería y Calidad del Software V2.0.0

## A- Documentacion

### Descripción 
Este proyecto implementa Sistema Integral de Gestión de Turnos Médicos en Red de Clínicas Privadas, el sistema planteado en el Caso de Estudio de la materia Ingeniería y Calidad del Software. Su objetivo principal es demostrar buenas prácticas de desarrollo: control de versiones, trazabilidad, integración continua, mantenimiento de calidad del código, etc. También se emplea el modelo de ramas GitFlow para organizar versiones, features, correcciones, etc. 
GitHub

Contiene un sistema desarrollado mayormente en Java, con componentes de front-end (HTML, CSS, JavaScript) y back-end (Java + Maven). 
GitHub

### Estructura del proyecto
Aquí un vistazo a las carpetas y archivos principales:

- controllers: Contiene las clases que manejan las peticiones entrantes (API REST o interfaz). Se encargan de recibir las solicitudes, validarlas y delegar la lógica de negocio a los services.

- exeption: Incluye las clases de excepciones personalizadas. Sirven para manejar de forma controlada los errores que puedan ocurrir en la aplicación, facilitando mensajes claros y un mejor tratamiento de fallos.

- models: Define las entidades o clases de dominio. Representan los objetos principales del sistema (por ejemplo: usuarios, pedidos, productos), normalmente mapeados a tablas de la base de datos.

- repositories: Encapsula el acceso a los datos. Aquí se definen las interfaces y clases que se comunican con la base de datos mediante consultas o frameworks ORM.

- config: Contiene las configuraciones generales de la aplicación (parámetros de conexión, beans, seguridad, etc.). Permite centralizar la configuración para que el código sea más mantenible.

- service: Implementa la lógica de negocio de la aplicación. Actúa como intermediario entre los controllers y los repositories, procesando reglas y operaciones antes de interactuar con los datos.

- util: Almacena clases y funciones de utilidad que pueden ser reutilizadas en distintas partes del proyecto (validaciones, formateo de datos, helpers, etc.).


### Ejecucion

Para la ejecución se utilizará Docker con Docker Compose, lo que permite estandarizar el entorno de desarrollo y despliegue, asegurando que la aplicación se ejecute de la misma forma en cualquier máquina.

1- Construir y levantar los contenedores:

docker compose up --build

Este comando construirá la imagen de la aplicación y levantará tanto el servicio de Spring Boot como la base de datos configurada en docker-compose.yml.

Acceder a la aplicación:
Una vez que los contenedores estén en ejecución, la aplicación estará disponible en:

http://localhost:8080


2- Detener los contenedores:
Para detener la ejecución:

docker compose down


Persistencia de datos:
La base de datos utiliza un volumen de Docker, por lo que los datos se mantendrán incluso si los contenedores son detenidos o eliminados.

## B- Politica de Contribucion

1. Hace un fork del repositorio
2. Crea una rama para tu contribución
3. Haz tus cambios y commitea **siguiendo la convención de mensajes**
4. Subí la rama a tu fork y abrí un pull request contra `develop`

### Estándares de código
- Mantené el código legible y documentado
- Apuntá a una cobertura de tests de al menos el 70% y con tests no triviales
- No subás código roto: todo debe compilar correctamente y pasar los tests

### Pull Requests

- Relaciona el PR con el Issue correspondiente (Ejemplo: closes #123)
- Explica brevemente qué problema resuelve y qué cambios hiciste
- Incluí pruebas y ejemplos si aplica
- Tené paciencia: los PRs serán revisados por los mantenedores antes de mergearse



