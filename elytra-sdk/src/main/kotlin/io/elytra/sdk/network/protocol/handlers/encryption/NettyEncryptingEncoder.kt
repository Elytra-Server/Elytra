package io.elytra.sdk.network.protocol.handlers.encryption

import io.elytra.sdk.network.utils.NettyEncryptionTranslator
import io.elytra.sdk.utils.cryptManager
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import javax.crypto.SecretKey

class NettyEncryptingEncoder(sharedSecret: SecretKey) : MessageToByteEncoder<ByteBuf>() {
	private val encryptionCodec: NettyEncryptionTranslator = NettyEncryptionTranslator(cryptManager.createNetCipherInstance(1,sharedSecret))

	override fun encode(ctx: ChannelHandlerContext?, msg: ByteBuf, out: ByteBuf) {
		encryptionCodec.cipher(msg,out)
	}
}
