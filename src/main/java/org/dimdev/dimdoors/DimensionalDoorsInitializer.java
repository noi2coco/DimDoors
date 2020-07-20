package org.dimdev.dimdoors;

import net.fabricmc.api.ModInitializer;
import org.dimdev.dimdoors.block.ModBlocks;
import org.dimdev.dimdoors.command.ModCommands;
import org.dimdev.dimdoors.entity.ModEntityTypes;
import org.dimdev.dimdoors.item.ModItems;
import org.dimdev.dimdoors.world.ModBiomes;
import org.dimdev.dimdoors.world.ModDimensions;

public class DimensionalDoorsInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        ModBlocks.init();
        ModItems.init();
        ModEntityTypes.init();
        ModDimensions.init();
        ModBiomes.init();
        ModCommands.init();
    }
}
