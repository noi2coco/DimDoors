package org.dimdev.dimdoors.api.recipe;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BlockStateRecipe implements WorldRecipe<BlockState> {
	private final Map<String, WorldIngredient<?, ?>> ingredientKeys = new HashMap<>();

	@Override
	public boolean matches(World world, Vec3d origin) {
		return matches(world, new BlockPos(origin));
	}

	@Override
	public boolean matches(World world, BlockPos origin) {
		return false;
	}

	@Override
	public Optional<BlockState> craft(World worldAccess, Vec3d origin) {
		return Optional.empty();
	}

	@Override
	public Optional<BlockState> craft(World world, BlockPos origin) {
		return Optional.empty();
	}

	@Override
	public Identifier getId() {
		return null;
	}

	@Override
	public WorldRecipeType<?, BlockState> getType() {
		return null;
	}

	@Override
	public WorldRecipe<BlockState> fromNbt(NbtCompound nbt) {

		NbtCompound ingredientKeysCompound = nbt.getCompound("key");
		for (String key : ingredientKeysCompound.getKeys()) {
			NbtCompound keyCompound = nbt.getCompound(key);
			Identifier typeId = Identifier.tryParse(keyCompound.getString("type"));
			WorldIngredientType<?, ?, ?> type = WorldIngredientType.REGISTRY.get(typeId);
			if (type == WorldIngredientType.NONE) throw new RuntimeException(); // TODO: implement Exception
			assert type != null;
			ingredientKeys.put(key, type.fromNbt(keyCompound));
		}



		return this;
	}

	@Override
	public NbtCompound toNbt(NbtCompound nbt) {
		return null;
	}
}
