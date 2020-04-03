package io.elytra.sdk.io.config

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class JsonConfiguration(configName: String = "config") : JsonConfigurationFile(configName) {
    inline fun <reified T> get(path: String, defaultValue: T): T {
        return getElement(path, defaultValue, T::class.java)
    }

    inline fun <reified T> getOrNull(path: String): T? {
        return getElement(path, null, T::class.java)
    }

    fun <T> getElement(path: String, defaultValue: T, type: Type): T {
        return try {
            gson.fromJson<T>(getElementOrNull(path), type) ?: defaultValue
        } catch (e: Exception) {
            defaultValue
        }
    }

    fun addDefault(path: String, value: Any) {
        if (!contains(path)) set(path, value)
    }

    operator fun set(path: String, value: Any) {
        if (!path.contains(".")) {
            set(config, path, value)
            return
        }

        val subPaths = path.split('.')
        var prev = config
        for (i in subPaths.indices) {
            val sectionName = subPaths[i]
            if (i == subPaths.size - 1) {
                set(prev, sectionName, value)
                return
            }

            if (!prev.has(sectionName)) {
                val section = JsonObject()
                prev.add(sectionName, section)
                prev = section
                continue
            }
            prev = prev.getAsJsonObject(sectionName)
        }
    }

    private operator fun set(jsonObject: JsonObject, path: String, value: Any) {
        val a = gson.toJsonTree(value)
        jsonObject.add(path, a)
        save()
    }

    operator fun contains(path: String): Boolean {
        return try {
            getElementOrNull(path) != null
        } catch (e: Exception) {
            false
        }
    }

    private fun getElementOrNull(path: String): JsonElement? {
        if (!path.contains(".")) {
            return config.get(path)
        }

        var subPath: JsonElement = config
        for (p in path.split('.')) {
            if (subPath !is JsonObject) return null
            if (!subPath.has(p)) return null

            subPath = subPath.get(p)
        }

        return subPath
    }
}
