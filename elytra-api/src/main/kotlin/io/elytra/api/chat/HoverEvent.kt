package io.elytra.api.chat

import com.google.gson.annotations.SerializedName

data class HoverEvent(val action: Action, val value: String) {

    enum class Action {
        @SerializedName("show_text")
        SHOW_TEXT,

        @SerializedName("show_item")
        SHOW_ITEM,

        @SerializedName("show_entity")
        SHOW_ENTITY
    }
}
