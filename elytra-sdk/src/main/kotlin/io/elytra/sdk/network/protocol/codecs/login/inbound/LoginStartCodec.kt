package io.elytra.sdk.network.protocol.codecs.login.inbound

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import com.mojang.authlib.GameProfile
import io.elytra.sdk.network.protocol.message.login.LoginStartMessage
import io.elytra.sdk.network.utils.minecraft
import io.netty.buffer.ByteBuf

class LoginStartCodec : Codec<LoginStartMessage> {
	override fun decode(buffer: ByteBuf): LoginStartMessage {
		return LoginStartMessage(GameProfile(null,ByteBufUtils.readUTF8(buffer)))
	}

	override fun encode(buffer: ByteBuf, message: LoginStartMessage): ByteBuf {
		ByteBufUtils.writeUTF8(buffer,message.gameProfile.name)
		return buffer
	}
}
