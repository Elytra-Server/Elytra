package io.elytra.sdk.utils

import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

object ResourceUtils {

    fun saveResource(resource: String, outputFilePath: String = resource, replace: Boolean = false) {
        val outputFile = File(outputFilePath)
        if (outputFile.exists() && !replace) {
            // File is already there, and we don't want to replace it
            return
        }

        val inputStream = javaClass.classLoader.getResourceAsStream(resource.removePrefix("./"))
            ?: throw FileNotFoundException("Could not find the resource '$resource'")

        // Make sure parent directory exists
        outputFile.parentFile.mkdirs()

        if (!outputFile.exists() || replace) {
            inputStream.use {
                FileOutputStream(outputFile).use { out ->
                    val buf = ByteArray(1024)
                    var len: Int
                    while (inputStream.read(buf).also { len = it } > 0) {
                        out.write(buf, 0, len)
                    }
                }
            }
        }
    }
}
