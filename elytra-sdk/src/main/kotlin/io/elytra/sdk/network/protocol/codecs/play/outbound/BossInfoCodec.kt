package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.outbound.BossInfoMessage
import io.elytra.sdk.network.protocol.message.play.outbound.BossInfoMessage.Operation
import io.elytra.sdk.network.utils.*
import io.netty.buffer.ByteBuf

class BossInfoCodec : Codec<BossInfoMessage> {

    override fun encode(buffer: ByteBuf, message: BossInfoMessage): ByteBuf {
        buffer.writeUuid(message.uuid)
        buffer.writeEnumValue(message.operation)

        when (message.operation) {
            Operation.ADD -> {
                buffer.writeTextComponent(message.name!!)
                buffer.writeFloat(message.percent)
                buffer.writeEnumValue(message.color)
                buffer.writeEnumValue(message.overlay)
                buffer.writeByte(message.getFlags())
            }
            Operation.REMOVE -> {
            }
            Operation.UPDATE_PCT -> buffer.writeFloat(message.percent)
            Operation.UPDATE_NAME -> buffer.writeTextComponent(message.name!!)
            Operation.UPDATE_STYLE -> {
                buffer.writeEnumValue(message.color)
                buffer.writeEnumValue(message.overlay)
            }
            Operation.UPDATE_PROPERTIES -> buffer.writeByte(message.getFlags())
        }

        return buffer
    }

    override fun decode(buffer: ByteBuf): BossInfoMessage {
        val uuid = buffer.readUuid()
        val operation = buffer.readEnumValue<Operation>()

        val bossInfoMessage = BossInfoMessage(uuid, operation)

        when (operation) {
            Operation.ADD -> {
                bossInfoMessage.name = buffer.readTextComponent()
                bossInfoMessage.percent = buffer.readFloat()
                bossInfoMessage.color = buffer.readEnumValue()
                bossInfoMessage.overlay = buffer.readEnumValue()
                bossInfoMessage.setFlags(buffer.readUnsignedByte().toInt())
            }
            Operation.REMOVE -> {
            }
            Operation.UPDATE_PCT -> bossInfoMessage.percent = buffer.readFloat()
            Operation.UPDATE_NAME -> bossInfoMessage.name = buffer.readTextComponent()
            Operation.UPDATE_STYLE -> {
                bossInfoMessage.color = buffer.readEnumValue()
                bossInfoMessage.overlay = buffer.readEnumValue()
            }
            Operation.UPDATE_PROPERTIES -> bossInfoMessage.setFlags(buffer.readUnsignedByte().toInt())
        }

        return bossInfoMessage
    }
}
