package io.elytra.api.nbt.tags

import io.elytra.api.nbt.NbtInputStream
import java.io.DataOutput

class NbtLongArray(name: String?, val value: LongArray) : NbtValueTag(name) {
    override val tagType = NbtTagType.LONG_ARRAY
    override val codec = Codec

    override fun deepClone(): NbtTag {
        return NbtLongArray(name, value.clone())
    }

    override fun prettyValueStr(): String {
        return "[${value.size} longs]"
    }

    object Codec : NbtTagCodec() {
        override val id = 11

        override fun serialize(obj: Any, stream: DataOutput) {
            if (obj !is NbtLongArray) throw IllegalArgumentException()
            stream.writeInt(obj.value.size)
            obj.value.forEach { stream.writeLong(it) }
        }
        override fun deserialize(name: String?, stream: NbtInputStream): NbtTag {
            val len = stream.readInt()
            val array = LongArray(len)
            for (i in 0 until len)
                array[i] = stream.readLong()
            return NbtLongArray(name, array)
        }
    }
}
