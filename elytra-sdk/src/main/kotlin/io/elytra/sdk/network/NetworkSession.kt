package io.elytra.sdk.network

import com.flowpowered.network.Message
import com.flowpowered.network.MessageHandler
import com.flowpowered.network.protocol.AbstractProtocol
import com.flowpowered.network.session.BasicSession
import com.mojang.authlib.GameProfile
import io.elytra.api.utils.Asyncable
import io.elytra.api.utils.Tickable
import io.elytra.sdk.network.pipeline.CodecsHandler
import io.elytra.sdk.network.pipeline.EncryptionHandler
import io.elytra.sdk.network.protocol.PacketProvider
import io.elytra.sdk.network.protocol.message.DisconnectMessage
import io.elytra.sdk.network.protocol.message.play.KeepAliveMessage
import io.elytra.sdk.network.protocol.packets.BasicPacket
import io.elytra.sdk.network.protocol.packets.HandshakePacket
import io.elytra.sdk.network.protocol.packets.Protocol
import io.elytra.sdk.server.Elytra
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandler
import io.netty.handler.codec.CodecException
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque
import javax.crypto.SecretKey
import kotlin.random.Random

class NetworkSession(
    channel: Channel,
    var protocol: Protocol = Protocol.HANDSHAKE,
    @Volatile private var disconnected: Boolean = false,
    private var packetProvider: PacketProvider = PacketProvider(),
    private val messageQueue: BlockingQueue<Message> = LinkedBlockingDeque()
) : BasicSession(channel, HandshakePacket()), Tickable {

    // TODO Refactor this and make the ping functional
    private var connectionTimer: Int = 0
    var sessionState: SessionState = SessionState.HELLO
    val verifyToken: ByteArray = Random.nextBytes(4)
    var gameProfile: GameProfile? = null
    var encrypted: Boolean = false
    private var networkTickCount: Int = 0
    private var keepAliveId: Long = 0
    private var lastPingTime: Long = 0
    private var lastSentPingPacket: Long = 0
    var ping: Int = 0

    override fun send(message: Message?) {
        if (Elytra.server.debug && (message !is KeepAliveMessage)) {
            println("OUT $message")
        }
        super.send(message)
    }

    override fun sendWithFuture(message: Message?): ChannelFuture? {
        if (!isActive) {
            return null
        }
        return super.sendWithFuture(message)
    }

    override fun disconnect() {
        disconnect("No reason specified.")
    }

    override fun tick() {
        when (protocol) {
            Protocol.LOGIN -> {
                if (sessionState == SessionState.READY_TO_ACCEPT) {
                    tryLogin()
                } else if (sessionState == SessionState.DELAY_ACCEPT) {
                    println("ACCEPT")
                }
                if (connectionTimer++ == 600) {
                    disconnect("multiplayer.disconnect.slow_login")
                }
            }
            Protocol.PLAY -> {
                ++networkTickCount
                if (disconnected) disconnect("Exit the game")
                if (this.networkTickCount - this.lastSentPingPacket > 40L) {
                    this.lastSentPingPacket = this.networkTickCount.toLong()
                    lastPingTime = System.currentTimeMillis()
                    this.keepAliveId = lastPingTime
                    send(KeepAliveMessage(this.keepAliveId))
                }
            }
        }
        var message: Message?
        while (messageQueue.poll().also { message = it } != null) {
            if (disconnected) break
            super.messageReceived(message)
        }
    }

    fun protocol(protocol: Protocol) {
        this.protocol = protocol
        when (protocol) {
            Protocol.LOGIN -> setProtocol(packetProvider.loginPacket)
            Protocol.PLAY -> setProtocol(packetProvider.playPacket)
            Protocol.STATUS -> setProtocol(packetProvider.statusPacket)
            Protocol.HANDSHAKE -> setProtocol(packetProvider.handshakePacket)
            else -> disconnect()
        }
    }

    public override fun setProtocol(protocol: AbstractProtocol?) {
        updatePipeline("codecs", CodecsHandler(protocol as BasicPacket))
        super.setProtocol(protocol)
    }

    override fun onInboundThrowable(throwable: Throwable?) {
        if (throwable is CodecException) {
            println("Error in inbound network: $throwable")
            return
        }

        disconnect("decode error: ${throwable?.message}")
    }

    override fun onHandlerThrowable(message: Message?, handle: MessageHandler<*, *>?, throwable: Throwable?) {
        println("Error while handling $message (${handle?.javaClass?.simpleName}) - $throwable")
    }

    override fun messageReceived(message: Message) {
        if (Elytra.server.debug && (message !is KeepAliveMessage)) {
            println("IN $message")
        }
        if (message is Asyncable) {
            super.messageReceived(message)
            return
        }
        messageQueue.add(message)
    }

    override fun onDisconnect() {
        disconnected = true
    }

    fun disconnect(reason: String) {
        if (protocol == Protocol.PLAY) {
            Elytra.server.playerRegistry.remove(Elytra.server.playerRegistry.get(gameProfile!!.name)!!)
        }
        sendWithFuture(DisconnectMessage(reason))?.addListener(ChannelFutureListener.CLOSE)
        Elytra.server.sessionRegistry.remove(this)
    }

    private fun updatePipeline(key: String, handler: ChannelHandler) {
        channel.pipeline().replace(key, key, handler)
    }

    fun enableEncryption(sharedSecret: SecretKey) {
        encrypted = true
        channel.pipeline().addFirst("decrypt", EncryptionHandler(sharedSecret))
    }

    // TODO Then put this somewhere else maybe
    private fun tryLogin() {
        if (!gameProfile!!.isComplete) {
            gameProfile = GameProfile(UUID.nameUUIDFromBytes(("OfflinePlayer:" + gameProfile!!.name.toLowerCase()).toByteArray(StandardCharsets.UTF_8)), gameProfile!!.name)
        }
        Elytra.server.playerRegistry.initialize(this, gameProfile!!)
    }

    fun ping(id: Long) {
        val i = (System.currentTimeMillis() - lastPingTime).toInt()
        ping = ((ping * 3 + i) / 4)
    }
}
