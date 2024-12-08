# Cine App

## Descripción
**Cine App** es una aplicación móvil diseñada para explorar películas populares, obtener información detallada sobre cada película y realizar reservas de manera rápida y sencilla. La aplicación incluye soporte para temas claros y oscuros, aprovechando las capacidades de **Android Jetpack Compose** para la interfaz de usuario y **Retrofit** para la comunicación con la API de películas.

## Funcionalidades
- **Explorar películas populares**: Consulta una lista de las películas más populares disponibles.
- **Detalles de películas**: Accede a información completa sobre una película seleccionada, incluyendo su título, descripción e imagen.
- **Realizar reservas**: Llena un formulario con detalles personales para reservar una película.
- **Ver reservas**: Consulta una lista de todas las reservas realizadas previamente.
- **Perfil de usuario**: Visualiza y edita la información del perfil del usuario.
- **Configuración**: Cambia las preferencias de la aplicación, como el tema visual (claro u oscuro).

## Instalación
1. Clona el repositorio:
   ```bash
   git clone https://github.com/Christopher1307/cine-app.git
   ```
2. Abre el proyecto en Android Studio.
3. Sincroniza el proyecto con Gradle.
4. Ejecuta la aplicación en un emulador o en un dispositivo físico.

## Uso

### MainActivity
- **Función principal**: Actividad inicial de la aplicación.
- **Configuración de tema**: Detecta las preferencias del usuario para aplicar un tema claro u oscuro.
- **Configuración de API**: Configura el cliente Retrofit para las llamadas a la API de películas.
- **Interfaz de usuario**: Renderiza la lista de películas populares usando Jetpack Compose y un menú de navegación.

### DetailActivity
- **Función principal**: Muestra los detalles de una película seleccionada.
- **Carga de datos**: Recibe un objeto Movie mediante un Intent y muestra el título, descripción e imagen de la película.
- **Navegación**: Incluye un botón para acceder al formulario de reserva (ReservationFormActivity).

### ReservationFormActivity
- **Función principal**: Permite al usuario ingresar datos para reservar una película.
- **Interfaz de usuario**: Incluye campos de texto para:
  - Fecha
  - Hora
  - Nombre y apellidos
  - Correo electrónico
  - Fecha de nacimiento
  - Título de la película
- **Eventos interactivos**: Ofrece selectores para la fecha y hora.
- **Guardar reserva**: Almacena los datos ingresados en SharedPreferences.

### ReservationListActivity
- **Función principal**: Muestra una lista de todas las reservas realizadas.
- **Interfaz de usuario**: Usa un RecyclerView para listar las reservas.
- **Navegación**: Incluye un botón flotante para crear una nueva reserva a través de ReservationFormActivity.

### ProfileActivity
- **Función principal**: Muestra y permite editar la información del perfil del usuario.
- **Interfaz de usuario**: Incluye una barra de herramientas con opciones de navegación.

### SettingsActivity
- **Función principal**: Permite al usuario modificar la configuración de la aplicación, como el tema visual.
- **Interfaz de usuario**: Incluye una barra de herramientas con opciones para regresar a la actividad anterior.

## Clases Adicionales

### ReservationAdapter
- **Función principal**: Adaptador para el RecyclerView en ReservationListActivity.
- **Vinculación de datos**: Relaciona los datos de cada reserva con las vistas del RecyclerView.

### AuthInterceptor
- **Función principal**: Agrega el token de autorización a las solicitudes de la API de películas.

### Movie
- **Función principal**: Clase de datos que representa una película.
- **Parcelabilidad**: Implementa Parcelable para transferir objetos Movie entre actividades.

### MovieApiService
- **Función principal**: Define las llamadas a la API de películas usando Retrofit.
- **Métodos**: Incluye un método para obtener películas populares.

### MovieResponse
- **Función principal**: Clase de datos para manejar la respuesta de la API de películas.
- **Atributos**: Contiene una lista de objetos Movie.

## Contribuciones
Las contribuciones son bienvenidas. Si tienes una idea para mejorar la aplicación:

1. Abre un issue para discutir tus ideas.
2. Realiza un fork del repositorio.
3. Envía un pull request con tus cambios propuestos.

## Licencia
Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo LICENSE para más detalles.
