package ochat.onote.backend

import android.content.Context
import android.util.Log
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.models.Message
import io.getstream.chat.android.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object ChatManager {

    private const val API_KEY = "9cy4vbx3unb6"
    private val TAG = ChatManager::class.java.simpleName
    private var inicializado = false


    private lateinit var client: ChatClient

    //hacer un get de la variable inicializado
    fun getInicializado(): Boolean {
        return inicializado
    }

    fun getChatClient(): ChatClient {
        return client
    }

    fun initChat(applicationContext: Context) {
        if (::client.isInitialized) {
            Log.d(TAG, "ChatClient ya inicializado.")
            return
        }

        client = ChatClient.Builder(API_KEY, applicationContext)
            .logLevel(ChatLogLevel.ALL)
            .build()

        Log.d(TAG, "ChatClient inicializado correctamente.")
    }

    //devuelve un token de usuario
    fun connectUser(userId: String, userName: String) : Int   {
        if (!::client.isInitialized) {
            Log.e(TAG, "Error: ChatClient no está inicializado.")
            return -1
        }

        val user = User(id = userId, name = userName)
        val token = client.devToken(user.id)
        var flag = 0

        client.connectUser(user, token).enqueue { result ->
            if (result.isSuccess) {
                Log.d(TAG, "Usuario conectado: ${user.id}")
                flag = 1
                inicializado = true
            } else {
                Log.e(TAG, "Error al conectar usuario: ${user.id}.")
                flag = -1
            }
        }
        return flag
    }

    fun watchChannel(channelType: String = "livestream", channelId: String = "livestream_ochat") {
        if (!::client.isInitialized) {
            Log.e(TAG, "Error: ChatClient no está inicializado.")
            return
        }

        val channelClient = client.channel(channelType, channelId)
        channelClient.watch().enqueue { result ->
            if (result.isSuccess) {
                val channel: Channel = result.getOrThrow()
                Log.d(TAG, "Canal encontrado: ${channel.id}")
            } else {
                Log.e(TAG, "Error al obtener el canal")
            }
        }

        sendMessage(client, channelId, "Hola, soy Ochat")
    }

    fun sendMessage(client: ChatClient, channelId: String, text: String) {
        val channelClient = client.channel(channelType = "livestream", channelId = channelId)

        val message = Message(
            text = text
        )

        CoroutineScope(Dispatchers.IO).launch {
            val result = channelClient.sendMessage(message).await()
            if (result.isSuccess) {
                Log.d("Chat", "Mensaje enviado correctamente")
            } else {
                Log.d("Chat", "Error al enviar mensaje")
            }
        }
    }
}
