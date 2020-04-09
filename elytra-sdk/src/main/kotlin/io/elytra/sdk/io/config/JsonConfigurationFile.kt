package io.elytra.sdk.io.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import io.elytra.sdk.utils.ResourceUtils
import java.io.*

open class JsonConfigurationFile(var name: String) {
    companion object {
        private val empty
            get() = JsonObject()

        val gson: Gson = GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create()

        fun saveToConfig(value: Any, filePath: String) {
            val file = File(filePath)
            if (!file.exists()) {
                throw IOException("Could not find the file $filePath")
            }

            OutputStreamWriter(FileOutputStream(file), "UTF-8")
                .use { writer -> gson.toJson(value, writer) }
        }

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
                throw IOException("Failed to create the directory ${parent.absolutePath} for the file ${configFile.name}")
            }
        }
        if (!configFile.exists()) {
            try {
                ResourceUtils.saveResource(configFile.name)
            } catch (e: FileNotFoundException) {
                // File not found in the jar, create a blank one
                if (!configFile.createNewFile()) {
                    throw IOException("Failed to create the file ${configFile.name}")
                }
            }
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
}
