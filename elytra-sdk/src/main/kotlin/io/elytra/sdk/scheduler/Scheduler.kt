package io.elytra.sdk.scheduler

import io.elytra.sdk.network.SessionRegistry
import io.elytra.sdk.server.Elytra
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

// TODO: Not final, just to handle the tick for now
class Scheduler(
    private val sessionRegistry: SessionRegistry,

    // TODO: Needs to be changed when handle the "thread per world"
    private val mainExecutor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
) {

    private val tickEvery: Long = 50

    fun start() {
        mainExecutor.scheduleAtFixedRate({
            try {
                tick()
            } catch (ex: Exception) {
                Elytra.logger.error("Error while ticking")
                ex.printStackTrace()
            }
        }, 0, tickEvery, TimeUnit.MILLISECONDS)
    }

    private fun tick() {
        sessionRegistry.tick()
    }
}
