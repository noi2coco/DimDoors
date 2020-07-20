package org.dimdev.dimdoors.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dimdev.dimdoors.ModConfig;
import org.dimdev.dimdoors.world.limbo.LimboDimension;

import java.util.Random;

public class UnravelledFabricBlock extends Block {
    public UnravelledFabricBlock(Settings settings) {
        super(settings);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.dimension instanceof LimboDimension) {
            if (Math.random() < ModConfig.LIMBO.decaySpreadChance) {
                decay(world, pos.up());
                decay(world, pos.down());
                decay(world, pos.north());
                decay(world, pos.south());
                decay(world, pos.west());
                decay(world, pos.east());
            }
        }
    }

    private void decay(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (state.isAir() || block == ModBlocks.UNRAVELLED_FABRIC || block == ModBlocks.ETERNAL_FLUID || block instanceof BlockEntityProvider) {
            return;
        }

        if (!state.isFullCube(world, pos)) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            return;
        }

        world.setBlockState(pos, getDecayTarget(block).getDefaultState());
    }

    private Block getDecayTarget(Block block) {
        return ModBlocks.UNRAVELLED_FABRIC; // TODO
    }
}
