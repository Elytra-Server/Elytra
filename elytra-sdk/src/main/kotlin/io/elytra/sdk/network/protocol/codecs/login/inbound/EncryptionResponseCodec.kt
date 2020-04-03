package io.elytra.sdk.network.protocol.codecs.login.inbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.login.EncryptionResponseMessage
import io.elytra.sdk.network.utils.minecraft
import io.netty.buffer.ByteBuf
import java.io.IOException

class EncryptionResponseCodec : Codec<EncryptionResponseMessage> {
    override fun encode(buffer: ByteBuf, message: EncryptionResponseMessage): ByteBuf {
        throw IOException("No have decode support for this")
    }

    override fun decode(buffer: ByteBuf): EncryptionResponseMessage {
        val secretKeyEncrypted = buffer.minecraft.readByteArray()
        val verifyTokenEncrypted = buffer.minecraft.readByteArray()
        return EncryptionResponseMessage(secretKeyEncrypted!!, verifyTokenEncrypted!!)
    }
}
