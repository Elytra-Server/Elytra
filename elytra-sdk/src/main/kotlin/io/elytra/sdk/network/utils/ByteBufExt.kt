package io.elytra.sdk.network.utils

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.api.chat.TextComponent
import io.elytra.api.world.Position
import io.elytra.sdk.utils.fromJson
import io.netty.buffer.ByteBuf
import io.netty.handler.codec.DecoderException
import io.netty.handler.codec.EncoderException
import java.nio.charset.StandardCharsets
import java.util.*

fun ByteBuf.writeEnumValue(value: Enum<*>) {
    ByteBufUtils.writeVarInt(this, value.ordinal)
}

// Formula from https://wiki.vg/Data_types
fun ByteBuf.readPosition(): Position {
    val positionFromBuffer: Long = readLong()

    val x = positionFromBuffer shr 38
    val y = positionFromBuffer and 0xFFF
    val z = positionFromBuffer shl 26 shr 38

    return Position(x.toDouble(), y.toDouble(), z.toDouble())
}

// Formula from https://wiki.vg/Data_types
fun ByteBuf.writePosition(position: Position) {
    val x = position.x.toInt()
    val y = position.y.toInt()
    val z = position.z.toInt()

    writeLong((x and 0x3ffffff shl 38 or (z and 0x3FFFFFF shl 12) or (y and 0xFFF)).toLong())
}

inline fun <reified T : Enum<T>> ByteBuf.readEnumValue(): T {
    return enumValues<T>()[ByteBufUtils.readVarInt(this)]
}

fun ByteBuf.writeUuid(uuid: UUID) {
    writeLong(uuid.mostSignificantBits)
    writeLong(uuid.leastSignificantBits)
}

fun ByteBuf.readUuid(): UUID {
    return UUID(readLong(), readLong())
}

fun ByteBuf.writeTextComponent(textComponent: TextComponent) {
    writeString(textComponent.toJson())
}

fun ByteBuf.readTextComponent(): TextComponent {
    return fromJson(readString(256))
}

fun ByteBuf.writeString(string: String): ByteBuf? {
    val bytes = string.toByteArray(StandardCharsets.UTF_8)

    return if (bytes.size > 32767) {
        throw EncoderException("String too big (was " + bytes.size + " bytes encoded, max " + 32767 + ")")
    } else {
        ByteBufUtils.writeVarInt(this, bytes.size)
        writeBytes(bytes)
    }
}

fun ByteBuf.readString(maxLength: Int): String {
    val i: Int = ByteBufUtils.readVarInt(this)
    return if (i > maxLength * 4) {
        throw DecoderException("The received encoded string buffer length is longer than maximum allowed (" + i + " > " + maxLength * 4 + ")")
    } else if (i < 0) {
        throw DecoderException("The received encoded string buffer length is less than zero! Weird string!")
    } else {
        val stringBuffer = toString(readerIndex(), i, StandardCharsets.UTF_8)
        readerIndex(readerIndex() + i)
        if (stringBuffer.length > maxLength) {
            throw DecoderException("The received string length is longer than maximum allowed ($i > $maxLength)")
        } else {
            stringBuffer
        }
    }
}

fun ByteBuf.writeByteArray(array: ByteArray) {
    ByteBufUtils.writeVarInt(this, array.size)
    writeBytes(array)
}

fun ByteBuf.readByteArray(): ByteArray? {
    return this.readByteArray(readableBytes())
}

fun ByteBuf.readByteArray(maxLength: Int): ByteArray? {
    val i: Int = ByteBufUtils.readVarInt(this)
    return if (i > maxLength) {
        throw DecoderException("ByteArray with size $i is bigger than allowed $maxLength")
    } else {
        val abyte = ByteArray(i)
        readBytes(abyte)
        abyte
    }
}
