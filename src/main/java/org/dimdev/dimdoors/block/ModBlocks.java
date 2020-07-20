package org.dimdev.dimdoors.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.util.DyeColor;
import net.minecraft.util.registry.Registry;

public final class ModBlocks {
    public static final Block GOLD_DOOR = register("dimdoors:gold_door", new DoorBlock(FabricBlockSettings.of(Material.METAL, MaterialColor.GOLD).nonOpaque().build()));
    public static final Block QUARTZ_DOOR = register("dimdoors:quartz_door", new DoorBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.QUARTZ).nonOpaque().build()));

    public static final Block WHITE_FABRIC = register("dimdoors:white_fabric", new FabricBlock(FabricBlockSettings.of(Material.STONE, DyeColor.WHITE).lightLevel(15).build()));
    public static final Block ORANGE_FABRIC = register("dimdoors:orange_fabric", new FabricBlock(FabricBlockSettings.of(Material.STONE, DyeColor.ORANGE).lightLevel(15).build()));
    public static final Block MAGENTA_FABRIC = register("dimdoors:magenta_fabric", new FabricBlock(FabricBlockSettings.of(Material.STONE, DyeColor.MAGENTA).lightLevel(15).build()));
    public static final Block LIGHT_BLUE_FABRIC = register("dimdoors:light_blue_fabric", new FabricBlock(FabricBlockSettings.of(Material.STONE, DyeColor.LIGHT_BLUE).lightLevel(15).build()));
    public static final Block YELLOW_FABRIC = register("dimdoors:yellow_fabric", new FabricBlock(FabricBlockSettings.of(Material.STONE, DyeColor.YELLOW).lightLevel(15).build()));
    public static final Block LIME_FABRIC = register("dimdoors:lime_fabric", new FabricBlock(FabricBlockSettings.of(Material.STONE, DyeColor.LIME).lightLevel(15).build()));
    public static final Block PINK_FABRIC = register("dimdoors:pink_fabric", new FabricBlock(FabricBlockSettings.of(Material.STONE, DyeColor.PINK).lightLevel(15).build()));
    public static final Block GRAY_FABRIC = register("dimdoors:gray_fabric", new FabricBlock(FabricBlockSettings.of(Material.STONE, DyeColor.GRAY).lightLevel(15).build()));
    public static final Block LIGHT_GRAY_FABRIC = register("dimdoors:light_gray_fabric", new FabricBlock(FabricBlockSettings.of(Material.STONE, DyeColor.LIGHT_GRAY).lightLevel(15).build()));
    public static final Block CYAN_FABRIC = register("dimdoors:cyan_fabric", new FabricBlock(FabricBlockSettings.of(Material.STONE, DyeColor.CYAN).lightLevel(15).build()));
    public static final Block PURPLE_FABRIC = register("dimdoors:purple_fabric", new FabricBlock(FabricBlockSettings.of(Material.STONE, DyeColor.PURPLE).lightLevel(15).build()));
    public static final Block BLUE_FABRIC = register("dimdoors:blue_fabric", new FabricBlock(FabricBlockSettings.of(Material.STONE, DyeColor.BLUE).lightLevel(15).build()));
    public static final Block BROWN_FABRIC = register("dimdoors:brown_fabric", new FabricBlock(FabricBlockSettings.of(Material.STONE, DyeColor.BROWN).lightLevel(15).build()));
    public static final Block GREEN_FABRIC = register("dimdoors:green_fabric", new FabricBlock(FabricBlockSettings.of(Material.STONE, DyeColor.GREEN).lightLevel(15).build()));
    public static final Block RED_FABRIC = register("dimdoors:red_fabric", new FabricBlock(FabricBlockSettings.of(Material.STONE, DyeColor.RED).lightLevel(15).build()));
    public static final Block BLACK_FABRIC = register("dimdoors:black_fabric", new FabricBlock(FabricBlockSettings.of(Material.STONE, DyeColor.BLACK).lightLevel(15).build()));

    public static final Block WHITE_ANCIENT_FABRIC = register("dimdoors:white_ancient_fabric", new Block(FabricBlockSettings.of(Material.STONE, DyeColor.WHITE).strength(-1, 3600000).dropsNothing().build()));
    public static final Block ORANGE_ANCIENT_FABRIC = register("dimdoors:orange_ancient_fabric", new Block(FabricBlockSettings.of(Material.STONE, DyeColor.ORANGE).strength(-1, 3600000).dropsNothing().build()));
    public static final Block MAGENTA_ANCIENT_FABRIC = register("dimdoors:magenta_ancient_fabric", new Block(FabricBlockSettings.of(Material.STONE, DyeColor.MAGENTA).strength(-1, 3600000).dropsNothing().build()));
    public static final Block LIGHT_BLUE_ANCIENT_FABRIC = register("dimdoors:light_blue_ancient_fabric", new Block(FabricBlockSettings.of(Material.STONE, DyeColor.LIGHT_BLUE).strength(-1, 3600000).dropsNothing().build()));
    public static final Block YELLOW_ANCIENT_FABRIC = register("dimdoors:yellow_ancient_fabric", new Block(FabricBlockSettings.of(Material.STONE, DyeColor.YELLOW).strength(-1, 3600000).dropsNothing().build()));
    public static final Block LIME_ANCIENT_FABRIC = register("dimdoors:lime_ancient_fabric", new Block(FabricBlockSettings.of(Material.STONE, DyeColor.LIME).strength(-1, 3600000).dropsNothing().build()));
    public static final Block PINK_ANCIENT_FABRIC = register("dimdoors:pink_ancient_fabric", new Block(FabricBlockSettings.of(Material.STONE, DyeColor.PINK).strength(-1, 3600000).dropsNothing().build()));
    public static final Block GRAY_ANCIENT_FABRIC = register("dimdoors:gray_ancient_fabric", new Block(FabricBlockSettings.of(Material.STONE, DyeColor.GRAY).strength(-1, 3600000).dropsNothing().build()));
    public static final Block LIGHT_GRAY_ANCIENT_FABRIC = register("dimdoors:light_gray_ancient_fabric", new Block(FabricBlockSettings.of(Material.STONE, DyeColor.LIGHT_GRAY).strength(-1, 3600000).dropsNothing().build()));
    public static final Block CYAN_ANCIENT_FABRIC = register("dimdoors:cyan_ancient_fabric", new Block(FabricBlockSettings.of(Material.STONE, DyeColor.CYAN).strength(-1, 3600000).dropsNothing().build()));
    public static final Block PURPLE_ANCIENT_FABRIC = register("dimdoors:purple_ancient_fabric", new Block(FabricBlockSettings.of(Material.STONE, DyeColor.PURPLE).strength(-1, 3600000).dropsNothing().build()));
    public static final Block BLUE_ANCIENT_FABRIC = register("dimdoors:blue_ancient_fabric", new Block(FabricBlockSettings.of(Material.STONE, DyeColor.BLUE).strength(-1, 3600000).dropsNothing().build()));
    public static final Block BROWN_ANCIENT_FABRIC = register("dimdoors:brown_ancient_fabric", new Block(FabricBlockSettings.of(Material.STONE, DyeColor.BROWN).strength(-1, 3600000).dropsNothing().build()));
    public static final Block GREEN_ANCIENT_FABRIC = register("dimdoors:green_ancient_fabric", new Block(FabricBlockSettings.of(Material.STONE, DyeColor.GREEN).strength(-1, 3600000).dropsNothing().build()));
    public static final Block RED_ANCIENT_FABRIC = register("dimdoors:red_ancient_fabric", new Block(FabricBlockSettings.of(Material.STONE, DyeColor.RED).strength(-1, 3600000).dropsNothing().build()));
    public static final Block BLACK_ANCIENT_FABRIC = register("dimdoors:black_ancient_fabric", new Block(FabricBlockSettings.of(Material.STONE, DyeColor.BLACK).strength(-1, 3600000).dropsNothing().build()));

    public static final Block UNRAVELLED_FABRIC = register("dimdoors:unravelled_fabric", new UnravelledFabricBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.BLACK).ticksRandomly().build()));

    public static final Block MARKING_PLATE = register("dimdoors:marking_plate", new MarkingPlateBlock(FabricBlockSettings.of(Material.METAL, DyeColor.BLACK).build()));

    public static final Block ETERNAL_FLUID = register("dimdoors:eternal_fluid", new EternalFluidBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.RED).build()));

    private static Block register(String string, Block block) {
        return Registry.register(Registry.BLOCK, string, block);
    }

    public static void init() {
        // just loads the class
    }

    private static class DoorBlock extends net.minecraft.block.DoorBlock {
        protected DoorBlock(Settings settings) {
            super(settings);
        }
    }
}
