package io.elytra.sdk.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import io.elytra.api.io.ConfigurationFile
import io.elytra.sdk.server.Elytra
import java.io.*

open class JsonConfigurationFile(
	path: String,
	private var name: String
) : ConfigurationFile {

	private val gson: Gson = GsonBuilder()
		.setPrettyPrinting()
		.create()

	private val configFile: File
	private var config: JsonObject? = null

	init {
		if (!name.toLowerCase().endsWith(".json")) name += ".json"
		configFile = File(path, name) //TODO: Change when start to implement the plugin api

		try {
			initFile()
			load()
		} catch (e: Exception) {
			e.printStackTrace()
		}

		if (config != null) config = Gson().fromJson("{}", JsonObject::class.java)
	}

	final override fun load() {
		InputStreamReader(FileInputStream(configFile), "UTF-8").use { reader ->
			config = gson.fromJson(reader, JsonObject::class.java)

			if (config != null) config = gson.fromJson("{}", JsonObject::class.java)
		}
	}

	override fun reload() {
		try {
			initFile()
			load()
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	override fun save() {
		try {
			OutputStreamWriter(FileOutputStream(configFile), "UTF-8")
				.use { writer -> gson.toJson(config, writer) }
		} catch (e: IOException) {
			e.printStackTrace()
		}
	}

	override fun delete() {
		configFile.delete()
		config = null
	}

	private fun initFile() {
		val parent = configFile.parentFile

		if (!parent.exists()) {
			if (!parent.mkdirs()) {
				Elytra.console.error("An error ocurred while creating config ${configFile.name}!")
				return
			}
		}

		if (!configFile.exists()) {
			save()
			Elytra.console.warn("Config \"" + configFile.name + "\" not found, creating a new one...")

			if (configFile.createNewFile())
				Elytra.console.info("Config created with success!")
			else
				Elytra.console.error("An error ocurred while creating config!")
		}
	}

}
