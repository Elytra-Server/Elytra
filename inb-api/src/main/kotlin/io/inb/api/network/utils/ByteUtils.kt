package io.inb.api.network.utils

import io.netty.buffer.ByteBuf
import io.netty.handler.codec.DecoderException
import io.netty.handler.codec.EncoderException
import java.nio.charset.StandardCharsets
import kotlin.experimental.and

object ByteUtils {
	fun writeString(buffer: ByteBuf, string: String): ByteBuf? {
		val bytes = string.toByteArray(StandardCharsets.UTF_8)

		return if (bytes.size > 32767) {
			throw EncoderException("String too big (was " + bytes.size + " bytes encoded, max " + 32767 + ")")
		} else {
			writeVarIntToBuffer(buffer, bytes.size)
			buffer.writeBytes(bytes)
		}
	}

	fun readStringFromBuffer(buffer: ByteBuf, maxLength: Int): String {
		val i: Int = readVarIntFromBuffer(buffer)

		return if (i > maxLength * 4) {
			throw DecoderException("The received encoded string buffer length is longer than maximum allowed (" + i + " > " + maxLength * 4 + ")")
		} else if (i < 0) {
			throw DecoderException("The received encoded string buffer length is less than zero! Weird string!")
		} else {
			val stringBuffer = buffer.toString(buffer.readerIndex(), i, StandardCharsets.UTF_8)
			buffer.readerIndex(buffer.readerIndex() + i)
			if (stringBuffer.length > maxLength) {
				throw DecoderException("The received string length is longer than maximum allowed ($i > $maxLength)")
			} else {
				stringBuffer
			}
		}
	}

	fun writeVarIntToBuffer(buffer: ByteBuf, `in`: Int) {
		var input = `in`

		while (input and -128 != 0) {
			buffer.writeByte(input.and(127) or 128)
			input = input ushr 7
		}

		buffer.writeByte(input)
	}

	fun readVarIntFromBuffer(buffer: ByteBuf): Int {
		var i = 0
		var j = 0

		while (true) {
			val b0: Byte = buffer.readByte()
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

	private val HEX_ARRAY = "0123456789ABCDEF".toCharArray()

	fun bytesToHex(bytes: ByteArray): String? {
		val hexChars = CharArray(bytes.size * 2)

		for (j in bytes.indices) {
			val v = (bytes[j] and 0xFF.toByte()).toInt()
			hexChars[j * 2] = HEX_ARRAY[v ushr 4]
			hexChars[j * 2 + 1] = HEX_ARRAY[v and 0x0F]
		}

		return String(hexChars)
	}
}
