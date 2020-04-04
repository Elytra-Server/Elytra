package io.elytra.sdk.world.strategy

import io.elytra.api.io.NibbleArray
import io.elytra.api.nbt.NbtInputStream
import io.elytra.api.nbt.tags.*
import io.elytra.api.world.*
import io.elytra.sdk.io.ElytraInputStream
import io.elytra.sdk.world.ElytraChunk
import io.elytra.sdk.world.ElytraChunkColumn
import io.elytra.sdk.world.ElytraWorld
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.util.stream.Collectors
import java.util.zip.GZIPInputStream
import java.util.zip.InflaterInputStream

private const val sectorSize = 4096

data class ChunkEntry(val offset: Int, val paddedSize: Int)

class AnvilWorldStrategy : WorldLoadStrategy {
    override fun load(path: String): World {
        val levelStream = NbtInputStream(GZIPInputStream(FileInputStream("$path/level.dat")))
        val levelCompound = (levelStream.readTag() as NbtCompound)["Data"] as NbtCompound
        val spawnPoint = Position(
            (levelCompound["SpawnX"] as NbtInt).value.toDouble(),
            (levelCompound["SpawnY"] as NbtInt).value.toDouble(),
            (levelCompound["SpawnZ"] as NbtInt).value.toDouble()
        )

        val regions = File("$path/region").listFiles()
            .filter { it.path.endsWith("mca") }
            .map(::readRegion)

        val chunks = regions.flatten()

        return ElytraWorld(spawnPoint, chunks)
    }

    private fun readRegion(file: File): List<ChunkColumn> {
        val regionBytes = file.readBytes()
        val regionStream = ElytraInputStream(regionBytes)
        val chunkEntries = (0 until 1024).map {
            val entry = regionStream.readInt()
            val chunkOffset = entry ushr 8
            val chunkSize = entry and 0xF

            ChunkEntry(chunkOffset * sectorSize, chunkSize * sectorSize)
        }.filterNot { it.offset == 0 && it.paddedSize == 0 }

        return chunkEntries.parallelStream().map {
            val headerStream = ElytraInputStream(regionBytes, it.offset, it.paddedSize)
            val chunkSize = headerStream.readInt() - 1
            val compressionScheme = headerStream.readByte().toInt()
            val chunkStream = ByteArrayInputStream(regionBytes, it.offset + 5, chunkSize)
            val compressionStream = if (compressionScheme == 1) GZIPInputStream(chunkStream) else InflaterInputStream(chunkStream)
            val chunkNbtStream = NbtInputStream(compressionStream)
            val chunkCompound = (chunkNbtStream.readTag() as NbtCompound)["Level"] as NbtCompound

            readChunk(chunkCompound)
        }.collect(Collectors.toList())
    }

    private fun readChunk(nbt: NbtCompound): ChunkColumn {
        val chunkX = (nbt["xPos"] as NbtInt).value
        val chunkZ = (nbt["zPos"] as NbtInt).value

        val biomes = (nbt["Biomes"] as NbtByteArray).value
        val chunks = arrayOfNulls<Chunk>(16)

        (nbt["Sections"] as NbtList).forEach {
            val sectionCompound = it as NbtCompound
            val sectionY = (sectionCompound["Y"] as NbtByte).value

            val blocksId = (sectionCompound["Blocks"] as NbtByteArray).value
            val blocksData = NibbleArray((sectionCompound["Data"] as NbtByteArray).value)

            val blockLight = NibbleArray((sectionCompound["BlockLight"] as NbtByteArray).value)
            val skyLight = NibbleArray((sectionCompound["SkyLight"] as NbtByteArray).value)

            val section = ElytraChunk(blocksId, blocksData, blockLight, skyLight)

            chunks[sectionY] = section
        }

        return ElytraChunkColumn(chunkX, chunkZ, chunks, biomes)
    }
}
