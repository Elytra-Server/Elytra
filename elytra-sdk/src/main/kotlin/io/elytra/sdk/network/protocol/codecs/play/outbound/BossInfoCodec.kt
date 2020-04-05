package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.BossInfoMessage
import io.elytra.sdk.network.protocol.message.play.BossInfoMessage.Operation
import io.elytra.sdk.network.utils.minecraft
import io.netty.buffer.ByteBuf

class BossInfoCodec : Codec<BossInfoMessage> {

    override fun encode(buffer: ByteBuf, message: BossInfoMessage): ByteBuf {
        buffer.minecraft.writeUuid(message.uuid)
        buffer.minecraft.writeEnumValue(message.operation)

        when (message.operation) {
            Operation.ADD -> {
                buffer.minecraft.writeTextComponent(message.name!!)
                buffer.writeFloat(message.percent)
                buffer.minecraft.writeEnumValue(message.color)
                buffer.minecraft.writeEnumValue(message.overlay)
                buffer.writeByte(message.getFlags())
            }
            Operation.REMOVE -> {}
            Operation.UPDATE_PCT -> buffer.writeFloat(message.percent)
            Operation.UPDATE_NAME -> buffer.minecraft.writeTextComponent(message.name!!)
            Operation.UPDATE_STYLE -> {
                buffer.minecraft.writeEnumValue(message.color)
                buffer.minecraft.writeEnumValue(message.overlay)
            }
            Operation.UPDATE_PROPERTIES -> buffer.writeByte(message.getFlags())
        }

        return buffer
    }

    override fun decode(buffer: ByteBuf): BossInfoMessage {
        val uuid = buffer.minecraft.readUuid()!!
        val operation = buffer.minecraft.readEnumValue(Operation::class.java)

        val bossInfoMessage = BossInfoMessage(uuid, operation)

        when (operation) {
            Operation.ADD -> {
                bossInfoMessage.name = buffer.minecraft.readTextComponent()
                bossInfoMessage.percent = buffer.readFloat()
                bossInfoMessage.color = buffer.minecraft.readEnumValue(BossInfoMessage.Color::class.java)
                bossInfoMessage.overlay = buffer.minecraft.readEnumValue(BossInfoMessage.Overlay::class.java)
                bossInfoMessage.setFlags(buffer.readUnsignedByte().toInt())
            }
            Operation.REMOVE -> {}
            Operation.UPDATE_PCT -> bossInfoMessage.percent = buffer.readFloat()
            Operation.UPDATE_NAME -> bossInfoMessage.name = buffer.minecraft.readTextComponent()
            Operation.UPDATE_STYLE -> {
                bossInfoMessage.color = buffer.minecraft.readEnumValue(BossInfoMessage.Color::class.java)
                bossInfoMessage.overlay = buffer.minecraft.readEnumValue(BossInfoMessage.Overlay::class.java)
            }
            Operation.UPDATE_PROPERTIES -> bossInfoMessage.setFlags(buffer.readUnsignedByte().toInt())
        }

        return bossInfoMessage
    }
}
