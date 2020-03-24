package io.inb.api.utils

import io.netty.buffer.ByteBuf

object Utils {
	fun readString(buf: ByteBuf): String {
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
	}
}
