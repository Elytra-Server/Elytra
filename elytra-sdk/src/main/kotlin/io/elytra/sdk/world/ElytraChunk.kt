package io.elytra.sdk.world

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.api.io.NibbleArray
import io.elytra.api.nbt.tags.NbtCompound
import io.elytra.api.nbt.tags.NbtLongArray
import io.elytra.api.world.Chunk
import io.elytra.api.world.ChunkSection
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

data class ElytraChunk(
    override val skyLight: NibbleArray = NibbleArray(1024),
    override val blockLight: NibbleArray = NibbleArray(1024),
    val sections: List<ElytraChunkSection?> = List(16) { ElytraChunkSection() },
    val biomes: IntArray = IntArray(1024)
) : Chunk {

    override fun getBlockAt(x: Int, y: Int, z: Int) {
        TODO("Not yet implemented")
    }

    override fun getSkyLight(x: Int, y: Int, z: Int) {
        TODO("Not yet implemented")
    }

    override fun getBlockLight(x: Int, y: Int, z: Int) {
        TODO("Not yet implemented")
    }

    override fun setSkyLight(x: Int, y: Int, z: Int, skyLight: Int) {
        TODO("Not yet implemented")
    }

    override fun setBlockLight(x: Int, y: Int, z: Int, blockLight: Int) {
        TODO("Not yet implemented")
    }

    override fun getSectionAt(y: Int): ChunkSection {
        TODO("Not yet implemented")
    }

    override fun getBiome(x: Int, y: Int, z: Int) {
        TODO("Not yet implemented")
    }

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

        // No block entities for now
        ByteBufUtils.writeVarInt(buffer, 0)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ElytraChunk

        if (skyLight != other.skyLight) return false
        if (blockLight != other.blockLight) return false

        return true
    }

    override fun hashCode(): Int {
        var result = skyLight.hashCode()
        result = 31 * result + blockLight.hashCode()
        return result
    }
}
