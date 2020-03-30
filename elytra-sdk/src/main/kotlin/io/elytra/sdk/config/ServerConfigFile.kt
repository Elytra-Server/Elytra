package io.elytra.sdk.config

import io.elytra.api.io.ConfigurationFile
import io.elytra.api.server.ServerDescriptor
import io.elytra.sdk.utils.ElytraConsts
import io.elytra.sdk.utils.getResource

class ServerConfigFile(
	private var descriptor: ServerDescriptor?
) : ConfigurationFile {

	init {
		descriptor = getResource<ServerDescriptor>(ElytraConsts.SERVER_CONFIG_PATH)
	}
}
