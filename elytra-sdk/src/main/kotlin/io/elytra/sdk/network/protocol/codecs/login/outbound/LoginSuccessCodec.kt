package io.elytra.sdk.network.protocol.codecs.login.outbound

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.login.LoginSuccessMessage
import io.netty.buffer.ByteBuf

class LoginSuccessCodec : OutboundCodec<LoginSuccessMessage>() {
    override fun encode(buffer: ByteBuf, message: LoginSuccessMessage): ByteBuf {
        ByteBufUtils.writeUTF8(buffer, message.gameProfile.id.toString())
        ByteBufUtils.writeUTF8(buffer, message.gameProfile.name)

        return buffer
    }
}
