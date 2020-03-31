package io.elytra.api.nbt.tags

import io.elytra.api.nbt.NbtInputStream
import java.io.DataOutput
import java.io.DataOutputStream

class NbtByte(name: String?, val value: Int) : NbtValueTag(name) {
    override val tagType = NbtTagType.BYTE
    override val codec = Codec

    override fun deepClone(): NbtTag {
        return NbtByte(name, value)
    }

    override fun prettyValueStr(): String {
        return value.toString()
    }

    object Codec : NbtTagCodec() {
        override val id = 1
        override fun serialize(obj: Any, stream: DataOutput) {
            if(obj !is NbtByte) throw IllegalArgumentException()
            stream.write(obj.value)
        }
        override fun deserialize(name: String?, stream: NbtInputStream): NbtTag {
            val value = stream.readByte()
            return NbtByte(name, value.toInt())
        }
    }
}
