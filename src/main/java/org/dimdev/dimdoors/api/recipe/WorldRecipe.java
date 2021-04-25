package org.dimdev.dimdoors.api.recipe;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;

/**
 * @author CreepyCre
 * Defines a recipe using in-world objects such as {@link net.minecraft.block.BlockState BlockStates}, {@link net.minecraft.block.entity.BlockEntity BlockEntities} or {@link net.minecraft.entity.Entity Entities}, that produces a {@link WorldRecipeResult WorldRecipeResult}.
 */
// TODO: Serialization
public interface WorldRecipe<T> {
	boolean matches(World world, Vec3d origin);

	boolean matches(World world, BlockPos origin);

	Optional<T> craft(World worldAccess, Vec3d origin);

	Optional<T> craft(World world, BlockPos origin);

	Identifier getId();

	WorldRecipeType<?, T> getType();

	WorldRecipe<T> fromNbt(NbtCompound nbt);

	NbtCompound toNbt(NbtCompound nbt);
}
