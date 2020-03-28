package io.elytra.sdk.network.utils

import io.netty.buffer.ByteBuf
import io.netty.handler.codec.DecoderException
import io.netty.handler.codec.EncoderException
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.experimental.and

val ByteBuf.minecraft get() = MinecraftByteBuf(this)

inline class MinecraftByteBuf(private val byteBuf: ByteBuf){

	fun writeEnumValue(value: Enum<*>) {
		writeVarIntToBuffer(value.ordinal)
	}

	fun <T : Enum<T>?> readEnumValue(enumClass: Class<T>): T {
		return (enumClass.enumConstants as Array<*>)[readVarIntFromBuffer()] as T
	}

	fun writeVarIntToBuffer(`in`: Int) {
		var input = `in`
		while (input and -128 != 0) {
			byteBuf.writeByte(input.and(127) or 128)
			input = input ushr 7
		}
		byteBuf.writeByte(input)
	}

	fun readVarIntFromBuffer(): Int {
		var i = 0
		var j = 0

		while (true) {
			val b0: Byte = byteBuf.readByte()
			i = i or (b0.and(127).toInt()) shl j++ * 7

			if (j > 5) {
				throw RuntimeException("VarInt too big")
			}

			if ((b0.and(128.toByte())).toInt() != 128) {
				break
			}
		}

		return i
	}

	fun writeUuid(uuid: UUID) {
		byteBuf.writeLong(uuid.mostSignificantBits)
		byteBuf.writeLong(uuid.leastSignificantBits)
	}

	fun readUuid(): UUID? {
		return UUID(byteBuf.readLong(), byteBuf.readLong())
	}

	fun writeString(string: String): ByteBuf? {
		val bytes = string.toByteArray(StandardCharsets.UTF_8)

		return if (bytes.size > 32767) {
			throw EncoderException("String too big (was " + bytes.size + " bytes encoded, max " + 32767 + ")")
		} else {
			writeVarIntToBuffer(bytes.size)
			byteBuf.writeBytes(bytes)
		}
	}

	fun readStringFromBuffer(maxLength: Int): String {
		val i: Int = readVarIntFromBuffer()
		return if (i > maxLength * 4) {
			throw DecoderException("The received encoded string buffer length is longer than maximum allowed (" + i + " > " + maxLength * 4 + ")")
		} else if (i < 0) {
			throw DecoderException("The received encoded string buffer length is less than zero! Weird string!")
		} else {
			val stringBuffer = byteBuf.toString(byteBuf.readerIndex(), i, StandardCharsets.UTF_8)
			byteBuf.readerIndex(byteBuf.readerIndex() + i)
			if (stringBuffer.length > maxLength) {
				throw DecoderException("The received string length is longer than maximum allowed ($i > $maxLength)")
			} else {
				stringBuffer
			}
		}
	}
}
