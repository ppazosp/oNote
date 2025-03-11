package ochat.onote.utils


import ochat.onote.data.Event
import ochat.onote.data.EventType
import java.time.LocalDate
import java.time.LocalDateTime

val eventMap = mapOf(
    LocalDate.of(2025, 2, 20) to listOf(
        Event("2",
            "Reunión de trabajo",
            "Discusión sobre el nuevo proyecto y planificación de tareas con el equipo.",
            LocalDateTime.of(2025, 2, 20, 10, 0),
            LocalDateTime.of(2025, 2, 20, 11, 30),
            "Oficina central",
            EventType.FORMALLY
        ),
        Event("3",
            "Cena con amigos",
            "Cena en un restaurante italiano con el grupo de siempre.",
            LocalDateTime.of(2025, 2, 20, 20, 30),
            LocalDateTime.of(2025, 2, 20, 23, 0),
            "Restaurante Trattoria",
            EventType.CASUAL
        )
    ),

    LocalDate.of(2025, 2, 21) to listOf(
        Event("4",
            "Sesión de gimnasio",
            "Entrenamiento de fuerza y cardio para mantenerse en forma.",
            LocalDateTime.of(2025, 2, 21, 7, 0),
            LocalDateTime.of(2025, 2, 21, 8, 30),
            "Gimnasio FitLife",
            EventType.SPORTIVE
        ),
        Event("5",
            "Tarde de cine",
            "Ver la última película de Marvel con palomitas y refresco.",
            LocalDateTime.of(2025, 2, 21, 18, 0),
            LocalDateTime.of(2025, 2, 21, 20, 30),
            "Cine Yelmo",
            EventType.CASUAL
        )
    ),

    LocalDate.of(2025, 2, 22) to listOf(
        Event("6",
            "Fiesta de cumpleaños",
            "Celebración del cumpleaños de Ana con música y karaoke.",
            LocalDateTime.of(2025, 2, 22, 21, 0),
            LocalDateTime.of(2025, 2, 23, 2, 0),
            "Casa de Ana",
            EventType.FESTIVE
        )
    ),

    LocalDate.of(2025, 2, 23) to listOf(
        Event("7",
            "Desayuno con la shory hiperbellaka",
            "Disfruta de un desayuno especial con la shory en una acogedora cafetería del centro de Madrid. Croissants, café y buena conversación.",
            LocalDateTime.of(2025, 2, 23, 9, 0),
            LocalDateTime.of(2025, 2, 23, 10, 30),
            "Madrid",
            EventType.SMARTLY
        ),
        Event("8",
            "Tarde de compras",
            "Recorrido por tiendas en busca de ropa nueva para la temporada.",
            LocalDateTime.of(2025, 2, 23, 17, 0),
            LocalDateTime.of(2025, 2, 23, 19, 0),
            "Centro comercial Gran Vía",
            EventType.CASUAL
        )
    ),

    LocalDate.of(2025, 2, 24) to listOf(
        Event("9",
            "Entrevista de trabajo",
            "Entrevista para el puesto de Desarrollador Full-Stack en una startup.",
            LocalDateTime.of(2025, 2, 24, 11, 0),
            LocalDateTime.of(2025, 2, 24, 12, 0),
            "Oficinas de TechHub",
            EventType.FORMALLY
        )
    ),

    LocalDate.of(2025, 2, 25) to listOf(
        Event("10",
            "Clases de inglés",
            "Clase avanzada de inglés enfocada en conversación y pronunciación.",
            LocalDateTime.of(2025, 2, 25, 18, 0),
            LocalDateTime.of(2025, 2, 25, 19, 30),
            "Academia Oxford",
            EventType.CASUAL
        ),
        Event("11",
            "Cena romántica",
            "Cena especial en un restaurante de lujo con la pareja.",
            LocalDateTime.of(2025, 2, 25, 21, 0),
            LocalDateTime.of(2025, 2, 25, 23, 0),
            "Restaurante El Cielo",
            EventType.SMARTLY
        )
    ),

    LocalDate.of(2025, 2, 26) to listOf(
        Event("12",
            "Día de home office",
            "Trabajo remoto desde casa con reuniones por videollamada.",
            LocalDateTime.of(2025, 2, 26, 9, 0),
            LocalDateTime.of(2025, 2, 26, 18, 0),
            "Casa",
            EventType.CASUAL
        )
    ),

    LocalDate.of(2025, 2, 27) to listOf(
        Event("13",
            "Visita al médico",
            "Chequeo de rutina con el doctor.",
            LocalDateTime.of(2025, 2, 27, 10, 0),
            LocalDateTime.of(2025, 2, 27, 11, 0),
            "Clínica Sanitas",
            EventType.CASUAL
        ),
        Event("14",
            "Concierto de rock",
            "Asistir al concierto de una banda legendaria.",
            LocalDateTime.of(2025, 2, 27, 20, 0),
            LocalDateTime.of(2025, 2, 27, 23, 0),
            "WiZink Center",
            EventType.FESTIVE
        )
    ),

    LocalDate.of(2025, 2, 28) to listOf(
        Event("15",
            "Salida a la montaña",
            "Excursión a la montaña con amigos, con picnic incluido.",
            LocalDateTime.of(2025, 2, 28, 7, 0),
            LocalDateTime.of(2025, 2, 28, 17, 0),
            "Sierra de Madrid",
            EventType.SPORTIVE
        ),
        Event("16",
            "Torneo de videojuegos",
            "Competencia amistosa de Super Smash Bros con premios para los ganadores.",
            LocalDateTime.of(2025, 2, 28, 19, 0),
            LocalDateTime.of(2025, 2, 28, 22, 0),
            "Casa de Jorge",
            EventType.CASUAL
        )
    ),

    LocalDate.of(2025, 3, 1) to listOf(
        Event("17",
            "Boda de un amigo",
            "Celebración de la boda de Daniel y Laura con fiesta hasta la madrugada.",
            LocalDateTime.of(2025, 3, 1, 17, 0),
            LocalDateTime.of(2025, 3, 2, 3, 0),
            "Finca La Encantada",
            EventType.FORMALLY
        )
    ),

    LocalDate.of(2025, 3, 2) to listOf(
        Event("18",
            "Tarde de lectura",
            "Momento de relax con un libro y un café en casa.",
            LocalDateTime.of(2025, 3, 2, 16, 0),
            LocalDateTime.of(2025, 3, 2, 18, 0),
            "Casa",
            EventType.CASUAL
        )
    ),

    LocalDate.of(2025, 3, 3) to listOf(
        Event("19",
            "Partido de fútbol",
            "Jugar un partido con el equipo del barrio.",
            LocalDateTime.of(2025, 3, 3, 19, 0),
            LocalDateTime.of(2025, 3, 3, 21, 0),
            "Polideportivo Municipal",
            EventType.SPORTIVE
        )
    ),

    LocalDate.of(2025, 3, 4) to listOf(
        Event("20",
            "Día de spa",
            "Un día completo de relajación con masajes y jacuzzi.",
            LocalDateTime.of(2025, 3, 4, 11, 0),
            LocalDateTime.of(2025, 3, 4, 15, 0),
            "Spa Zen",
            EventType.SMARTLY
        )
    )
)