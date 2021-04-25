package org.dimdev.dimdoors.api.recipe;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class WorldRecipeIngredientUsageTracker {
	private final Stack<Runnable> usageRemovalStack = new Stack<>();
	private final Map<BlockPos, WorldIngredient> allocatedBlocks = new HashMap<>();
	private final Map<UUID, WorldIngredient> allocatedEntities = new HashSet<>();

	public void pop() {
		usageRemovalStack.pop().run();
	}

	public void pushNone() {
		usageRemovalStack.push(() -> {});
	}

	public boolean allocateBlock(BlockPos pos, WorldIngredient ingredient) {
		if (allocatedBlocks.containsKey(pos)) return false;
		allocatedBlocks.put(pos, ingredient);
		usageRemovalStack.push(() -> allocatedBlocks.remove(pos));
		return true;
	}

	public boolean allocateEntity(Entity entity, WorldIngredient ingredient) {
		UUID uuid = entity.getUuid();
		if (allocatedEntities.containsKey(uuid)) return false;
		allocatedEntities.put(uuid, ingredient);
		usageRemovalStack.push(() -> allocatedEntities.remove(uuid));
		return true;
	}

	public TargetedWorldIngredientInformation buildTarget(World world) {
		new TargetedWorldIngredientInformation()
		return
	}
}
