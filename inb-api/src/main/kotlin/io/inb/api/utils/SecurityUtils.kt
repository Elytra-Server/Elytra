package io.inb.api.utils

import java.security.*
import java.security.spec.X509EncodedKeySpec

object SecurityUtils {

	private val random = SecureRandom()

	/**
	 * Generate a RSA key pair.
	 *
	 * @return The RSA key pair.
	 */
	fun generateKeyPair(): KeyPair? {
		var keyPair: KeyPair? = null
		try {
			val generator = KeyPairGenerator.getInstance("RSA")

			generator.initialize(1024)
			keyPair = generator.generateKeyPair()
		} catch (ex: NoSuchAlgorithmException) {
			ex.printStackTrace()
		}

		return keyPair
	}

	/**
	 * Generate a random verify token.
	 *
	 * @return An array of 4 random bytes.
	 */
	fun generateVerifyToken(): ByteArray? {
		val token = ByteArray(4)
		random.nextBytes(token)

		return token
	}

	/**
	 * Generates an X509 formatted key used in authentication.
	 *
	 * @param base The key to use to generate a public key from its key spec.
	 * @return The X509 formatted key.
	 */
	fun generateX509Key(base: Key): Key? {
		var key: Key? = null

		try {
			val encodedKeySpec = X509EncodedKeySpec(base.encoded)
			val keyFactory = KeyFactory.getInstance("RSA")

			key = keyFactory.generatePublic(encodedKeySpec)
		} catch (ex: Exception) {
			ex.printStackTrace()
		}

		return key
	}
}
