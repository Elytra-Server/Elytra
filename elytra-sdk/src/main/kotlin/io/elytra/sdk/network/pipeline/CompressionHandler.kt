package io.elytra.sdk.network.pipeline

import com.flowpowered.network.util.ByteBufUtils
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.DecoderException
import io.netty.handler.codec.EncoderException
import io.netty.handler.codec.MessageToMessageCodec
import java.util.zip.Deflater
import java.util.zip.Inflater

class CompressionHandler(
    private val threshold: Int
) : MessageToMessageCodec<ByteBuf, ByteBuf>() {

    private val compressionLevel = Deflater.DEFAULT_COMPRESSION

    private val inflater: Inflater = Inflater()
    private val deflater: Deflater = Deflater(compressionLevel)

    override fun encode(ctx: ChannelHandlerContext, msg: ByteBuf, out: MutableList<Any>) {
        val prefixBuf = ctx.alloc().buffer(5)
        val contentsBuf: ByteBuf

        if (msg.readableBytes() >= threshold) {
            val index = msg.readerIndex()
            val length = msg.readableBytes()

            val sourceData = ByteArray(length)
            msg.readBytes(sourceData)
            deflater.setInput(sourceData)
            deflater.finish()

            val compressedData = ByteArray(length)
            val compressedLength = deflater.deflate(compressedData)
            deflater.reset()

            contentsBuf = when {
                compressedLength == 0 -> {
                    throw EncoderException("Failed to compress message of size $length")
                }
                compressedLength >= length -> {
                    ByteBufUtils.writeVarInt(prefixBuf, 0)
                    msg.readerIndex(index)
                    msg.retain()
                    msg
                }
                else -> {
                    ByteBufUtils.writeVarInt(prefixBuf, length)
                    Unpooled.wrappedBuffer(compressedData, 0, compressedLength)
                }
            }
        } else {
            ByteBufUtils.writeVarInt(prefixBuf, 0)
            msg.retain()
            contentsBuf = msg
        }

        out.add(Unpooled.wrappedBuffer(prefixBuf, contentsBuf))
    }

    override fun decode(ctx: ChannelHandlerContext, msg: ByteBuf, out: MutableList<Any>) {
        val index = msg.readerIndex()
        val uncompressedSize = ByteBufUtils.readVarInt(msg)

        if (uncompressedSize == 0) {
            val length = msg.readableBytes()

            if (length >= threshold) {
                throw DecoderException("Received uncompressed message of size $length greater than threshold $threshold")
            }

            val buf = ctx.alloc().buffer(length)
            msg.readBytes(buf, length)
            out.add(buf)
        } else {
            val sourceData = ByteArray(msg.readableBytes())
            msg.readBytes(sourceData)
            inflater.setInput(sourceData)

            val destData = ByteArray(uncompressedSize)
            val resultLength = inflater.inflate(destData)
            inflater.reset()

            if (resultLength == 0) {
                msg.readerIndex(index)
                msg.retain()
                out.add(msg)
            } else if (resultLength != uncompressedSize) {
                throw DecoderException(
                    "Received compressed message claiming to be of size $uncompressedSize but actually $resultLength")
            } else {
                out.add(Unpooled.wrappedBuffer(destData))
            }
        }
    }
}
