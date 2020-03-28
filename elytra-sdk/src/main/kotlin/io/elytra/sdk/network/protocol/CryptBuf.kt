package io.elytra.sdk.network.protocol

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import java.nio.ByteBuffer
import java.security.GeneralSecurityException
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.ShortBufferException
import javax.crypto.spec.IvParameterSpec


class CryptBuf(private val cipher: Cipher = Cipher.getInstance("AES/CFB8/NoPadding")){
	constructor(mode: Int, sharedSecret: SecretKey) : this() {
		cipher.init(mode, sharedSecret, IvParameterSpec(sharedSecret.encoded))
	}

	fun crypt(msg: ByteBuf, out: MutableList<Any>) {
		val outBuffer: ByteBuffer = ByteBuffer.allocate(msg.readableBytes())
		cipher.update(msg.nioBuffer(), outBuffer)
		outBuffer.flip()
		out.add(Unpooled.wrappedBuffer(outBuffer))
	}
}
