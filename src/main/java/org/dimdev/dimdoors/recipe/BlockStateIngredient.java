/**
 * @author CreepyCre
 */
package org.dimdev.dimdoors.recipe;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.dimdev.dimdoors.api.util.function.TriFunction;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// TODO: Serialization
public class BlockStateIngredient<R> implements WorldIngredient<BlockState, R> {
	private IngredientPredicate<BlockState> testIngredient;
	private TriFunction<BlockState, World, BlockPos, Optional<R>> transformIngredient;

	public Optional<R> consume(BlockState state, World world, Vec3d pos) {
		return consume(state, world, new BlockPos(pos));
	}

	@Override
	public Optional<R> consume(BlockState state, World world, BlockPos pos) {
		return transformIngredient.apply(state, world, pos);
	}

	@Override
	public void cachePossibleTargetsBox(World world, Predicate<BlockState> filter, Collection<Box> boxes) {
		Set<BlockBox> blockBoxes = boxes.parallelStream().map(box -> {
			int minXBlockBox = (int) Math.floor(box.minX);
			int minYBlockBox = (int) Math.floor(box.minY);
			int minZBlockBox = (int) Math.floor(box.minZ);
			int maxXBlockBox = (int) Math.floor(box.maxX);
			int maxYBlockBox = (int) Math.floor(box.maxY);
			int maxZBlockBox = (int) Math.floor(box.maxZ);

			if (maxXBlockBox == box.maxX && maxXBlockBox > minXBlockBox) maxXBlockBox--;
			if (maxYBlockBox == box.maxY && maxYBlockBox > minYBlockBox) maxYBlockBox--;
			if (maxZBlockBox == box.maxZ && maxZBlockBox > minZBlockBox) maxZBlockBox--;

			return new BlockBox(minXBlockBox, minYBlockBox, minZBlockBox, maxXBlockBox, maxYBlockBox, maxZBlockBox);
		}).collect(Collectors.toSet());
		cachePossibleTargetsBlockBox(world, filter, blockBoxes);
	}

	@Override
	public void cachePossibleTargetsBlockBox(World world, Predicate<BlockState> filter, Collection<BlockBox> boxes) {
		// TODO
	}

	@Override
	public TargetedWorldIngredientInformation collectTargetedIngredientInformation(TargetedWorldIngredientInformation info) {
		return null; // TODO
	}

	@Override
	public boolean test(BlockState state) {
		return testIngredient.test(state); // TODO
	}
}
