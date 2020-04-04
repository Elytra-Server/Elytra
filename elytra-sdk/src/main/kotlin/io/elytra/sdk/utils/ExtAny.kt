package io.elytra.sdk.utils

import com.google.gson.Gson
import io.elytra.api.io.ConfigurationFile
import java.io.IOException

inline fun <reified T> ConfigurationFile.getResource(resourcePath: String): T {
    val resource = javaClass.classLoader.getResource(resourcePath) ?: throw IOException("")

    return Gson().fromJson(resource.readText(), T::class.java)
}
