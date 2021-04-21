package org.dimdev.dimdoors.recipe;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;

/**
 * @author CreepyCre
 * Produces the result of a {@link org.dimdev.dimdoors.recipe.WorldRecipe WorldRecipe} by either modifying the world or returning an {@link Optional Optional<T>}
 */
public interface WorldRecipeResult<T> {
	Optional<T> produceResult(World world, Vec3d pos,);
}
