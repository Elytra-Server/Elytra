package io.elytra.api.chat

/**
 * The mode in which a message should be sent to the player.
 */
object ChatMode {
    /** Messages sent from other players should go in this mode */
    const val PLAYER = 0
    /** Other messages, such as messages from command output, should go here */
    const val FEEDBACK = 1
    /** This is displayed above the hot bar, AKA action bar */
    const val GAME_INFO = 2
}
