package io.elytra.api.nbt.tags

import io.elytra.api.nbt.NbtInputStream
import java.io.DataOutput
import java.io.DataOutputStream

class NbtLong(name: String?, val value: Long) : NbtValueTag(name) {
    override val tagType = NbtTagType.LONG
    override val codec = Codec

    override fun deepClone(): NbtTag {
        return NbtLong(name, value)
    }

    override fun prettyValueStr(): String {
        return value.toString()
    }

    object Codec : NbtTagCodec() {
        override val id = 4

        override fun serialize(obj: Any, stream: DataOutput) {
            if(obj !is NbtLong) throw IllegalArgumentException()
            stream.writeLong(obj.value)
        }
        override fun deserialize(name: String?, stream: NbtInputStream): NbtTag {
            val value = stream.readLong()
            return NbtLong(name, value)
        }
    }
}
