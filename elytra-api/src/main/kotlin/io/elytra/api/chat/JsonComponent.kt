package io.elytra.api.chat

import java.lang.StringBuilder

/**
 * A component that can be transformed into a json string.
 */
interface JsonComponent {
    /**
     * Parse the component to a json string, and
     * append it to the [buff].
     */
    fun toJson(buff: Appendable)

    /**
     * Parse the component to a json string.
     */
    fun toJson(): String = StringBuilder().apply(this::toJson).toString()
}
