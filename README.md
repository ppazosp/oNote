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
- **Spring Boot (Java)**: API en Java para la gestión de datos y conexión con MongoDB.
- **MongoDB**: Base de datos NoSQL para almacenar la información de asignaturas y transcripciones.
- **Python WebSocket Server**: Servidor transcriptor de audio mediante la API de WhisperAI

## Capturas de Pantalla

### Asignaturas
<img src="assets/screenshots/subjects_screen.jpeg" width="300">

### Clases
<img src="assets/screenshots/classes_screen.jpeg" width="300">

### Transcripción en tiempo real
<img src="assets/screenshots/class_screen.jpeg" width="300">

### Calendario de avisos
<img src="assets/screenshots/calendar_screen.jpeg" width="300">

### Repositorio de archivos
<img src="assets/screenshots/repo_screen.jpeg" width="300">
