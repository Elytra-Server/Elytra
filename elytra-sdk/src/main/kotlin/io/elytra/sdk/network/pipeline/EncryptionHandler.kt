package io.elytra.sdk.network.pipeline

import io.elytra.sdk.network.utils.NettyEncryptionTranslator
import io.elytra.sdk.utils.cryptManager
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageCodec
import javax.crypto.SecretKey

class EncryptionHandler(private val sharedSecret: SecretKey) : ByteToMessageCodec<ByteBuf>(){

	private val encryptionCodec: NettyEncryptionTranslator = NettyEncryptionTranslator(cryptManager.createNetCipherInstance(1,sharedSecret))
	private val decryptionCodec: NettyEncryptionTranslator = NettyEncryptionTranslator(cryptManager.createNetCipherInstance(2,sharedSecret))

	override fun encode(ctx: ChannelHandlerContext?, msg: ByteBuf, out: ByteBuf) {
		encryptionCodec.cipher(msg,out)
	}

	override fun decode(ctx: ChannelHandlerContext, msg: ByteBuf, out: MutableList<Any>) {
		out.add(decryptionCodec.decipher(ctx,msg))
	}

}
