package io.elytra.sdk.network.protocol

object ProtocolInfo {

	/**
	 * The protocol version supported by the server.
	 */
	const val CURRENT_PROTOCOL = 340

	/**
	 * The game version supported by the server.
	 */
	const val MINECRAFT_VERSION = "12.2.2"

	//region PLAY
	const val CONFIRM_TELEPORT: Int = 0x00
	const val CLIENT_SETTINGS: Int = 0x04
	const val I_CUSTOM_PAYLOAD:Int = 0x09 // 0x9
	const val I_CHAT:Int = 0x02
	const val O_CHAT:Int = 0x0F

	const val HELD_ITEM_CHANGE: Int = 0x3A
	const val PLAYER_ABILITIES: Int = 0x2B
	const val SERVER_DIFFICULTY: Int = 0x0D
	const val O_CUSTOM_PAYLOAD: Int = 0x18
	const val JOIN_GAME: Int = 0x23
	const val PLAYER_POS_LOOK: Int = 0x2F
	const val LOAD_CHUNK_DATA: Int = 0x21
	const val ENTITY_STATUS: Int = 0x1A
	const val PLAY_DISCONNECT: Int = 0x1B
	const val PLAYER_LIST_ITEM: Int = 0x2e
	//endregion

	//region STATUS
	const val SERVER_QUERY: Int = 0x00
	const val SERVER_INFO:Int = 0x00
	const val I_PING: Int = 0x01
	const val O_PING:Int = 0x01
	//endregion

	//region LOGIN
	const val LOGIN_START: Int = 0x00
	const val ENCRYPTION_RESPONSE: Int = 0x01
	const val ENCRYPTION_REQUEST: Int = 0x01
	const val LOGIN_SUCCESS: Int = 0x02
	const val LOGIN_DISCONNECT:Int = 0x00
	//endregion

	const val HANDSHAKE: Int = 0x00
}
