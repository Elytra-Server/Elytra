package io.elytra.sdk.network.protocol.codecs

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.message.HandshakeMessage
import io.netty.buffer.ByteBuf


class HandshakeCodec : Codec<HandshakeMessage> {

	override fun decode(buffer: ByteBuf): HandshakeMessage {
		val version = ByteBufUtils.readVarInt(buffer)
		val address = ByteBufUtils.readUTF8(buffer)
		val port = buffer.readUnsignedShort()
		val state = ByteBufUtils.readVarInt(buffer)
		return HandshakeMessage(version, address, port, state)
	}

	override fun encode(buf: ByteBuf, message: HandshakeMessage): ByteBuf {
		ByteBufUtils.writeVarInt(buf, message.version);
		ByteBufUtils.writeUTF8(buf, message.address);
		buf.writeShort(message.port);
		ByteBufUtils.writeVarInt(buf, message.state);
		return buf
	}
}
