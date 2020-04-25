package io.elytra.sdk.network.protocol.codecs.play

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.outbound.CustomPayloadMessage
import io.elytra.sdk.network.utils.readString
import io.elytra.sdk.network.utils.writeString
import io.netty.buffer.ByteBuf
import java.io.IOException

class CustomPayloadCodec : Codec<CustomPayloadMessage> {

    override fun encode(buffer: ByteBuf, message: CustomPayloadMessage): ByteBuf {
        buffer.writeString(message.channel)
        buffer.writeBytes(message.data)
        return buffer
    }

    override fun decode(buffer: ByteBuf): CustomPayloadMessage {
        val channel = buffer.readString(20)
        val i = buffer.readableBytes()
        if (i in 0..32767) {
            val data = buffer.readBytes(i)
            return CustomPayloadMessage(channel, data)
        } else {
            throw IOException("Payload may not be larger than 32767 bytes")
        }
    }
}
