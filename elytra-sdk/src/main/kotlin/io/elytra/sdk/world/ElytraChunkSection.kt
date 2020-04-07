package io.elytra.sdk.world

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.api.io.NibbleArray
import io.elytra.api.world.ChunkSection
import io.netty.buffer.ByteBuf

class ElytraChunkSection(
    override val palette: List<Int> = listOf(0),
    override val data: ByteArray = ByteArray(4096),
    override val skyLight: NibbleArray = NibbleArray(1024),
    override val blockLight: NibbleArray = NibbleArray(1024),
    var nonAirBlocks: Int = 0
) : ChunkSection {

    fun write(buffer: ByteBuf) {
        buffer.writeShort(nonAirBlocks)
        buffer.writeByte(8)

        ByteBufUtils.writeVarInt(buffer, palette.size)

        for (blockId in palette) {
            ByteBufUtils.writeVarInt(buffer, blockId)
        }

        ByteBufUtils.writeVarInt(buffer, data.size / 8)

        for (i in 0 until data.size / 8) {
            for (j in 0 until 8) {
                buffer.writeByte(data[i * 8 + 7 - j].toInt())
            }
        }
    }
}
