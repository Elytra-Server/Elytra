package io.elytra.api.chat

import java.lang.StringBuilder

interface JsonComponent {
    fun toJson(buff: Appendable)

    fun toJson(): String = StringBuilder().apply(this::toJson).toString()
}
