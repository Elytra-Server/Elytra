package io.elytra.sdk.network.protocol.codecs.play.inbound

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.message.play.ConfirmTeleportMessage
import io.elytra.sdk.network.utils.minecraft
import io.netty.buffer.ByteBuf

class ConfirmTeleportCodec : Codec<ConfirmTeleportMessage> {

	override fun encode(buffer: ByteBuf, message: ConfirmTeleportMessage): ByteBuf {
		ByteBufUtils.writeVarInt(buffer,message.telportId)
		return buffer
	}

	override fun decode(buffer: ByteBuf): ConfirmTeleportMessage {
		val teleportId = ByteBufUtils.readVarInt(buffer);
		return ConfirmTeleportMessage(teleportId)
	}

}
