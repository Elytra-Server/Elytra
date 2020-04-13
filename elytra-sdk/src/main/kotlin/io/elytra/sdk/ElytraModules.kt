package io.elytra.sdk

import com.mojang.authlib.minecraft.MinecraftSessionService
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
import io.elytra.api.command.handler.CommandHandler
import io.elytra.api.command.registry.CommandRegistry
import io.elytra.sdk.command.handler.ElytraCommandHandler
import io.elytra.sdk.command.registry.ElytraCommandRegistry
import io.elytra.sdk.network.SessionRegistry
import io.elytra.sdk.scheduler.Scheduler
import io.elytra.sdk.server.PlayerRegistry
import java.net.Proxy
import java.util.*
import org.koin.dsl.module

val registryModules = module {
    single { PlayerRegistry() }
    single { SessionRegistry() }
    single<CommandRegistry> { ElytraCommandRegistry() }
}

val internalModules = module {
    single { Scheduler(get()) }
    single<CommandHandler> { ElytraCommandHandler(get()) }

    single<MinecraftSessionService> {
        (YggdrasilAuthenticationService(
            Proxy.NO_PROXY, UUID.randomUUID().toString()
        )).createMinecraftSessionService()
    }
}
