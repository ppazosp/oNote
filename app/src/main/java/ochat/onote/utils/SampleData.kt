package ochat.onote.utils


import ochat.onote.data.Task
import ochat.onote.data.TaskType
import java.time.LocalDate
import java.time.LocalDateTime

val transcriptionLines = listOf(
    "Hola, bienvenidos a la transcripción en tiempo real.",
    "Esto es una prueba de cómo el texto se va añadiendo dinámicamente.",
    "La inteligencia artificial está cambiando la forma en que interactuamos con la tecnología.",
    "Hoy es un buen día para aprender Jetpack Compose.",
    "Este texto es generado en tiempo real, línea por línea.",
    "La velocidad de procesamiento es clave para una buena experiencia de usuario.",
    "La transcripción puede ser útil en conferencias, reuniones o subtítulos en vivo.",
    "¿Qué otras aplicaciones imaginas para esta tecnología?",
    "A medida que el audio se procesa, el texto sigue apareciendo aquí.",
    "Esto demuestra cómo una `LazyColumn` puede manejar grandes cantidades de datos sin problemas.",
    "Hola, bienvenidos a la transcripción en tiempo real.",
    "Esto es una prueba de cómo el texto se va añadiendo dinámicamente.",
    "La inteligencia artificial está cambiando la forma en que interactuamos con la tecnología.",
    "Hoy es un buen día para aprender Jetpack Compose.",
    "Este texto es generado en tiempo real, línea por línea.",
    "La velocidad de procesamiento es clave para una buena experiencia de usuario.",
    "La transcripción puede ser útil en conferencias, reuniones o subtítulos en vivo.",
    "¿Qué otras aplicaciones imaginas para esta tecnología?",
    "A medida que el audio se procesa, el texto sigue apareciendo aquí.",
    "Esto demuestra cómo una `LazyColumn` puede manejar grandes cantidades de datos sin problemas."
)

val taskMap = mapOf(
    LocalDate.of(2025, 2, 20) to listOf(
        Task("2",
            "Reunión de trabajo",
            "Discusión sobre el nuevo proyecto y planificación de tareas con el equipo.",
            LocalDateTime.of(2025, 2, 20, 10, 0),
            LocalDateTime.of(2025, 2, 20, 11, 30),
            "Oficina central",
            TaskType.FORMALLY
        ),
        Task("3",
            "Cena con amigos",
            "Cena en un restaurante italiano con el grupo de siempre.",
            LocalDateTime.of(2025, 2, 20, 20, 30),
            LocalDateTime.of(2025, 2, 20, 23, 0),
            "Restaurante Trattoria",
            TaskType.CASUAL
        )
    ),

    LocalDate.of(2025, 2, 21) to listOf(
        Task("4",
            "Sesión de gimnasio",
            "Entrenamiento de fuerza y cardio para mantenerse en forma.",
            LocalDateTime.of(2025, 2, 21, 7, 0),
            LocalDateTime.of(2025, 2, 21, 8, 30),
            "Gimnasio FitLife",
            TaskType.SPORTIVE
        ),
        Task("5",
            "Tarde de cine",
            "Ver la última película de Marvel con palomitas y refresco.",
            LocalDateTime.of(2025, 2, 21, 18, 0),
            LocalDateTime.of(2025, 2, 21, 20, 30),
            "Cine Yelmo",
            TaskType.CASUAL
        )
    ),

    LocalDate.of(2025, 2, 22) to listOf(
        Task("6",
            "Fiesta de cumpleaños",
            "Celebración del cumpleaños de Ana con música y karaoke.",
            LocalDateTime.of(2025, 2, 22, 21, 0),
            LocalDateTime.of(2025, 2, 23, 2, 0),
            "Casa de Ana",
            TaskType.FESTIVE
        )
    ),

    LocalDate.of(2025, 2, 23) to listOf(
        Task("7",
            "Desayuno con la shory hiperbellaka",
            "Disfruta de un desayuno especial con la shory en una acogedora cafetería del centro de Madrid. Croissants, café y buena conversación.",
            LocalDateTime.of(2025, 2, 23, 9, 0),
            LocalDateTime.of(2025, 2, 23, 10, 30),
            "Madrid",
            TaskType.SMARTLY
        ),
        Task("8",
            "Tarde de compras",
            "Recorrido por tiendas en busca de ropa nueva para la temporada.",
            LocalDateTime.of(2025, 2, 23, 17, 0),
            LocalDateTime.of(2025, 2, 23, 19, 0),
            "Centro comercial Gran Vía",
            TaskType.CASUAL
        )
    ),

    LocalDate.of(2025, 2, 24) to listOf(
        Task("9",
            "Entrevista de trabajo",
            "Entrevista para el puesto de Desarrollador Full-Stack en una startup.",
            LocalDateTime.of(2025, 2, 24, 11, 0),
            LocalDateTime.of(2025, 2, 24, 12, 0),
            "Oficinas de TechHub",
            TaskType.FORMALLY
        )
    ),

    LocalDate.of(2025, 2, 25) to listOf(
        Task("10",
            "Clases de inglés",
            "Clase avanzada de inglés enfocada en conversación y pronunciación.",
            LocalDateTime.of(2025, 2, 25, 18, 0),
            LocalDateTime.of(2025, 2, 25, 19, 30),
            "Academia Oxford",
            TaskType.CASUAL
        ),
        Task("11",
            "Cena romántica",
            "Cena especial en un restaurante de lujo con la pareja.",
            LocalDateTime.of(2025, 2, 25, 21, 0),
            LocalDateTime.of(2025, 2, 25, 23, 0),
            "Restaurante El Cielo",
            TaskType.SMARTLY
        )
    ),

    LocalDate.of(2025, 2, 26) to listOf(
        Task("12",
            "Día de home office",
            "Trabajo remoto desde casa con reuniones por videollamada.",
            LocalDateTime.of(2025, 2, 26, 9, 0),
            LocalDateTime.of(2025, 2, 26, 18, 0),
            "Casa",
            TaskType.CASUAL
        )
    ),

    LocalDate.of(2025, 2, 27) to listOf(
        Task("13",
            "Visita al médico",
            "Chequeo de rutina con el doctor.",
            LocalDateTime.of(2025, 2, 27, 10, 0),
            LocalDateTime.of(2025, 2, 27, 11, 0),
            "Clínica Sanitas",
            TaskType.CASUAL
        ),
        Task("14",
            "Concierto de rock",
            "Asistir al concierto de una banda legendaria.",
            LocalDateTime.of(2025, 2, 27, 20, 0),
            LocalDateTime.of(2025, 2, 27, 23, 0),
            "WiZink Center",
            TaskType.FESTIVE
        )
    ),

    LocalDate.of(2025, 2, 28) to listOf(
        Task("15",
            "Salida a la montaña",
            "Excursión a la montaña con amigos, con picnic incluido.",
            LocalDateTime.of(2025, 2, 28, 7, 0),
            LocalDateTime.of(2025, 2, 28, 17, 0),
            "Sierra de Madrid",
            TaskType.SPORTIVE
        ),
        Task("16",
            "Torneo de videojuegos",
            "Competencia amistosa de Super Smash Bros con premios para los ganadores.",
            LocalDateTime.of(2025, 2, 28, 19, 0),
            LocalDateTime.of(2025, 2, 28, 22, 0),
            "Casa de Jorge",
            TaskType.CASUAL
        )
    ),

    LocalDate.of(2025, 3, 1) to listOf(
        Task("17",
            "Boda de un amigo",
            "Celebración de la boda de Daniel y Laura con fiesta hasta la madrugada.",
            LocalDateTime.of(2025, 3, 1, 17, 0),
            LocalDateTime.of(2025, 3, 2, 3, 0),
            "Finca La Encantada",
            TaskType.FORMALLY
        )
    ),

    LocalDate.of(2025, 3, 2) to listOf(
        Task("18",
            "Tarde de lectura",
            "Momento de relax con un libro y un café en casa.",
            LocalDateTime.of(2025, 3, 2, 16, 0),
            LocalDateTime.of(2025, 3, 2, 18, 0),
            "Casa",
            TaskType.CASUAL
        )
    ),

    LocalDate.of(2025, 3, 3) to listOf(
        Task("19",
            "Partido de fútbol",
            "Jugar un partido con el equipo del barrio.",
            LocalDateTime.of(2025, 3, 3, 19, 0),
            LocalDateTime.of(2025, 3, 3, 21, 0),
            "Polideportivo Municipal",
            TaskType.SPORTIVE
        )
    ),

    LocalDate.of(2025, 3, 4) to listOf(
        Task("20",
            "Día de spa",
            "Un día completo de relajación con masajes y jacuzzi.",
            LocalDateTime.of(2025, 3, 4, 11, 0),
            LocalDateTime.of(2025, 3, 4, 15, 0),
            "Spa Zen",
            TaskType.SMARTLY
        )
    )
)