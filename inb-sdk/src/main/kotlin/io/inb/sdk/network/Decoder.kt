package io.inb.sdk.network

import io.inb.api.protocol.CodecHandler
import io.inb.api.protocol.Packet
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ReplayingDecoder

class Decoder : ReplayingDecoder<Packet>() {
	override fun decode(ctx: ChannelHandlerContext?, buffer: ByteBuf?, list: MutableList<Any>?) {
		val id = buffer?.readUnsignedInt()

		println("Decoding $id")

		val codec = id?.toShort()?.let { CodecHandler.getCodec(it) }

		if(codec == null){
			println("Invalid packet, no codec for $id")
			return
		}

		list?.add(codec.decode(buffer))
	}
}
