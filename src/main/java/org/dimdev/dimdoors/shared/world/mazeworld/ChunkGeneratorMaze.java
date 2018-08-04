package org.dimdev.dimdoors.shared.world.mazeworld;

import net.minecraft.block.BlockStairs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import org.dimdev.ddutils.math.MathUtils;
import org.dimdev.dimdoors.shared.world.ModBiomes;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChunkGeneratorMaze implements IChunkGenerator {

    private Random rand;

    // Noise generators
    private NoiseGeneratorOctaves minLimitPerlinNoise;
    private NoiseGeneratorOctaves maxLimitPerlinNoise;
    private NoiseGeneratorOctaves mainPerlinNoise;
    public NoiseGeneratorOctaves depthNoise;
    private static final int MAZE_CELL_HEIGHT = 12;
    private static final int MAZE_GEN_BASE_SEED = 5113; //684th prime number

    double[] mainNoiseRegion;
    double[] minLimitRegion;
    double[] maxLimitRegion;

    private World world;

    private Biome[] biomesForGeneration = {ModBiomes.LIMBO};

    double[] depthRegion;

    public ChunkGeneratorMaze(World world) {
        this.world = world;
        rand = new Random(world.getSeed());
        minLimitPerlinNoise = new NoiseGeneratorOctaves(rand, 16); //base terrain
        maxLimitPerlinNoise = new NoiseGeneratorOctaves(rand, 16); //hillyness
        mainPerlinNoise = new NoiseGeneratorOctaves(rand, 80);  //seems to adjust the size of features, how stretched things are -default 8
        depthNoise = new NoiseGeneratorOctaves(rand, 16);
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        rand.setSeed(MathUtils.mergeSeeds((int) world.getSeed(), x, z, MAZE_GEN_BASE_SEED));
        ChunkPrimer primer = new ChunkPrimer();
        List<MazeCell> mazeCells = determineVerticalMazeCells(x, z);
        setBlocksInChunk(x, z, primer, mazeCells);
        Chunk chunk = new Chunk(world, primer, x, z);
        chunk.generateSkylightMap();

        if (!chunk.isTerrainPopulated()) {
            chunk.setTerrainPopulated(true);
        }

        return chunk;
    }

    private List<MazeCell> determineVerticalMazeCells(int x, int z) {
        int yMax = world.getHeight();
        List<MazeCell> mazeCells = new ArrayList();
        for (int level = 0; (level + 1) * MAZE_CELL_HEIGHT < yMax; level++) {
            mazeCells.add(getMazeCellFromRandom(level, rand.nextDouble()));
        }

        for (int level = mazeCells.size() - 1; level >= 0; level--) {
            MazeCell cell = mazeCells.get(level);
            if (cell.cellType == MazeCell.EnumCellType.STAIRS) {
                mazeCells.get(level - 1).cellType = MazeCell.EnumCellType.STAIRS_UPPER;
            }
            cell.setConnections(world.getSeed(), x, z, level);
        }
        return mazeCells;
    }

    private MazeCell getMazeCellFromRandom(int level, double determinator) {
        if (level == 0) {
            if (determinator < 0.4) {
                return new MazeCell(MazeCell.EnumCellType.ROOM);
            }
            return new MazeCell(MazeCell.EnumCellType.HALL);
        }

        if (determinator < 0.05) {
            return new MazeCell(MazeCell.EnumCellType.STAIRS);
        } else if (determinator < 0.43) {
            return new MazeCell(MazeCell.EnumCellType.ROOM);
        }
        return new MazeCell(MazeCell.EnumCellType.HALL);
    }

    @Override
    public void populate(int x, int z) {
        Biome biome = world.getBiome(new BlockPos(x * 16 + 16, 0, z * 16 + 16));
        WorldEntitySpawner.performWorldGenSpawning(world, biome, x * 16 + 8, z * 16 + 8, 16, 16, rand);
    }

    @Override
    public boolean generateStructures(Chunk chunk, int x, int z) {
        return false;
    }

    @SuppressWarnings("LocalVariableNamingConvention")
    public void setBlocksInChunk(int x, int z, ChunkPrimer primer, List<MazeCell> mazeCells) {
        biomesForGeneration = world.getBiomeProvider().getBiomesForGeneration(biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);

        int baseY = 256;
        for (MazeCell cell : mazeCells) {
            if (cell.cellType == null) {
                break;
            }
            switch (cell.cellType) {
                case STAIRS_UPPER:
                    for (int i = 0; i < 4; i++) {
                        if (cell.connections[i]) {
                            switch (i) {
                                case 0:
                                    for (int j = 0; j < 6; j++) {
                                        primer.setBlockState(0 + j, baseY - 12 - j, 6, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST));
                                        primer.setBlockState(0 + j, baseY - 12 - j, 7, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST));
                                    }
                                    primer.setBlockState(0, baseY - 12, 8, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH));
                                    break;
                                case 1:
                                    for (int j = 0; j < 6; j++) {
                                        primer.setBlockState(8, baseY - 12 - j, 0 + j, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
                                        primer.setBlockState(9, baseY - 12 - j, 0 + j, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
                                    }
                                    primer.setBlockState(7, baseY - 12, 0, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST));
                                    break;
                                case 2:
                                    for (int j = 0; j < 6; j++) {
                                        primer.setBlockState(15 - j, baseY - 12 - j, 8, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
                                        primer.setBlockState(15 - j, baseY - 12 - j, 9, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
                                    }
                                    primer.setBlockState(15, baseY - 12, 7, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
                                    break;
                                case 3:
                                    for (int j = 0; j < 6; j++) {
                                        primer.setBlockState(6, baseY - 12 - j, 15 - j, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH));
                                        primer.setBlockState(7, baseY - 12 - j, 15 - j, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH));
                                    }
                                    primer.setBlockState(8, baseY - 12, 15, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    break;
                case STAIRS:
                    for (int tempX = 6; tempX < 10; tempX++) {
                        for (int tempZ = 6; tempZ < 10; tempZ++) {
                            primer.setBlockState(tempX, baseY - 6, tempZ, Blocks.STONEBRICK.getDefaultState());
                        }
                    }
                    for (int i = 0; i < 4; i++) {
                        if (cell.connections[i]) {
                            switch (i) {
                                case 0:
                                    for (int j = 0; j < 6; j++) {
                                        primer.setBlockState(0 + j, baseY - 11 + j, 8, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
                                        primer.setBlockState(0 + j, baseY - 11 + j, 9, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
                                    }
                                    primer.setBlockState(0, baseY - 11, 7, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH));
                                    break;
                                case 1:
                                    for (int j = 0; j < 6; j++) {
                                        primer.setBlockState(6, baseY - 11 + j, 0 + j, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH));
                                        primer.setBlockState(7, baseY - 11 + j, 0 + j, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH));
                                    }
                                    primer.setBlockState(8, baseY - 11, 0, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST));
                                    break;
                                case 2:
                                    for (int j = 0; j < 6; j++) {
                                        primer.setBlockState(15 - j, baseY - 11 + j, 6, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST));
                                        primer.setBlockState(15 - j, baseY - 11 + j, 7, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST));
                                    }
                                    primer.setBlockState(15, baseY - 11, 8, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
                                    break;
                                case 3:
                                    for (int j = 0; j < 6; j++) {
                                        primer.setBlockState(8, baseY - 11 + j, 15 - j, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
                                        primer.setBlockState(9, baseY - 11 + j, 15 - j, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
                                    }
                                    primer.setBlockState(7, baseY - 11, 15, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    break;
                case HALL:
                    for (int tempX = 6; tempX < 10; tempX++) {
                        for (int tempZ = 6; tempZ < 10; tempZ++) {
                            primer.setBlockState(tempX, baseY - 12, tempZ, Blocks.STONEBRICK.getDefaultState());
                        }
                    }
                    for (int i = 0; i < 4; i++) {
                        if (cell.connections[i]) {
                            switch (i) {
                                case 0:
                                    for (int tempX = 0; tempX < 6; tempX++) {
                                        for (int tempZ = 6; tempZ < 10; tempZ++) {
                                            primer.setBlockState(tempX, baseY - 12, tempZ, Blocks.STONEBRICK.getDefaultState());
                                        }
                                    }
                                    break;
                                case 1:
                                    for (int tempX = 6; tempX < 10; tempX++) {
                                        for (int tempZ = 0; tempZ < 6; tempZ++) {
                                            primer.setBlockState(tempX, baseY - 12, tempZ, Blocks.STONEBRICK.getDefaultState());
                                        }
                                    }
                                    break;
                                case 2:
                                    for (int tempX = 10; tempX < 16; tempX++) {
                                        for (int tempZ = 6; tempZ < 10; tempZ++) {
                                            primer.setBlockState(tempX, baseY - 12, tempZ, Blocks.STONEBRICK.getDefaultState());
                                        }
                                    }
                                    break;
                                case 3:
                                    for (int tempX = 6; tempX < 10; tempX++) {
                                        for (int tempZ = 10; tempZ < 16; tempZ++) {
                                            primer.setBlockState(tempX, baseY - 12, tempZ, Blocks.STONEBRICK.getDefaultState());
                                        }
                                    }
                                    break;
                            }
                        }
                    }
                    break;
                case ROOM:
                    for (int tempX = 1; tempX < 15; tempX++) {
                        for (int tempZ = 1; tempZ < 15; tempZ++) {
                            primer.setBlockState(tempX, baseY - 12, tempZ, Blocks.STONEBRICK.getDefaultState());
                        }
                    }
                    for (int i = 0; i < 4; i++) {
                        if (cell.connections[i]) {
                            switch (i) {
                                case 0:
                                    for (int tempZ = 6; tempZ < 10; tempZ++) {
                                        primer.setBlockState(0, baseY - 12, tempZ, Blocks.STONEBRICK.getDefaultState());
                                    }
                                    break;
                                case 1:
                                    for (int tempX = 6; tempX < 10; tempX++) {
                                        primer.setBlockState(tempX, baseY - 12, 0, Blocks.STONEBRICK.getDefaultState());
                                    }
                                    break;
                                case 2:
                                    for (int tempZ = 6; tempZ < 10; tempZ++) {
                                        primer.setBlockState(15, baseY - 12, tempZ, Blocks.STONEBRICK.getDefaultState());
                                    }
                                    break;
                                case 3:
                                    for (int tempX = 6; tempX < 10; tempX++) {
                                        primer.setBlockState(tempX, baseY - 12, 15, Blocks.STONEBRICK.getDefaultState());
                                    }
                                    break;
                            }
                        }
                    }
                    break;
                default:
                    break;
            }

            baseY -= MAZE_CELL_HEIGHT;
        }
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        Biome biome = world.getBiome(pos);
        return biome.getSpawnableList(creatureType);
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World world, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunk, int x, int z) {

    }

    @Override
    public boolean isInsideStructure(World world, String structureName, BlockPos pos) {
        return false;
    }
}
