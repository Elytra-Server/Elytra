package io.elytra.sdk.events

import io.elytra.api.events.EventBus
import io.elytra.api.events.Registrable
import io.elytra.api.events.listen

@Deprecated("Only used for development testing")
class TemporaryEventRegister : Registrable {

    override fun register() {
        EventBus.listen<PlayerJoinEvent>()
            .subscribe {
                println("teste ${it.player.displayName}")
            }
    }
}
