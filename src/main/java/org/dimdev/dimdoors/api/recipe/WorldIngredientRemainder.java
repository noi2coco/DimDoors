package org.dimdev.dimdoors.api.recipe;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;

/**
 * @author CreepyCre
 */
public interface WorldIngredientRemainder<T> {
	Optional<T> placeOrReturn(World world, Vec3d pos);

	Optional<T> placeOrReturn(World world, BlockPos pos);
}
