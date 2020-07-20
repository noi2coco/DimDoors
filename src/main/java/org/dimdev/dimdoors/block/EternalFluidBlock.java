package org.dimdev.dimdoors.block;

import net.minecraft.block.Block;
import net.minecraft.block.FluidBlock;
import org.dimdev.dimdoors.fluid.ModFluids;

public class EternalFluidBlock extends FluidBlock {
    public EternalFluidBlock(Block.Settings settings) {
        super(ModFluids.ETERNAL_FLUID, settings);
    }
}
