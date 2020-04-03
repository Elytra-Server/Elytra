package io.elytra.api.nbt

import java.io.DataOutput

abstract class NbtTag(val name: String?) {
    var parent: NbtTag? = null
        internal set

    abstract val tagType: NbtTagType

    abstract val hasValue: Boolean

    abstract val codec: NbtTagCodec

    abstract fun deepClone(): NbtTag

    override fun toString(): String {
        return toString(DefaultIndent.str, DefaultIndent.indentLevel)
    }

    private fun toString(indentString: String = DefaultIndent.str, indentLevel: Int = DefaultIndent.indentLevel): String {
        val sb = StringBuilder()
        prettyPrint(sb, indentString, indentLevel)
        return sb.toString()
    }

    abstract fun prettyPrint(sb: StringBuilder, indentStr: String, indentLevel: Int) // TODO: protected

    object DefaultIndent {
        var str: String = "  "
        var indentLevel = 0
    }

    abstract class NbtTagCodec {
        abstract val id: Int
        abstract fun serialize(obj: Any, stream: DataOutput)

        open fun deserialize(stream: NbtInputStream): NbtTag {
            val name = stream.readUTF()
            return deserialize(name, stream)
        }

        abstract fun deserialize(name: String?, stream: NbtInputStream): NbtTag
    }
}
