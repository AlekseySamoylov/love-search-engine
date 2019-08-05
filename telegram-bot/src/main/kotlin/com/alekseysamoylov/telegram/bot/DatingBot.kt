package com.alekseysamoylov.telegram.bot

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.logging.BotLogger
import org.telegram.telegrambots.meta.api.objects.Message


@Component
class DatingBot : TelegramLongPollingBot() {

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
            BotLogger.error("DatingBot error",e)
        }
    }

    private fun handleIncomingMessage(message: Message) {
        BotLogger.info("DatingBot", message.text)
        val sendMessageRequest = SendMessage(message.chatId, "Hello, ${message.from.firstName}")
        execute(sendMessageRequest)
    }
}

