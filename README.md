Cine App
Descripción
Cine App es una aplicación móvil para explorar películas populares, ver detalles de películas, y realizar reservas. La aplicación soporta temas claros y oscuros y está construida utilizando Android Jetpack Compose y Retrofit para llamadas a la API.  
Funcionalidades
Explorar películas populares: Ver una lista de películas populares obtenidas de una API.
Ver detalles de películas: Ver información detallada de una película seleccionada.
Realizar reservas: Completar un formulario para reservar una película.
Ver reservas: Ver una lista de todas las reservas realizadas.
Perfil de usuario: Ver y editar la información del perfil del usuario.
Configuración: Cambiar la configuración de la aplicación, incluyendo el tema.
Instalación
Clona el repositorio:
git clone https://github.com/Christopher1307/cine-app.git
Abre el proyecto en Android Studio.
Sincroniza el proyecto con Gradle.
Ejecuta la aplicación en un emulador o dispositivo físico.
Uso
MainActivity
Función principal: Es la actividad principal que se ejecuta al iniciar la aplicación.
Configuración de tema: Lee las preferencias del usuario para aplicar el tema claro u oscuro.
Configuración de la API: Configura el cliente de Retrofit para realizar llamadas a la API de películas.
Interfaz de usuario: Utiliza Jetpack Compose para renderizar la lista de películas populares y un menú de navegación.
DetailActivity
Función principal: Muestra los detalles de una película seleccionada.
Carga de datos: Recibe un objeto Movie a través de un Intent y muestra su título, descripción e imagen.
Navegación: Incluye un botón para navegar a la actividad de formulario de reserva (ReservationFormActivity).
ReservationFormActivity
Función principal: Permite al usuario ingresar detalles para reservar una película.
Interfaz de usuario: Incluye campos de texto para la fecha, hora, nombre, apellidos, correo electrónico, fecha de nacimiento y título de la película.
Eventos: Configura diálogos para seleccionar la fecha y hora.
Guardar reserva: Guarda los detalles de la reserva en SharedPreferences.
ReservationListActivity
Función principal: Muestra una lista de todas las reservas realizadas.
Interfaz de usuario: Utiliza un RecyclerView para mostrar las reservas.
Navegación: Incluye un botón flotante para navegar a la actividad de formulario de reserva (ReservationFormActivity).
ProfileActivity
Función principal: Muestra la información del perfil del usuario.
Interfaz de usuario: Incluye una barra de herramientas con la opción de regresar a la actividad anterior.
SettingsActivity
Función principal: Permite al usuario cambiar la configuración de la aplicación.
Interfaz de usuario: Incluye una barra de herramientas con la opción de regresar a la actividad anterior.
Clases Adicionales
ReservationAdapter
Función principal: Adaptador para el RecyclerView en ReservationListActivity.
Vinculación de datos: Vincula los datos de cada reserva a las vistas correspondientes en el RecyclerView.
AuthInterceptor
Función principal: Interceptor de autenticación para agregar el token de autorización a las solicitudes de la API.
Movie
Función principal: Clase de datos que representa una película.
Parcelabilidad: Implementa Parcelable para permitir que los objetos Movie se pasen entre actividades.
MovieApiService
Función principal: Interfaz de Retrofit para definir las llamadas a la API de películas.
Métodos: Incluye un método para obtener las películas populares.
MovieResponse
Función principal: Clase de datos que representa la respuesta de la API de películas.
Atributos: Contiene una lista de objetos Movie.
Contribuciones
Las contribuciones son bienvenidas. Por favor, abre un issue o un pull request para discutir cualquier cambio que te gustaría hacer.  
Licencia
Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo LICENSE para más detalles.
