package io.inb.api.utils

import io.netty.buffer.ByteBuf
import io.netty.handler.codec.EncoderException
import java.nio.charset.StandardCharsets

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

	fun writeVarIntToBuffer(buffer: ByteBuf, input: Int){
		var input = input
		while (input and -128 != 0) {
			buffer.writeByte(input and 127 or 128)
			input = input ushr 7
		}
		buffer.writeByte(input)
	}

	fun writeString(buffer: ByteBuf, string: String): ByteBuf?{
		val bytes = string.toByteArray(StandardCharsets.UTF_8)
		return if (bytes.size > 32767) {
			throw EncoderException("String too big (was " + bytes.size + " bytes encoded, max " + 32767 + ")")
		} else {
			writeVarIntToBuffer(buffer,bytes.size)
			buffer.writeBytes(bytes)
		}
	}

}
