package io.elytra.sdk.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object ElytraConsts {
    const val SERVER_CONFIG_PATH = "./server.json"
    const val COMMAND_PREFIX = "/"

    val GSON: Gson = GsonBuilder().create()
}
