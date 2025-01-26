# PokePoke: Tu Pokédex interactiva

## Introducción
PokePoke es una aplicación Android que permite a los usuarios explorar una Pokédex interactiva, capturar Pokémon y gestionar una lista personalizada de Pokémon capturados. La aplicación se integra con la API de Pokémon y Firebase para ofrecer datos en tiempo real, además de proporcionar una experiencia de usuario visualmente atractiva y funcional.

## Características principales
- **Pokédex completa**: Muestra una lista de los primeros 150 Pokémon con sus detalles básicos.
- **Captura de Pokémon**: Permite capturar Pokémon con un simple toque y almacenarlos en Firebase.
- **Visualización personalizada**: Los Pokémon capturados se resaltan visualmente en la lista.
- **Datos detallados**: Incluye información como el peso, la altura, los tipos y la imagen oficial de cada Pokémon.
- **Interfaz moderna**: Diseño atractivo basado en colores inspirados en el universo Pokémon.

## Tecnologías utilizadas
- **Java**: Lenguaje principal para el desarrollo de la aplicación.
- **Android SDK**: Framework para desarrollar la aplicación.
- **Firebase Firestore**: Base de datos en la nube para almacenar los Pokémon capturados.
- **Retrofit**: Librería para realizar peticiones HTTP a la [API de Pokémon](https://pokeapi.co/).
- **RecyclerView y CardView**: Para mostrar listas dinámicas de Pokémon con tarjetas personalizadas.
- **Glide**: Para cargar imágenes de los Pokémon desde URLs.
- **Material Design**: Para un diseño limpio y amigable con el usuario.

## Instrucciones de uso
1. **Clonar el repositorio**:  
   ```bash
   git clone https://github.com/tu_usuario/pokepoke.git
   Abrir el proyecto: Importa el proyecto en Android Studio.
2. **Abrir el proyecto**
Importa el proyecto en Android Studio.

3. **Configurar Firebase**
1. Descarga el archivo `google-services.json` desde Firebase y colócalo en el directorio `app`.
2. Configura Firebase Firestore en tu proyecto siguiendo la [documentación oficial](https://firebase.google.com/docs/firestore).

4. **Ejecutar la app**
1. Conecta un dispositivo físico o utiliza un emulador.
2. Haz clic en **"Run"** en Android Studio para compilar y ejecutar la aplicación.

5. **Dependencias necesarias**
Todas las librerías están gestionadas a través de Gradle y se descargarán automáticamente al compilar el proyecto.

---

## Conclusiones del desarrollador
El desarrollo de PokePoke fue un desafío emocionante que permitió explorar diversas tecnologías y patrones de diseño. Algunos de los aprendizajes clave incluyen:

- La integración de Retrofit para consumir APIs REST de manera eficiente.
- La implementación de RecyclerView para mostrar listas dinámicas y visualmente atractivas.
- La gestión de datos en la nube con Firebase Firestore.
- La importancia de optimizar las solicitudes de red y el manejo de errores.

Este proyecto también destacó la importancia de estructurar el código para mantenerlo legible, reutilizable y fácil de mantener.


¡Gracias por explorar PokePoke!  
Si tienes alguna pregunta o sugerencia, no dudes en abrir un issue o contribuir al proyecto.
