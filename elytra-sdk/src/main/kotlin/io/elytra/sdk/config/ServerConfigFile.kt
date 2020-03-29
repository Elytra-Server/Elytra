package io.elytra.sdk.config

import io.elytra.api.io.ConfigurationFile
import io.elytra.api.server.ServerDescriptor
import io.elytra.sdk.server.Elytra
import io.elytra.sdk.utils.ElytraConsts

import io.elytra.sdk.utils.getResource
import java.io.IOException

class ServerConfigFile(
	private var descriptor: ServerDescriptor?
) : ConfigurationFile{

	override fun load() {
		try {
			val resource = getResource<ServerDescriptor>(ElytraConsts.SERVER_CONFIG_PATH)
			this.descriptor = resource
		}catch(e: IOException){
			Elytra.console.error(e.message!!)
		}
	}
}
