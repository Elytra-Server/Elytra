package io.elytra.api.command

/**
 * Represents an entity that can issue conversations
 */
interface CommandIssuer {

    /**
     * Sends a single chat message
     *
     * @param message message dispatched into the chat
     */
    fun sendMessage(message: String)

    /**
     * Sends multiple messages to the chat
     *
     * @param messages an array of strings dispatched into the chat
     */
    fun sendMessage(vararg messages: String)
}
