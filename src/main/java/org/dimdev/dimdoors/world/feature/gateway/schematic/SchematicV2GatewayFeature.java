package org.dimdev.dimdoors.world.feature.gateway.schematic;

import java.util.Random;

import net.minecraft.block.AirBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import com.mojang.serialization.Codec;

public class SchematicV2GatewayFeature extends Feature<SchematicV2GatewayFeatureConfig> {
    public SchematicV2GatewayFeature(Codec<SchematicV2GatewayFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, SchematicV2GatewayFeatureConfig config) {
        if (world.getBlockState(pos).getBlock() instanceof AirBlock && config.getGateway().test(world, pos)) {
			config.getGateway().generate(world, pos);
            return true;
        }
        return false;
    }
}
