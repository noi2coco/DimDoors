package org.dimdev.dimdoors.api.recipe;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author CreepyCre
 */
public class PlacingBlockStateRemainder implements WorldIngredientRemainder<Void> {
	private Supplier<BlockState> remainderSupplier;

	@Override
	public Optional<Void> placeOrReturn(World world, Vec3d pos) {
		return placeOrReturn(world, new BlockPos(pos));
	}

	@Override
	public Optional<Void> placeOrReturn(World world, BlockPos pos) {
		world.setBlockState(pos, remainderSupplier.get());
		return Optional.empty();
	}
}
