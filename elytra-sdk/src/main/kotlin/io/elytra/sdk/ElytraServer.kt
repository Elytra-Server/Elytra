package io.elytra.sdk

import io.elytra.api.server.Server
import io.elytra.api.server.ServerDescriptor
import io.elytra.sdk.io.config.JsonConfigurationFile
import io.elytra.sdk.network.protocol.ProtocolInfo
import io.elytra.sdk.server.Elytra
import io.elytra.sdk.server.ElytraServer
import io.elytra.sdk.utils.ElytraConsts
import io.elytra.sdk.utils.ResourceUtils
import org.koin.core.context.startKoin
import org.koin.dsl.binds
import org.koin.dsl.module

object ElytraServer {
    @JvmStatic
    fun main(args: Array<String>) {
        // Returns null when the application is called with --help or --version
        val descriptor = parseArguments(args) ?: return

        printUglyLogo()

        val koin = startKoin {
            printLogger()
            modules(
                registryModules + internalModules + module {
                    single { Elytra(descriptor) } binds arrayOf(ElytraServer::class, Server::class)
                }
            )
        }

        val elytra: Elytra = koin.koin.get()

        Elytra.logger.info("Bootstrapping the server...")
        Elytra.logger.info("This version of Elytra is targeted for Minecraft ${ProtocolInfo.MINECRAFT_VERSION}")
        elytra.boot()
    }

    private fun parseArguments(args: Array<String>): ServerDescriptor? {
        val serverDescriptorBuilder = getDefaultServerDescriptor(args).builder()

        var i = 0
        loop@ while (i < args.size) {
            val option = args[i]
            if (option.isEmpty() || option[0] != '-') {
                System.err.println("Invalid argument passed: $option")
            }

            when (option) {
                "--help", "-help", "-h" -> {
                    println("TODO Show Help")
                    return null
                }
                "--version", "-version", "-v" -> {
                    println("Minecraft Version: ${ProtocolInfo.MINECRAFT_VERSION}")
                    println("Protocol Version: ${ProtocolInfo.CURRENT_PROTOCOL}")
                    return null
                }
                "--onlinemode", "--online", "--onlyPremium", "-P" -> {
                    if (i == args.size - 1) {
                        serverDescriptorBuilder.options.onlyPremium = true
                        break@loop
                    } else if (args[i + 1].startsWith('-')) {
                        serverDescriptorBuilder.options.onlyPremium = true
                    } else {
                        val raw = args[++i]
                        when (raw.toLowerCase()) {
                            "true", "t", "1" -> serverDescriptorBuilder.options.onlyPremium = true
                            "false", "f", "0" -> serverDescriptorBuilder.options.onlyPremium = false
                            else -> {
                                System.err.println("Specified option '$raw' for $option is not valid!")
                                System.err.println("Valid values are (t)rue or (f)alse!")
                            }
                        }
                    }
                }
            }

            // The following options require parameters, so check the array size if it has them
            if (i == args.size - 1) {
                System.err.println("The option '$option' wasn't found or requires extra parameters")
            } else {
                val rawParam = args[++i]
                when (option) {
                    "--port", "-p" -> {
                        val port = rawParam.toIntOrNull()
                        if (port == null) {
                            System.err.println("The port '$rawParam' is not a valid port value")
                        } else {
                            serverDescriptorBuilder.options.port = port
                        }
                    }
                }
            }
            i++
        }

        return serverDescriptorBuilder.build()
    }

    private fun getDefaultServerDescriptor(args: Array<String>): ServerDescriptor {
        var configPath = ElytraConsts.SERVER_CONFIG_PATH

        for (i in args.indices) {
            if (i < args.size - 1 && args[i].equals("--config", true)) {
                configPath = args[i + 1]
                break
            }
        }

        ResourceUtils.saveResource("server.json", configPath, false)

        val serverDescriptor: ServerDescriptor = JsonConfigurationFile
            .getConfig<ServerDescriptor>(configPath)
            .run {
                copy(motd = motd.copy(
                    description = motd.description
                        .replace('&', '§')
                        .replace("(?<!\\\\)\"".toRegex(), "\\\\\""),
                    pingText = motd.pingText
                        .replace('&', '§')
                        .replace("(?<!\\\\)\"".toRegex(), "\\\\\"")
                ))
            }

        JsonConfigurationFile.saveToConfig(serverDescriptor, configPath)

        return serverDescriptor
    }

    private fun printUglyLogo() {
        println("""
 _____  _         _               
|  ___|| |       | |              
| |__  | | _   _ | |_  _ __  __ _ 
|  __| | || | | || __|| '__|/ _` |
| |___ | || |_| || |_ | |  | (_| |
\____/ |_| \__, | \__||_|   \__,_|
            __/ |                 
           |___/                               
            """)
    }
}
