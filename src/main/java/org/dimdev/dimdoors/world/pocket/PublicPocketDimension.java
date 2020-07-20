package org.dimdev.dimdoors.world.pocket;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import org.dimdev.dimdoors.world.ModBiomes;
import org.dimdev.dimdoors.world.ModDimensions;

public class PublicPocketDimension extends DungeonPocketDimension {
    public PublicPocketDimension(World world, DimensionType dimensionType) {
        super(world, dimensionType);
    }

    @Override
    protected Biome getBiome() {
        return ModBiomes.BLACK_VOID;
    }

    @Override
    public DimensionType getType() {
        return ModDimensions.PUBLIC;
    }
}
