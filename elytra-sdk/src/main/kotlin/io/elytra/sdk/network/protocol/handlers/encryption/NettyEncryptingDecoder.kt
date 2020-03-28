package io.elytra.sdk.network.protocol.handlers.encryption

import io.elytra.sdk.network.utils.NettyEncryptionTranslator
import io.elytra.sdk.utils.cryptManager
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import io.netty.handler.codec.MessageToMessageDecoder
import javax.crypto.SecretKey

class NettyEncryptingDecoder(sharedSecret: SecretKey) : MessageToMessageDecoder<ByteBuf>() {

	private val decryptionCodec: NettyEncryptionTranslator = NettyEncryptionTranslator(cryptManager.createNetCipherInstance(2,sharedSecret))

	override fun decode(ctx: ChannelHandlerContext, msg: ByteBuf, out: MutableList<Any>) {
		decryptionCodec.decipher(ctx,msg)
	}
}
