package io.elytra.sdk.network.protocol.codecs.play.inbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.ConfirmTeleportMessage
import io.elytra.sdk.network.utils.minecraft
import io.netty.buffer.ByteBuf

class ConfirmTeleportCodec : Codec<ConfirmTeleportMessage> {

	override fun encode(buffer: ByteBuf, message: ConfirmTeleportMessage): ByteBuf {
		buffer.minecraft.writeVarIntToBuffer(message.telportId)
		return buffer
	}

	override fun decode(buffer: ByteBuf): ConfirmTeleportMessage {
		val teleportId = buffer.minecraft.readVarIntFromBuffer();
		return ConfirmTeleportMessage(teleportId)
	}

}
