package io.elytra.api.command

import io.elytra.api.chat.TextComponent

interface TabCompletion {

    val match: String
    val tooltip: TextComponent
}
