package io.inb.api.network.protocol.codecs.status

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.inb.api.network.protocol.message.status.StatusResponseMessage
import io.netty.buffer.ByteBuf

class StatusResponseCodec : Codec<StatusResponseMessage> {
	override fun encode(buf: ByteBuf, message: StatusResponseMessage): ByteBuf {
		ByteBufUtils.writeUTF8(buf, Gson().toJson(message.body));
		return buf;
	}

	override fun decode(buffer: ByteBuf): StatusResponseMessage {
		val json = ByteBufUtils.readUTF8(buffer)
		return StatusResponseMessage(Gson().fromJson(json, JsonObject::class.java));
	}
}
