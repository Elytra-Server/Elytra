package io.elytra.sdk.network.utils

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import javax.crypto.Cipher
import javax.crypto.ShortBufferException

class NettyEncryptionTranslator (private val cipher: Cipher){
	private var inputBuffer = ByteArray(0)
	private var outputBuffer = ByteArray(0)

	private fun bufToBytes(buf: ByteBuf): ByteArray {
		val i = buf.readableBytes()
		if (inputBuffer.size < i) {
			inputBuffer = ByteArray(i)
		}
		buf.readBytes(inputBuffer, 0, i)
		return inputBuffer
	}

	fun decipher(ctx: ChannelHandlerContext, buffer: ByteBuf): ByteBuf? {
		val i = buffer.readableBytes()
		val abyte = bufToBytes(buffer)
		val bytebuf = ctx.alloc().heapBuffer(cipher.getOutputSize(i))
		bytebuf.writerIndex(cipher.update(abyte, 0, i, bytebuf.array(), bytebuf.arrayOffset()))
		return bytebuf
	}

	fun cipher(`in`: ByteBuf, out: ByteBuf) {
		val i = `in`.readableBytes()
		val abyte = bufToBytes(`in`)
		val j: Int = cipher.getOutputSize(i)
		if (outputBuffer.size < j) {
			outputBuffer = ByteArray(j)
		}
		out.writeBytes(outputBuffer, 0, cipher.update(abyte, 0, i, outputBuffer))
	}

}
