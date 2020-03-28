package io.elytra.sdk.utils

import com.google.gson.Gson
import io.elytra.api.io.ConfigurationFile

inline fun <reified T> ConfigurationFile.getResource(resourcePath: String) : T {
	val resource = javaClass.classLoader.getResource(resourcePath)

	return Gson().fromJson(resource.readText(), T::class.java)
}

