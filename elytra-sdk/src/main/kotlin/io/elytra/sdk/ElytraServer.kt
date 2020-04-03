package io.elytra.sdk

import io.elytra.sdk.server.Elytra

object ElytraServer {
    @JvmStatic
    fun main(args: Array<String>) {
        Elytra.server.boot()
    }
}
