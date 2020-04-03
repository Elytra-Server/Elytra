package io.elytra.api.nbt

abstract class NbtValueTag(name: String?) : NbtTag(name) {
    override val hasValue: Boolean
        get() = true

    final override fun prettyPrint(sb: StringBuilder, indentStr: String, indentLevel: Int) {
        for (i in 0 until indentLevel) {
            sb.append(indentStr)
        }
        sb.append(tagType.notchianName)
        if (! name.isNullOrEmpty()) {
            sb.append("(\"$name\")")
        }
        sb.append(": ${prettyValueStr()}")
    }

    abstract fun prettyValueStr(): String
}
