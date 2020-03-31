package io.elytra.api.nbt.tags

import io.elytra.api.nbt.NbtInputStream
import java.io.DataOutput
import java.io.DataOutputStream
import java.util.*

class NbtList(name: String?, tags: Iterable<NbtTag> = emptyList()) : NbtTag(name), Iterable<NbtTag> {
    override val tagType = NbtTagType.LIST
    override val hasValue = false
    override val codec = Codec

    val tags : MutableList<NbtTag> = mutableListOf()
    var listType : NbtTagType = NbtTagType.UNKNOWN
        set(value) {
            if(value == NbtTagType.END)
                throw IllegalArgumentException("Only empty list tags may have TagType of End")
            else if(value == NbtTagType.UNKNOWN)
                throw IllegalArgumentException("Not accepting an unknown type list beyond creation")
            if(tags.isNotEmpty()) {
                if(tags.any { it.tagType != value })
                    throw IllegalArgumentException("Assigned tag type $value doesn't match all elements in list")
            }
            field = value
        }

    init {
        tags.forEach { add(it) }
    }

    fun add(tag: NbtTag) {
        require(tag != this && tag != parent) { "Can't add list tag to itself or its child" }
        require(tag.parent == null) { "A tag may only be added to a single compount/list at a time" }
        require(tag.name == null) { "List tag strictly requires nameless childs" }
        if(listType == NbtTagType.UNKNOWN)
            listType = tag.tagType
        else
            require(tag.tagType == listType) { "Incompatible type added to list, accepts: $listType, given: ${tag.tagType}" }
        tags.add(tag)
        tag.parent = this
    }

    fun addRange(tags: Iterable<NbtTag>) {
        tags.forEach { add(it) }
    }

    fun remove(tag: NbtTag) : Boolean {
        if(! tags.remove(tag))
            return false
        tag.parent = null
        return true
    }

    fun removeAt(index: Int) {
        val tag = tags.removeAt(index)
        tag.parent = null
    }

    fun clear() {
        tags.forEach { it.parent = null }
        tags.clear()
    }

    fun contains(tag: NbtTag) : Boolean {
        return tags.contains(tag)
    }

    val size: Int
        get() = tags.size

    fun get(index: Int) : NbtTag {
        return tags[index]
    }

    override fun deepClone(): NbtTag {
        val list = NbtList(name)
        tags.forEach { list.add(it.deepClone()) }
        return list
    }

    override fun iterator(): Iterator<NbtTag> {
        return tags.iterator()
    }

    override fun prettyPrint(sb: StringBuilder, indentStr: String, indentLevel: Int) {
        for(i in 0 until indentLevel) {
            sb.append(indentStr)
        }
        sb.append(tagType.notchianName)
        if(! name.isNullOrEmpty()) {
            sb.append("(\"$name\")")
        }
        sb.append(": ${tags.size} entries {")

        if(size > 0) {
            sb.appendln()
            tags.forEach {
                it.prettyPrint(sb, indentStr, indentLevel + 1)
                sb.appendln()
            }
            for(i in 0 until indentLevel)
                sb.append(indentStr)
        }
        sb.append("}")
    }

    object Codec : NbtTagCodec() {
        override val id = 9

        override fun serialize(obj: Any, stream: DataOutput) {
            if(obj !is NbtList) throw IllegalArgumentException()
            stream.writeByte(obj.listType.codec.id)
            stream.writeInt(obj.tags.size)
            obj.tags.forEach { it.codec.serialize(it, stream) }
        }
        override fun deserialize(name: String?, stream: NbtInputStream): NbtTag {
            val tagTypeId = stream.readByte().toInt()
            val codec = NbtTagType.idToCodec[tagTypeId] ?: throw IllegalStateException("Unknown id $tagTypeId while reading NbtList")
            val ln = stream.readInt()
            val tags = ArrayList<NbtTag>(ln)
            for(i in 0 until ln) {
                val tag = codec.deserialize(null, stream)
                tags.add(tag)
            }
            return NbtList(name, tags)
        }
    }
}
