package io.inb.sdk.network.handler

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.util.CharsetUtil

class ServerHandler : ChannelInboundHandlerAdapter() {

	override fun channelRead(ctx: ChannelHandlerContext, msg: Any?) {
		val input: ByteBuf = msg as ByteBuf

		val received = input.toString(CharsetUtil.UTF_8)
		println("Server received: $received")

		ctx.write(Unpooled.copiedBuffer("Received: $received", CharsetUtil.UTF_8))
	}
}
