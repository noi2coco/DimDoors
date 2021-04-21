/**
 * @author CreepyCre
 */
package org.dimdev.dimdoors.recipe;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

// TODO: Serialization
public interface WorldIngredient<T, R> extends Predicate<T> {
	Optional<R> consume(T t, World world, Vec3d pos);

	Optional<R> consume(T t, World world, BlockPos pos);

	default void cachePossibleTargetsBox(World world, Predicate<T> filter, Box... boxes) {
		cachePossibleTargetsBox(world, filter, Arrays.asList(boxes));
	}

	void cachePossibleTargetsBox(World world, Predicate<T> filter, Collection<Box> boxes);

	default void cachePossibleTargetsBlockBox(World world, Predicate<T> filter, BlockBox... boxes) {
		cachePossibleTargetsBlockBox(world, filter, Arrays.asList(boxes));
	}

	void cachePossibleTargetsBlockBox(World world, Predicate<T> filter, Collection<BlockBox> boxes);

	TargetedWorldIngredientInformation collectTargetedIngredientInformation(TargetedWorldIngredientInformation info);
}
