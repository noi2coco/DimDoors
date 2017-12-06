package com.zixiken.dimdoors.shared.items;

import java.util.List;

import com.zixiken.dimdoors.DimDoors;
import com.zixiken.dimdoors.shared.blocks.BlockDimDoorBase;
import com.zixiken.dimdoors.shared.blocks.BlockDimDoorUnstable;
import com.zixiken.dimdoors.shared.blocks.ModBlocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import static com.zixiken.dimdoors.DimDoors.translateAndAdd;

public class ItemDimDoorUnstable extends ItemDoorBase {

    public ItemDimDoorUnstable() {
        super(ModBlocks.UNSTABLE_DIMENSIONAL_DOOR, null);
        setUnlocalizedName(BlockDimDoorUnstable.ID);
        setRegistryName(new ResourceLocation(DimDoors.MODID, BlockDimDoorUnstable.ID));
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        translateAndAdd("info.unstable_dimensional_door", tooltip);
    }

    @Override
    protected BlockDimDoorBase getDoorBlock() {
        return ModBlocks.UNSTABLE_DIMENSIONAL_DOOR;
    }
}
