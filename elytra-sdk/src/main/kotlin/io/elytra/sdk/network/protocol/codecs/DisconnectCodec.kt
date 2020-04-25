package io.elytra.sdk.network.protocol.codecs

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.outbound.DisconnectMessage
import io.elytra.sdk.network.utils.readString
import io.elytra.sdk.network.utils.writeString
import io.netty.buffer.ByteBuf

class DisconnectCodec : Codec<DisconnectMessage> {
    override fun encode(buffer: ByteBuf, message: DisconnectMessage): ByteBuf {
        buffer.writeString(message.textComponent.toJson())
        return buffer
    }

    override fun decode(buffer: ByteBuf): DisconnectMessage {
        return DisconnectMessage(buffer.readString(32767))
    }
}
