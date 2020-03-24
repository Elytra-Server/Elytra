package io.inb.sdk.network

import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageEncoder

class Encoder : MessageToMessageEncoder<Any>() {
	override fun encode(ctx: ChannelHandlerContext?, msg: Any?, out: MutableList<Any>?) {
		if(msg is Packet){
			val packet = msg as Packet
			val clazz = packet.kClass
			val codec: Codec<Packet> = CodecHandler.getCodec(clazz)

			if(codec == null){
				println("ESTA MERDA É NULLA")
				return
			}

			val buffer = Unpooled.buffer(1)
			buffer.writeByte(codec.id.toInt())
			out?.add(Unpooled.wrappedBuffer(buffer, codec.encode(packet)))
		}

		if (msg != null) {
			out?.add(msg)
		}
	}
}
