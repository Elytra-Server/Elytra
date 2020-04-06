package io.elytra.api.command

interface CommandSender {

    fun sendMessage(message: String)

    fun sendMessage(vararg messages: String)
}
