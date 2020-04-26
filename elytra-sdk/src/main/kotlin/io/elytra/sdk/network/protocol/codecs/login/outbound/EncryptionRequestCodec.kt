package io.elytra.sdk.network.protocol.codecs.login.outbound

import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.login.EncryptionRequestMessage
import io.elytra.sdk.network.utils.writeByteArray
import io.elytra.sdk.network.utils.writeString
import io.netty.buffer.ByteBuf

class EncryptionRequestCodec : OutboundCodec<EncryptionRequestMessage>() {
    override fun encode(buffer: ByteBuf, message: EncryptionRequestMessage): ByteBuf {
        buffer.writeString(message.hashedServerId)
        buffer.writeByteArray(message.publicKey.encoded)
        buffer.writeByteArray(message.verifyToken)

        return buffer
    }
}
