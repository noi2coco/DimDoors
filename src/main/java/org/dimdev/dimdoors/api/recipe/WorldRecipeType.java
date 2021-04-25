package org.dimdev.dimdoors.api.recipe;

import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.dimdev.dimdoors.util.IdentifierUtil;

import java.util.function.Supplier;

public interface WorldRecipeType<T extends WorldRecipe<R>, R> {
	Registry<WorldRecipeType<? extends WorldRecipe<?>, ?>> REGISTRY = FabricRegistryBuilder.from(new DefaultedRegistry<WorldRecipeType<? extends WorldRecipe<?>, ?>>("dimdoors:none", RegistryKey.ofRegistry(IdentifierUtil.create("world_recipe_type")), Lifecycle.stable())).buildAndRegister();

	WorldRecipeType<WorldRecipe<Void>, Void> NONE = Registry.register(REGISTRY, IdentifierUtil.create("none"), new WorldRecipeType<WorldRecipe<Void>, Void>() {
		@Override
		public WorldRecipe<Void> fromNbt(NbtCompound nbt) {
			throw new UnsupportedOperationException("Cannot deserialize NONE world recipe type!");
		}

		@Override
		public NbtCompound toNbt(NbtCompound nbt) {
			throw new UnsupportedOperationException("Cannot serialize NONE world recipe type!");
		}

		@Override
		public Identifier getId() {
			return IdentifierUtil.create("none");
		}
	});
	WorldRecipeType<BlockStateRecipe, BlockState> BLOCK_STATE_RECIPE = register(IdentifierUtil.create("block_state_recipe"), BlockStateRecipe::new);

	WorldRecipe<R> fromNbt(NbtCompound nbt);

	NbtCompound toNbt(NbtCompound nbt);

	Identifier getId();

	static <T extends WorldRecipe<R>, R> WorldRecipeType<T, R> register(Identifier id, Supplier<T> factory) {
		return Registry.register(REGISTRY, id, new WorldRecipeType<T, R>() {
			@Override
			public WorldRecipe<R> fromNbt(NbtCompound nbt) {
				return factory.get().fromNbt(nbt);
			}

			@Override
			public NbtCompound toNbt(NbtCompound nbt) {
				nbt.putString("type", id.toString());
				return nbt;
			}

			@Override
			public Identifier getId() {
				return id;
			}
		});
	}
}
