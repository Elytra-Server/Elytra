package io.elytra.sdk.world.strategy

import io.elytra.api.io.NibbleArray
import io.elytra.api.nbt.NbtInputStream
import io.elytra.api.nbt.tags.NbtByteArray
import io.elytra.api.nbt.tags.NbtCompound
import io.elytra.api.nbt.tags.NbtShort
import io.elytra.api.world.Position
import io.elytra.api.world.World
import io.elytra.api.world.WorldLoadStrategy
import io.elytra.sdk.world.ElytraChunk
import io.elytra.sdk.world.ElytraChunkSection
import io.elytra.sdk.world.ElytraWorld
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.util.zip.GZIPInputStream

@Deprecated("For testing only")
class ClassicWorldStrategy : WorldLoadStrategy {
    /**
     * A mapping of the classic block ids to the global palette block state ids
     */
    private val classicPalette = listOf(0, 1, 9, 10, 14, 15, 21, 33, 34, 34, 50, 50, 66, 68, 69, 70, 71, 73, 157,
        228, 230, 1383, 1384, 1385, 1386, 1387, 1388, 1389, 1390, 1391, 1392, 1393, 1394, 1395, 1396, 1397,
        1398, 1411, 1412, 1424, 1425, 1426, 1427, 7811, 7809, 1428, 1430, 1431, 1432, 1433)

    override fun load(path: String): World {
        val file = File(path)
        val inputStream = GZIPInputStream(FileInputStream(file))

        val mapNbt = NbtInputStream(inputStream).readTag()
        require(mapNbt is NbtCompound) { "NBTCompound required" }

        // TODO Validate
        val sizeX = (mapNbt["X"] as NbtShort).value
        val sizeY = (mapNbt["Y"] as NbtShort).value
        val sizeZ = (mapNbt["Z"] as NbtShort).value
        val spawn = (mapNbt["Spawn"] as NbtCompound)
        val spawnX = (spawn["X"] as NbtShort).value
        val spawnY = (spawn["Y"] as NbtShort).value
        val spawnZ = (spawn["Z"] as NbtShort).value
        val blockData = (mapNbt["BlockArray"] as NbtByteArray).value

        require(sizeX % 16 == 0 && sizeY % 16 == 0 && sizeZ % 16 == 0) { "Sizes must be divisible by 16" }
        require(sizeY <= 256) { "Height cannot be greater than 256" }

        val world = ElytraWorld(Position(spawnX.toDouble(), spawnY.toDouble(), spawnZ.toDouble()))
        val chunksX = sizeX / 16
        val chunksZ = sizeZ / 16
        val chunkSections = sizeY / 16

        for (chunkX in 0 until chunksX) {
            for (chunkZ in 0 until chunksZ) {
                world.setChunk(chunkX, chunkZ, convertToChunk(blockData, sizeX, sizeY, sizeZ, chunkX, chunkZ, chunkSections))
            }
        }

        return world
    }

    private fun convertToChunk(blocks: ByteArray, sizeX: Int, sizeY: Int, sizeZ: Int, chunkX: Int, chunkZ: Int, chunkSections: Int): ElytraChunk {
        val xyzToIndex = { x: Int, y: Int, z: Int -> y * sizeX * sizeZ + z * sizeX + x }
        val sections = mutableListOf<ElytraChunkSection>()
        for (section in 0 until chunkSections) {
            sections.add(convertToChunkSection(blocks, chunkX, section, chunkZ, xyzToIndex))
        }
        return ElytraChunk(NibbleArray(2048), NibbleArray(2048), sections, IntArray(1024) { 73 })
    }

    private fun convertToChunkSection(blocks: ByteArray, chunkX: Int, chunkY: Int, chunkZ: Int, f: (Int, Int, Int) -> Int): ElytraChunkSection {
        val data = ByteArray(4096)
        var i = 0
        var nonAirBlocks = 0

        val range = 0 until 16
        for (y in range) {
            for (z in range) {
                for (x in range) {
                    val mapX = x + chunkX * 16
                    val mapY = y + chunkY * 16
                    val mapZ = z + chunkZ * 16

                    val blockId = blocks[f(mapX, mapY, mapZ)].toInt()
                    if (blockId != 0) {
                        nonAirBlocks++
                    }

                    data[i] = blockId.toByte()
                    i++
                }
            }
        }

        return ElytraChunkSection(classicPalette, data, nonAirBlocks)
    }

    private fun decompressStream(compressed: ByteArray): ByteArray {
        val decompressed = ByteArrayOutputStream()
        val gzip = GZIPInputStream(ByteArrayInputStream(compressed))
        val buffer = ByteArray(1024)

        var len = 0
        do {
            len = gzip.read(buffer)
            if (len != -1) {
                decompressed.write(buffer, 0, len)
            }
        } while (len != -1)

        return decompressed.toByteArray()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            ClassicWorldStrategy().load("E:\\Users\\WinX64\\Desktop\\Lucas\\Games\\ClassiCube\\maps\\Flat.cw")
        }
    }
}
