package io.elytra.sdk.network.protocol.message.login

import com.flowpowered.network.Message
import io.elytra.api.utils.Asyncable

data class EncryptionResponseMessage(val secretKeyEncrypted: ByteArray, val verifyTokenEncrypted: ByteArray) : Message, Asyncable
