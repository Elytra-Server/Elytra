package io.inb.api.network.pipeline

import com.flowpowered.network.util.ByteBufUtils
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageCodec
import kotlin.experimental.and


class DecoderHandler : ByteToMessageCodec<ByteBuf>() {

	private fun readableVarInt(buf: ByteBuf): Boolean {
		if (buf.readableBytes() > 5) {
			return true
		}
		val idx = buf.readerIndex()
		var input: Byte
		do {
			if (buf.readableBytes() < 1) {
				buf.readerIndex(idx)
				return false
			}
			input = buf.readByte()

		} while ((input.and(0x80.toByte())) != 0.toByte())
		buf.readerIndex(idx)

		return true
	}

	override fun encode(ctx: ChannelHandlerContext, msg: ByteBuf, out: ByteBuf) {
		ByteBufUtils.writeVarInt(out, msg.readableBytes());
		out.writeBytes(msg);
	}

	override fun decode(ctx: ChannelHandlerContext?, input: ByteBuf?, out: MutableList<Any>?) {
		input!!.markReaderIndex()

		if (!readableVarInt(input)) {
			return
		}

		val length = ByteBufUtils.readVarInt(input)
		if (input.readableBytes() < length) {
			input.resetReaderIndex()
			return
		}

		val buf = ctx!!.alloc().buffer(length)

		input.readBytes(buf, length)
		out!!.add(buf)
	}
}
