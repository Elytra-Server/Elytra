package io.elytra.sdk.network.protocol.handlers.login

import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.SessionState
import io.elytra.sdk.network.protocol.handlers.ElytraMessageHandler
import io.elytra.sdk.network.protocol.message.login.EncryptionResponseMessage
import io.elytra.sdk.server.Elytra
import io.elytra.sdk.network.utils.cryptManager
import org.apache.commons.lang3.Validate
import java.math.BigInteger
import java.security.PrivateKey

class EncryptionResponseHandler : ElytraMessageHandler<EncryptionResponseMessage>() {
	override fun handle(session: NetworkSession, message: EncryptionResponseMessage) {
		Validate.validState(session.sessionState == SessionState.KEY, "Unexpected key packet")

		val privatekey: PrivateKey = Elytra.server.keypair.private
		session.sessionState = SessionState.AUTHENTICATING

		val clientToken: ByteArray = cryptManager.decryptData(privatekey,message.secretKeyEncrypted)

		check(!session.verifyToken.contentEquals(clientToken)) { "Invalid nonce!" }

		val secretKey = cryptManager.decryptSharedKey(privatekey,message.secretKeyEncrypted)

		//TODO Add encryption on encode & decode
		session.sessionState = SessionState.ACCEPTED
		session.enableEncryption(secretKey)

		val serverId: String = BigInteger(cryptManager.getServerIdHash("", Elytra.server.keypair.public, secretKey)).toString(16)

		val gameProfile = Elytra.server.sessionService.hasJoinedServer(session.gameProfile, serverId)

		if(gameProfile != null){
			println("UUID of player ${gameProfile.name} is ${gameProfile.id}")
			session.gameProfile = gameProfile
			session.sessionState = SessionState.READY_TO_ACCEPT
		}else{
			//TODO Need add verification for only member premium from server configuration
			//session.disconnect("Username ${session.gameProfile!!.name} tried to join with an invalid session")
		}

		//session.tryLogin()
	}

}
