package io.elytra.sdk.network.protocol.message.login

import com.flowpowered.network.Message
import io.elytra.api.utils.Asyncable
import java.security.PublicKey

data class EncryptionRequestMessage(
    val hashedServerId: String,
    val publicKey: PublicKey,
    val verifyToken: ByteArray
) : Message, Asyncable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EncryptionRequestMessage

        if (hashedServerId != other.hashedServerId) return false
        if (publicKey != other.publicKey) return false
        if (!verifyToken.contentEquals(other.verifyToken)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = hashedServerId.hashCode()
        result = 31 * result + publicKey.hashCode()
        result = 31 * result + verifyToken.contentHashCode()
        return result
    }
}
