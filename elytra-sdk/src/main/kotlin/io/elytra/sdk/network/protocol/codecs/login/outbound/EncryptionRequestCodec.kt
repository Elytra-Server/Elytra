package io.elytra.sdk.network.protocol.codecs.login.outbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.login.EncryptionRequestMessage
import io.elytra.sdk.network.utils.minecraft
import io.netty.buffer.ByteBuf
import java.io.IOException

class EncryptionRequestCodec : Codec<EncryptionRequestMessage> {
    override fun encode(buffer: ByteBuf, message: EncryptionRequestMessage): ByteBuf {
        buffer.minecraft.writeString(message.hashedServerId)
        buffer.minecraft.writeByteArray(message.publicKey.encoded)
        buffer.minecraft.writeByteArray(message.verifyToken)
        return buffer
    }

    override fun decode(buffer: ByteBuf): EncryptionRequestMessage {
        throw IOException("No have decode support for this")
    }
}
