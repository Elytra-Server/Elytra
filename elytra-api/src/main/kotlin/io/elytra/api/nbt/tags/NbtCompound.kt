package io.elytra.api.nbt.tags

import io.elytra.api.nbt.NbtInputStream
import java.io.*
import java.util.*

class NbtCompound(name: String?, tags: Iterable<NbtTag> = emptyList()) : NbtTag(name), Iterable<NbtTag> {
    override val tagType = NbtTagType.COMPOUND
    override val hasValue = false
    override val codec = Codec

    private val tags: MutableMap<String, NbtTag> = mutableMapOf()

    init {
        tags.forEach {
            add(it)
        }
    }

    fun add(tag: NbtTag) {
        require(tag != this) { "Can't add compound tag to itself" }
        require(tag.parent == null) { "A tag may only be added to a single compound/list at a time" }
        val name = requireNotNull(tag.name) { "Can't add nameless tag to compound" }
        tags[name] = tag
        tag.parent = this
    }

    fun addRange(tags: Iterable<NbtTag>) {
        tags.forEach { add(it) }
    }

    fun remove(tag: NbtTag): Boolean {
        val removed = tags.remove(tag.name) ?: return false
        removed.parent = null
        return true
    }

    fun clear() {
        this.forEach { it.parent = null }
        tags.clear()
    }

    fun contains(tag: NbtTag): Boolean {
        return tags.containsValue(tag)
    }

    val size: Int
        get() = tags.size

    operator fun get(tagName: String): NbtTag? {
        return tags[tagName]
    }

    override fun deepClone(): NbtTag {
        val copy = NbtCompound(name)
        tags.values.forEach { copy.add(it.deepClone()) }
        return copy
    }

    override fun iterator(): Iterator<NbtTag> {
        return tags.values.iterator()
    }

    override fun prettyPrint(sb: StringBuilder, indentStr: String, indentLevel: Int) {
        for (i in 0 until indentLevel)
            sb.append(indentStr)
        sb.append(tagType.notchianName)
        sb.append("(\"$name\")")
        sb.append(": $size entries {")
        if (size > 0) {
            sb.appendln()
            this.forEach {
                it.prettyPrint(sb, indentStr, indentLevel + 1)
                sb.appendln()
            }
            for (i in 0 until indentLevel)
                sb.append(indentStr)
        }
        sb.append('}')
    }

    object Codec : NbtTagCodec() {
        override val id = 10
        fun serializeIncludingSelf(obj: NbtCompound, stream: DataOutput) {
            stream.writeByte(id)
            serialize(obj, stream)
        }
        override fun serialize(obj: Any, stream: DataOutput) {
            if (obj !is NbtCompound) throw IllegalArgumentException()
            obj.tags.values.forEach {
                stream.writeByte(it.codec.id)
                it.codec.serialize(it, stream)
            }
            stream.writeByte(NbtEnd.Codec.id)
        }
        override fun deserialize(name: String?, stream: NbtInputStream): NbtTag {
            val children = ArrayList<NbtTag>(5)
            while (true) {
                val child = stream.readTag() ?: throw IllegalStateException("Null tag in NbtCompound")
                if (child is NbtEnd)
                    break
                children.add(child)
            }
            return NbtCompound(name, children)
        }
    }

    fun toBytes(littleEndian: Boolean = false): ByteArray {
        val bs = ByteArrayOutputStream()
        val os: DataOutput = DataOutputStream(bs)
        Codec.serialize(this, os)
        return bs.toByteArray()
    }

    companion object {
        fun deserialize(stream: InputStream, littleEndian: Boolean = false): NbtCompound {
            val os: DataInput = DataInputStream(stream)
            return Codec.deserialize(NbtInputStream(os)) as NbtCompound
        }
    }
}
