package io.elytra.sdk.io.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import io.elytra.sdk.utils.ResourceUtils
import java.io.*
import java.util.logging.Level

open class JsonConfigurationFile(var name: String) {
	companion object {
		private val empty
			get() = JsonObject()

		val gson: Gson = GsonBuilder()
			.setPrettyPrinting()
			.disableHtmlEscaping()
			.create()


		inline fun <reified T> getConfig(filePath: String): T {
			val file = File(filePath)
			if (!file.exists()) {
				throw IOException("Could not find the file $filePath")
			}

			return getConfig(file)
		}

		inline fun <reified T> getConfig(file: File): T {
			return InputStreamReader(FileInputStream(file), "UTF-8").use { reader ->
				gson.fromJson(reader, T::class.java)
			}
		}
	}

	private val configFile: File
	var config: JsonObject = empty

	init {
		if (!name.toLowerCase().endsWith(".json")) name += ".json"

		configFile = File(name)

		try {
			loadFile()
			loadConfig()
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	fun reload() {
		try {
			loadFile()
			loadConfig()
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	fun save() {
		try {
			OutputStreamWriter(FileOutputStream(configFile), "UTF-8")
				.use { writer -> gson.toJson(config, writer) }
		} catch (e: IOException) {
			e.printStackTrace()
		}
	}

	@Throws(IOException::class)
	private fun loadFile() {
		val parent = configFile.parentFile
		if (!parent.exists()) {
			if (!parent.mkdirs()) {
				log(Level.SEVERE, "Ocurreu um erro ao criar a config!")
				return
			}
		}
		if (!configFile.exists()) {
			try {
				ResourceUtils.saveResource(configFile.name)
			} catch (e: IllegalArgumentException) {
			}
			log(Level.WARNING, "A config \"" + configFile.name + "\" não foi encontrada, a criar uma nova...")
			if (configFile.createNewFile())
				log(Level.INFO, "A config foi criada com sucesso!")
			else
				log(Level.SEVERE, "Ocurreu um erro ao criar a config!")
		}
	}

	fun delete() {
		configFile.delete()
		config = empty
	}

	@Throws(Exception::class)
	private fun loadConfig() {
		config = try {
			getConfig(configFile)
		} catch (e: IOException) {
			empty
		}
	}

	private fun log(logLevel: Level, msg: String) {
		println("[JsonConfiguration] $msg")
	}
}
