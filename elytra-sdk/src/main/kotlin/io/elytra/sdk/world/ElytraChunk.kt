package io.elytra.sdk.world

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.api.nbt.tags.NbtCompound
import io.elytra.api.nbt.tags.NbtLongArray
import io.elytra.api.world.Chunk
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

data class ElytraChunk(
    override val x: Int = 0,
    override val z: Int = 0,
    val sections: List<ElytraChunkSection?> = List(16) { ElytraChunkSection() },
    val biomes: IntArray = IntArray(1024)
) : Chunk {

    fun write(buffer: ByteBuf) {
        // TODO Calculate if it's a full chunk and which sections must be sent
        val fullChunk = true

        buffer.writeBoolean(fullChunk)

        var bitMask = 0

        for (i in 0 until 16) {
            if (sections.size > i && sections[i] != null) {
                bitMask = bitMask or (1 shl i)
            }
        }

        ByteBufUtils.writeVarInt(buffer, bitMask)

        // TODO Calculate the highest block for each (X, Z) in the chunk
        // Maybe cache it in a NbtCompound already?
        val heightmap = NbtCompound("")

        heightmap.add(NbtLongArray("MOTION_BLOCKING", LongArray(36)))
        buffer.writeBytes(heightmap.toBytes())

        if (fullChunk) {
            for (i in biomes) {
                buffer.writeInt(i)
            }
        }

        val chunkSectionData = Unpooled.buffer()

        for (section in sections) {
            section?.write(chunkSectionData)
        }

        ByteBufUtils.writeVarInt(buffer, chunkSectionData.writerIndex())
        buffer.writeBytes(chunkSectionData)
        chunkSectionData.release()

        // TODO: Add block entities

        ByteBufUtils.writeVarInt(buffer, 0)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ElytraChunk

        if (sections != other.sections) return false
        if (!biomes.contentEquals(other.biomes)) return false
        if (x != other.x) return false
        if (z != other.z) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sections.hashCode()
        result = 31 * result + biomes.contentHashCode()
        result = 31 * result + x
        result = 31 * result + z
        return result
    }

    companion object {

        /**
         * The width of a chunk (x axis).
         */
        const val WIDTH = 16

        /**
         * The height of a chunk (z axis).
         */
        const val HEIGHT = 16

        /**
         * The Y depth of a single chunk section.
         */
        const val DEPTH = 16
    }
}
