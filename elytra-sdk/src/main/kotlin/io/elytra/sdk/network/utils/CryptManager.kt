package io.elytra.sdk.network.utils

import java.security.*
import java.security.spec.EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object CryptManager {
    fun createNewSharedKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(128)
        return keyGenerator.generateKey()
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
        val keySpec: EncodedKeySpec = X509EncodedKeySpec(encodedKey)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(keySpec)
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
