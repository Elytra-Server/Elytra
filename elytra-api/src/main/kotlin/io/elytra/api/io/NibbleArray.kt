package io.elytra.api.io

/**
 * Represents a [ByteArray] with half size
 */
class NibbleArray(val arr: ByteArray) {
    constructor(size: Int) : this(ByteArray(size / 2))

    operator fun get(index: Int): Int {
        val value = arr[index / 2].toInt()
        return if (index % 2 == 0) (value and 0x0f) else ((value and 0xf0) shr 4)
    }

    operator fun set(index: Int, value: Int) {
        val nibble = value and 0xf
        val halfIndex = index / 2
        val previous = arr[halfIndex].toInt()
        if (index % 2 == 0) {
            arr[halfIndex] = ((previous and 0xf0) or nibble).toByte()
        } else {
            arr[halfIndex] = ((previous and 0x0f) or (nibble shl 4)).toByte()
        }
    }

    val size = arr.size * 2
}
