package io.elytra.sdk.network.protocol

object ProtocolInfo {

	/**
	 * The protocol version supported by the server.
	 */
	var CURRENT_PROTOCOL = 340

	/**
	 * The game version supported by the server.
	 */
	var MINECRAFT_VERSION = "12.2.2"

	var LOGIN_PACKET: Byte = 0x01
}
