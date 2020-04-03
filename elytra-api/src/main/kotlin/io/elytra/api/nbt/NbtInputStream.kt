package io.elytra.api.nbt

import io.elytra.api.io.LittleEndianDataInputStream
import io.elytra.api.nbt.tags.NbtTag
import java.io.*

class NbtInputStream(stream: DataInput) : DataInput by stream {
    constructor(bytes: ByteArray) : this(pickInputStream(bytes))
    constructor(stream: InputStream) : this(DataInputStream(stream) as DataInput)

    fun readTag(): NbtTag? {
        val id = try {
            readUnsignedByte()
        } catch (ex: EOFException) {
            return null
        }

        val tagFactory = requireNotNull(NbtTagType.idToCodec[id]) { "Unknown id $id while reading nbt" }

        return tagFactory.deserialize(this)
    }

    companion object {
        private fun pickInputStream(bytes: ByteArray): DataInput {
            val bs = LittleEndianDataInputStream(ByteArrayInputStream(bytes))

            return DataInputStream(bs)
        }
    }
}
