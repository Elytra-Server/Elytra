package io.inb.api.network.protocol.handlers.status

import com.flowpowered.network.MessageHandler
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.inb.api.events.ServerListPingEvent
import io.inb.api.io.EventBus
import io.inb.api.network.NetworkSession
import io.inb.api.network.protocol.handlers.InbMessageHandler
import io.inb.api.network.protocol.message.status.StatusRequestMessage
import io.inb.api.network.protocol.message.status.StatusResponseMessage
import java.util.*
import kotlin.collections.ArrayList

/**
 * @see https://wiki.vg/Server_List_Ping
 */
class StatusRequestHandler : InbMessageHandler<StatusRequestMessage>() {

	override fun handle(session: NetworkSession, message: StatusRequestMessage) {
		val event = ServerListPingEvent(
			"§aInb - Test Server",
			"14.4.4",
			480,
			0
		)
		EventBus.post(event)

		val json: String = Gson().toJson(
			StatusResponse(
				Version("INB",498),
				Players(1,0, ArrayList()),
				Description("§4Hello World! INB")))

		session.send(StatusResponseMessage(json))
		//println("StatusRequestHandler - $message")
	}

	data class StatusResponse(val version: Version,
							  val players: Players,
							  val description: Description) {}

	data class Description(val text: String) {}

	data class Players(val max: Int,
					   val online: Int,
					   val sample: List<Player>) {}

	data class Player(val name: String,
					   val id: String) {}

	data class Version(val name: String,
					   val protocol: Int) {}

}
