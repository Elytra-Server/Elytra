package io.elytra.api.nbt.tags

import io.elytra.api.nbt.NbtInputStream
import java.io.DataOutput

class NbtEnd : NbtTag(null) {
    override val tagType = NbtTagType.END
    override val hasValue = false
    override val codec = Codec

    override fun deepClone(): NbtTag {
        return NbtEnd()
    }

    override fun prettyPrint(sb: StringBuilder, indentStr: String, indentLevel: Int) {
    }

    object Codec : NbtTagCodec() {
        override val id = 0

        override fun serialize(obj: Any, stream: DataOutput) {
        }
        override fun deserialize(stream: NbtInputStream): NbtTag {
            return deserialize(null, stream)
        }
        override fun deserialize(name: String?, stream: NbtInputStream): NbtTag {
            return NbtEnd()
        }
    }
}
