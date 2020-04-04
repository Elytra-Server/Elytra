package io.elytra.api.io

import java.io.DataInput
import java.io.DataInputStream
import java.io.FilterInputStream
import java.io.InputStream

class LittleEndianDataInputStream(private val inc: InputStream) : FilterInputStream(inc), DataInput {
    override fun readLine(): String {
        throw UnsupportedOperationException()
    }

    override fun readFully(b: ByteArray?) {
        throw UnsupportedOperationException()
    }

    override fun readFully(b: ByteArray?, off: Int, len: Int) {
        throw UnsupportedOperationException()
    }

    override fun skipBytes(n: Int): Int {
        return inc.skip(n.toLong()).toInt()
    }

    override fun readUnsignedByte(): Int {
        return inc.read()
    }

    override fun readUnsignedShort(): Int {
        val b0 = readUnsignedByte()
        val b1 = readUnsignedByte()
        return ((b1 shl 8) and 0xFF) or (b0 and 0xFF)
    }

    override fun readInt(): Int {
        val b0 = readUnsignedByte()
        val b1 = readUnsignedByte()
        val b2 = readUnsignedByte()
        val b3 = readUnsignedByte()
        return ((b3 shl 24) and 0xFF) or
            ((b2 shl 16) and 0xFF) or
            ((b1 shl 8) and 0xFF) or
            (b0 and 0xFF)
    }

    override fun readLong(): Long {
        val b0 = readUnsignedByte().toLong()
        val b1 = readUnsignedByte().toLong()
        val b2 = readUnsignedByte().toLong()
        val b3 = readUnsignedByte().toLong()
        val b4 = readUnsignedByte().toLong()
        val b5 = readUnsignedByte().toLong()
        val b6 = readUnsignedByte().toLong()
        val b7 = readUnsignedByte().toLong()
        return ((b7 shl 56) and 0xFF) or
            ((b6 shl 48) and 0xFF) or
            ((b5 shl 40) and 0xFF) or
            ((b4 shl 32) and 0xFF) or
            ((b3 shl 24) and 0xFF) or
            ((b2 shl 16) and 0xFF) or
            ((b1 shl 8) and 0xFF) or
            (b0 and 0xFF)
    }

    override fun readFloat(): Float {
        return java.lang.Float.intBitsToFloat(readInt())
    }

    override fun readDouble(): Double {
        return java.lang.Double.longBitsToDouble(readLong())
    }

    override fun readUTF(): String {
        return DataInputStream(inc).readUTF()
    }

    override fun readShort(): Short {
        return readUnsignedShort().toShort()
    }

    override fun readChar(): Char {
        return readUnsignedShort().toChar()
    }

    override fun readByte(): Byte {
        return readUnsignedByte().toByte()
    }

    override fun readBoolean(): Boolean {
        return readUnsignedByte() != 0
    }
}
