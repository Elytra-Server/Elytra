package io.elytra.sdk.network.protocol.codecs.play

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.message.play.CustomPayloadMessage
import io.elytra.sdk.network.protocol.message.play.KeepAliveMessage
import io.netty.buffer.ByteBuf

class KeepAliveCodec : Codec<KeepAliveMessage>{
	override fun encode(buf: ByteBuf, message: KeepAliveMessage): ByteBuf {
		ByteBufUtils.writeVarInt(buf,message.id)
		return buf
	}

	override fun decode(buffer: ByteBuf?): KeepAliveMessage {
		val id = ByteBufUtils.readVarInt(buffer)
		return KeepAliveMessage(id)
	}

}
