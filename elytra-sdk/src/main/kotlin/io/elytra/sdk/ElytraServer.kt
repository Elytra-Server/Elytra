package io.elytra.sdk

import io.elytra.sdk.server.Elytra
import io.elytra.sdk.server.internalModules
import io.elytra.sdk.server.registryModules
import org.koin.core.context.startKoin

object ElytraServer {
    @JvmStatic
    fun main(args: Array<String>) {
        val modules = (registryModules + internalModules)

        startKoin {
            printLogger()
            modules(modules)
        }

        Elytra.server.boot()
    }
}
