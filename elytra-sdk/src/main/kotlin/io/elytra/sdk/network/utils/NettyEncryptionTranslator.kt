package io.elytra.sdk.network.utils

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import javax.crypto.Cipher

class NettyEncryptionTranslator(private val cipher: Cipher) {
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

    fun decipher(ctx: ChannelHandlerContext, buffer: ByteBuf): ByteBuf {
        val i = buffer.readableBytes()
        val byteArray = bufToBytes(buffer)
        val byteBuf = ctx.alloc().heapBuffer(cipher.getOutputSize(i))
        byteBuf.writerIndex(cipher.update(byteArray, 0, i, byteBuf.array(), byteBuf.arrayOffset()))
        return byteBuf
    }

    fun cipher(buf: ByteBuf, out: ByteBuf) {
        val i = buf.readableBytes()
        val bytes = bufToBytes(buf)
        val j: Int = cipher.getOutputSize(i)
        if (outputBuffer.size < j) {
            outputBuffer = ByteArray(j)
        }
        out.writeBytes(outputBuffer, 0, cipher.update(bytes, 0, i, outputBuffer))
    }
}
