package io.elytra.api.nbt.tags

import io.elytra.api.nbt.NbtInputStream
import java.io.DataOutput

class NbtInt(name: String?, val value: Int) : NbtValueTag(name) {
    override val tagType = NbtTagType.INT
    override val codec = Codec

    override fun deepClone(): NbtTag {
        return NbtInt(name, value)
    }

    override fun prettyValueStr(): String {
        return value.toString()
    }

    object Codec : NbtTagCodec() {
        override val id = 3

        override fun serialize(obj: Any, stream: DataOutput) {
            if (obj !is NbtInt) throw IllegalArgumentException()
            stream.writeInt(obj.value)
        }
        override fun deserialize(name: String?, stream: NbtInputStream): NbtTag {
            val value = stream.readInt()
            return NbtInt(name, value)
        }
    }
}
