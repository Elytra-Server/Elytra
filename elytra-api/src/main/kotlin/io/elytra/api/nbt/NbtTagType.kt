package io.elytra.api.nbt

import io.elytra.api.nbt.tags.*
import io.elytra.api.nbt.tags.NbtTag

enum class NbtTagType(val codec: NbtTag.NbtTagCodec, val notchianName: String) {
    UNKNOWN(NbtEnd.Codec, "TAG_Unknown"),
    END(NbtEnd.Codec, "TAG_End"),
    BYTE(NbtByte.Codec, "TAG_Byte"),
    SHORT(NbtShort.Codec, "TAG_Short"),
    INT(NbtInt.Codec, "TAG_Int"),
    LONG(NbtLong.Codec, "TAG_Long"),
    FLOAT(NbtFloat.Codec, "TAG_Float"),
    DOUBLE(NbtDouble.Codec, "TAG_Double"),
    BYTE_ARRAY(NbtByteArray.Codec, "TAG_Byte_Array"),
    STRING(NbtString.Codec, "TAG_String"),
    LIST(NbtList.Codec, "TAG_List"),
    COMPOUND(NbtCompound.Codec, "TAG_Compound"),
    INT_ARRAY(NbtIntArray.Codec, "TAG_Int_Array"),
    LONG_ARRAY(NbtLongArray.Codec, "TAG_Long_Array");

    companion object {
        val idToCodec = values().associate { type -> Pair(type.codec.id, type.codec) }
        val idToType = values().associateBy { type -> type.codec.id }
    }
}
