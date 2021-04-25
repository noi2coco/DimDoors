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

public interface WorldIngredientType<T extends WorldIngredient<U, V>, U, V> {
	Registry<WorldIngredientType<? extends WorldIngredient<?, ?>, ?, ?>> REGISTRY = FabricRegistryBuilder.from(new DefaultedRegistry<WorldIngredientType<? extends WorldIngredient<?, ?>, ?, ?>>("dimdoors:none", RegistryKey.ofRegistry(new Identifier("dimdoors", "world_recipe_type")), Lifecycle.stable())).buildAndRegister();

	WorldIngredientType<WorldIngredient<Void, Void>, Void, Void> NONE = Registry.register(REGISTRY, IdentifierUtil.create("none"), new WorldIngredientType<WorldIngredient<Void, Void>, Void, Void>() {
		@Override
		public WorldIngredient<Void, Void> fromNbt(NbtCompound nbt) {
			throw new UnsupportedOperationException("Cannot deserialize NONE world ingredient type!");
		}

		@Override
		public NbtCompound toNbt(NbtCompound nbt) {
			throw new UnsupportedOperationException("Cannot serialize NONE world ingredient type!");
		}

		@Override
		public Identifier getId() {
			return IdentifierUtil.create("none");
		}
	});
	WorldIngredientType<BlockStateIngredient, BlockState, BlockState> BLOCK_STATE_INGREDIENT = register(IdentifierUtil.create("block_state_ingredient"), BlockStateIngredient::new);

	WorldIngredient<U, V> fromNbt(NbtCompound nbt);

	NbtCompound toNbt(NbtCompound nbt);

	Identifier getId();

	static <T extends WorldIngredient<U, V>, U, V> WorldIngredientType<T, U, V> register(Identifier id, Supplier<T> factory) {
		return Registry.register(REGISTRY, id, new WorldIngredientType<T, U, V>() {
			@Override
			public WorldIngredient<U, V> fromNbt(NbtCompound nbt) {
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
