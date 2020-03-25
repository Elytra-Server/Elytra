package io.inb.api.network.protocol.codecs.play

import com.flowpowered.network.Codec
import io.inb.api.network.protocol.message.play.ClientSettingsMessage
import io.inb.api.network.protocol.message.play.ConfirmTeleportMessage
import io.inb.api.utils.Utils
import io.netty.buffer.ByteBuf

class ConfirmTeleportCodec : Codec<ConfirmTeleportMessage> {
	override fun encode(buffer: ByteBuf, message: ConfirmTeleportMessage): ByteBuf {
		Utils.writeVarIntToBuffer(buffer,message.telportId)
		return buffer
	}

	override fun decode(buffer: ByteBuf): ConfirmTeleportMessage {
		val teleportId = Utils.readVarIntFromBuffer(buffer);
		return ConfirmTeleportMessage(teleportId)
	}

}
