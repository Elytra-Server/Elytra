package io.elytra.sdk.network.protocol.codecs.login.outbound

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import com.mojang.authlib.GameProfile
import io.elytra.sdk.network.protocol.message.login.LoginSuccessMessage
import io.elytra.sdk.network.utils.minecraft
import io.netty.buffer.ByteBuf

class LoginSuccessCodec : Codec<LoginSuccessMessage> {

    override fun decode(buffer: ByteBuf): LoginSuccessMessage {
        val uuid = buffer.minecraft.readUuid()
        val username = buffer.minecraft.readString(16)
        return LoginSuccessMessage(GameProfile(uuid, username))
    }

    override fun encode(buffer: ByteBuf, message: LoginSuccessMessage): ByteBuf {
        ByteBufUtils.writeUTF8(buffer, message.gameProfile.id.toString())
        ByteBufUtils.writeUTF8(buffer, message.gameProfile.name)
        return buffer
    }
}
