package io.elytra.sdk.network.protocol.packets

import io.elytra.sdk.network.protocol.ProtocolInfo
import io.elytra.sdk.network.protocol.codecs.DisconnectCodec
import io.elytra.sdk.network.protocol.codecs.login.inbound.EncryptionResponseCodec
import io.elytra.sdk.network.protocol.codecs.login.inbound.LoginStartCodec
import io.elytra.sdk.network.protocol.codecs.login.outbound.EncryptionRequestCodec
import io.elytra.sdk.network.protocol.codecs.login.outbound.LoginSuccessCodec
import io.elytra.sdk.network.protocol.codecs.play.outbound.SetCompressionCodec
import io.elytra.sdk.network.protocol.handlers.login.EncryptionResponseHandler
import io.elytra.sdk.network.protocol.handlers.login.LoginStartHandler
import io.elytra.sdk.network.protocol.message.login.EncryptionRequestMessage
import io.elytra.sdk.network.protocol.message.login.EncryptionResponseMessage
import io.elytra.sdk.network.protocol.message.login.LoginStartMessage
import io.elytra.sdk.network.protocol.message.login.LoginSuccessMessage
import io.elytra.sdk.network.protocol.message.play.outbound.DisconnectMessage
import io.elytra.sdk.network.protocol.message.play.outbound.SetCompressionMessage

class LoginPacket : BasicPacket("LOGIN", 5) {

    init {
        inbound(
            ProtocolInfo.LOGIN_START,
            LoginStartMessage::class.java,
            LoginStartCodec::class.java,
            LoginStartHandler::class.java
        )
        inbound(
            ProtocolInfo.ENCRYPTION_RESPONSE,
            EncryptionResponseMessage::class.java,
            EncryptionResponseCodec::class.java,
            EncryptionResponseHandler::class.java
        )

        outbound(ProtocolInfo.LOGIN_DISCONNECT, DisconnectMessage::class.java, DisconnectCodec::class.java)
        outbound(ProtocolInfo.ENCRYPTION_REQUEST, EncryptionRequestMessage::class.java, EncryptionRequestCodec::class.java)
        outbound(ProtocolInfo.LOGIN_SUCCESS, LoginSuccessMessage::class.java, LoginSuccessCodec::class.java)
        outbound(ProtocolInfo.O_SET_COMPRESSION, SetCompressionMessage::class.java, SetCompressionCodec::class.java)
    }
}
