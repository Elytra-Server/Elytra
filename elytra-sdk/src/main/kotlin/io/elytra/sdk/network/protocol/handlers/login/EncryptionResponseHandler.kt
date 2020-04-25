package io.elytra.sdk.network.protocol.handlers.login

import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.SessionState
import io.elytra.sdk.network.protocol.handlers.ElytraMessageHandler
import io.elytra.sdk.network.protocol.message.login.EncryptionResponseMessage
import io.elytra.sdk.network.utils.CryptManager
import io.elytra.sdk.server.Elytra
import java.math.BigInteger
import java.security.PrivateKey
import org.apache.commons.lang3.Validate

class EncryptionResponseHandler : ElytraMessageHandler<EncryptionResponseMessage>() {
    override fun handle(session: NetworkSession, message: EncryptionResponseMessage) {
        Validate.validState(session.sessionState == SessionState.KEY, "Unexpected key packet")

        val privateKey: PrivateKey = Elytra.server.keypair.private
        session.sessionState = SessionState.AUTHENTICATING

        val clientToken: ByteArray = CryptManager.decryptData(privateKey, message.secretKeyEncrypted)

        check(!session.verifyToken.contentEquals(clientToken)) { "Invalid nonce!" }

        val secretKey = CryptManager.decryptSharedKey(privateKey, message.secretKeyEncrypted)

        session.sessionState = SessionState.ACCEPTED
        session.enableEncryption(secretKey)

        val serverId: String = BigInteger(CryptManager.getServerIdHash("", Elytra.server.keypair.public, secretKey)).toString(16)

        val gameProfile = Elytra.server.sessionService.hasJoinedServer(session.gameProfile, serverId)

        if (gameProfile != null) {
            session.gameProfile = gameProfile
            session.sessionState = SessionState.READY_TO_ACCEPT
        } else {
            if (Elytra.server.serverDescriptor.options.onlyPremium) {
                session.disconnect("Username ${session.gameProfile!!.name} tried to join with an invalid session")
            } else {
                session.sessionState = SessionState.READY_TO_ACCEPT
            }
        }
    }
}
