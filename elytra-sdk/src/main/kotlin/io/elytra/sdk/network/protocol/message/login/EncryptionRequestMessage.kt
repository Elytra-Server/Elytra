package io.elytra.sdk.network.protocol.message.login

import com.flowpowered.network.Message
import io.elytra.api.utils.Asyncable
import java.security.PublicKey

data class EncryptionRequestMessage(val hashedServerId: String, val publicKey: PublicKey, val verifyToken: ByteArray) : Message, Asyncable
