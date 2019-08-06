package com.alekseysamoylov.telegram.bot

import com.alekseysamoylov.telegram.connector.CrawlerByService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.logging.BotLogger
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import javax.annotation.PostConstruct


@Component
class DatingBot(private val crawlerByConnector: CrawlerByService) : TelegramLongPollingBot() {

//    @PostConstruct
//    fun registerBot() {
//        val botsApi = TelegramBotsApi()
//        try {
//            botsApi.registerBot(this)
//        } catch (e: TelegramApiException) {
//            e.printStackTrace();
//        }
//    }

    @Value("\${telegram.username}")
    lateinit var username: String
    @Value("\${telegram.password}")
    lateinit var password: String

    override fun getBotUsername(): String {
        return username
    }

    override fun getBotToken(): String {
        return password
    }

    override fun onUpdateReceived(update: Update) {
        try {
            if (update.hasMessage()) {
                val message = update.getMessage()
                if (message.hasText() || message.hasLocation()) {
                    handleIncomingMessage(message)
                }
            }
        } catch (e: Exception) {
            BotLogger.error("DatingBot error", e)
        }
    }

    private fun handleIncomingMessage(message: Message) {
        BotLogger.info("DatingBot", message.text)
        val messageText = message.text
        val offset = messageText.toIntOrNull()
        val responseText = crawlerByConnector.getPersons(message.from.id, offset)
        val sendMessageRequest = SendMessage(message.chatId, responseText)
        execute(sendMessageRequest)
    }
}

