package io.elytra.sdk.network.protocol.message.login

import com.flowpowered.network.Message
import io.elytra.api.utils.Asyncable
import io.elytra.sdk.utils.cryptManager
import java.security.PrivateKey

data class EncryptionResponseMessage(
	val secretKeyEncrypted: ByteArray,
	val verifyTokenEncrypted: ByteArray
) : Message, Asyncable {
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as EncryptionResponseMessage

		if (!secretKeyEncrypted.contentEquals(other.secretKeyEncrypted)) return false
		if (!verifyTokenEncrypted.contentEquals(other.verifyTokenEncrypted)) return false

		return true
	}

	override fun hashCode(): Int {
		var result = secretKeyEncrypted.contentHashCode()
		result = 31 * result + verifyTokenEncrypted.contentHashCode()
		return result
	}
}
