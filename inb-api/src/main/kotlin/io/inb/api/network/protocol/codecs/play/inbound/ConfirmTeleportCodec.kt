package io.inb.api.network.protocol.codecs.play.inbound

import com.flowpowered.network.Codec
import io.inb.api.network.protocol.message.play.ConfirmTeleportMessage
import io.inb.api.network.utils.ByteUtils
import io.netty.buffer.ByteBuf

class ConfirmTeleportCodec : Codec<ConfirmTeleportMessage> {
	override fun encode(buffer: ByteBuf, message: ConfirmTeleportMessage): ByteBuf {
		ByteUtils.writeVarIntToBuffer(buffer,message.telportId)
		return buffer
	}

	override fun decode(buffer: ByteBuf): ConfirmTeleportMessage {
		val teleportId = ByteUtils.readVarIntFromBuffer(buffer);
		return ConfirmTeleportMessage(teleportId)
	}

}
