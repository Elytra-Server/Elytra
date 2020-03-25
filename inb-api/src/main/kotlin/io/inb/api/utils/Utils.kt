package io.inb.api.utils

import io.netty.buffer.ByteBuf
import io.netty.handler.codec.DecoderException
import io.netty.handler.codec.EncoderException
import java.nio.charset.StandardCharsets
import kotlin.experimental.and

object Utils {
	/*fun readString(buf: ByteBuf): String {
		val len = buf.readUnsignedShort()
		val characters = CharArray(len)

		for (i in 0 until len) {
			characters[i] = buf.readChar()
		}

		return String(characters)
	}

	fun writeString(buffer: ByteBuf, string: String) {
		val chars = string.toCharArray()
		buffer.writeShort(chars.size)

		for (c in chars) {
			buffer.writeChar(c.toInt())
		}
	}*/

	fun writeString(buffer: ByteBuf, string: String): ByteBuf?{
		val bytes = string.toByteArray(StandardCharsets.UTF_8)
		return if (bytes.size > 32767) {
			throw EncoderException("String too big (was " + bytes.size + " bytes encoded, max " + 32767 + ")")
		} else {
			writeVarIntToBuffer(buffer,bytes.size)
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
			val s = buffer.toString(buffer.readerIndex(), i, StandardCharsets.UTF_8)
			buffer.readerIndex(buffer.readerIndex() + i)
			if (s.length > maxLength) {
				throw DecoderException("The received string length is longer than maximum allowed ($i > $maxLength)")
			} else {
				s
			}
		}
	}

	fun writeVarIntToBuffer(buffer: ByteBuf, input: Int){
		var input = input
		while (input and -128 != 0) {
			buffer.writeByte(input and 127 or 128)
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
}
