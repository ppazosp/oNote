# oNote

**oNote** es una aplicación móvil diseñada para la transcripción de clases universitarias en tiempo real, facilitando la organización y acceso a la información educativa.

## Características principales

- **Transcripción en tiempo real**: Utiliza la API de Whisper de OpenAI con ajustes personalizados para mejorar la experiencia.
- **Calendario integrado**: Permite agregar entregas y avisos sobre una asignatura para mejorar la organización.
- **Gestor de archivos**: Los usuarios pueden subir documentos y asociarlos con una clase o asignatura, permitiendo el acceso compartido.

## Tecnologías utilizadas

### **Frontend**
- Jetpack Compose en Android Studio para una interfaz moderna reactiva y nativa.

### **Backend**
- **Spring Boot Server**: API REST para la gestión de datos y conexión con MongoDB. 
  [Repositorio aquí](https://github.com/ppazosp/oNoteDB)
- **MongoDB**: Base de datos NoSQL para almacenar la información de asignaturas, transcripciones y archivos.
- **Python WebSocket Server**: Servidor transcriptor y analizador de audio mediante la API de WhisperAI.
  [Repositorio aquí](https://github.com/ppazosp/oNoteAPIs)


## Capturas de Pantalla

<table>
  <tr>
    <td align="center"><strong>Asignaturas</strong></td>
    <td align="center"><strong>Clases</strong></td>
    <td align="center"><strong>Transcripción en tiempo real</strong></td>
  </tr>
  <tr>
    <td align="center"><img src="assets/screenshots/subjects_screen.jpeg" width="300"></td>
    <td align="center"><img src="assets/screenshots/classes_screen.jpeg" width="300"></td>
    <td align="center"><img src="assets/screenshots/class_screen.jpeg" width="300"></td>
  </tr>
  <tr>
    <td align="center"><strong>Calendario de avisos</strong></td>
    <td align="center"><strong>Repositorio de archivos</strong></td>
    <td></td>
  </tr>
  <tr>
    <td align="center"><img src="assets/screenshots/calendar_screen.jpeg" width="300"></td>
    <td align="center"><img src="assets/screenshots/repo_screen.jpeg" width="300"></td>
    <td></td>
  </tr>
</table>
