package io.elytra.sdk.utils

import java.security.*
import java.security.spec.EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.*
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

val cryptManager get() = CryptManager()

class CryptManager{

	fun createNewSharedKey(): SecretKey {
		val keygenerator = KeyGenerator.getInstance("AES")
		keygenerator.init(128)
		return keygenerator.generateKey()
	}

	fun generateKeyPair(): KeyPair {
		val keypair = KeyPairGenerator.getInstance("RSA")
		keypair.initialize(1024)
		return keypair.generateKeyPair()
	}

	fun getServerIdHash(serverId: String, publicKey: PublicKey, secretKey: SecretKey): ByteArray {
		return digestOperation("SHA-1", serverId.toByteArray(charset("ISO_8859_1")), secretKey.encoded, publicKey.encoded)
	}

	private fun digestOperation(algorithm: String, vararg data: ByteArray): ByteArray {
		val digest = MessageDigest.getInstance(algorithm)
		for (byte in data) {
			digest.update(byte)
		}
		return digest.digest()
	}

	fun decodePublicKey(encodedKey: ByteArray?): PublicKey {
		val keyspec: EncodedKeySpec = X509EncodedKeySpec(encodedKey)
		val keyfactory = KeyFactory.getInstance("RSA")
		return keyfactory.generatePublic(keyspec)
	}

	fun decryptSharedKey(key: PrivateKey, secretKeyEncrypted: ByteArray): SecretKey {
		return SecretKeySpec(decryptData(key, secretKeyEncrypted), "AES")
	}

	fun encryptData(key: Key, data: ByteArray): ByteArray {
		return cipherOperation(1, key, data)
	}

	fun decryptData(key: Key, data: ByteArray): ByteArray {
		return cipherOperation(2, key, data)
	}

	private fun cipherOperation(opMode: Int, key: Key, data: ByteArray): ByteArray {
		return createTheCipherInstance(opMode, key.algorithm, key)?.doFinal(data)
	}

	private fun createTheCipherInstance(opMode: Int, transformation: String, key: Key): Cipher {
		val cipher = Cipher.getInstance(transformation)
		cipher.init(opMode, key)
		return cipher
	}

	fun createNetCipherInstance(opMode: Int, key: Key): Cipher {
		val cipher = Cipher.getInstance("AES/CFB8/NoPadding")
		cipher.init(opMode, key, IvParameterSpec(key.encoded))
		return cipher
	}

}
