package org.dimdev.dimdoors.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.dimdev.dimdoors.DimDoors;
import org.dimdev.dimdoors.block.ModBlocks;
import org.dimdev.dimdoors.block.entity.DetachedRiftBlockEntity;
import org.dimdev.dimdoors.rift.targets.RiftReference;
import org.dimdev.dimdoors.sound.ModSoundEvents;
import org.dimdev.util.Location;
import org.dimdev.util.RotatedLocation;

import javax.annotation.Nullable;
import java.util.List;

public class RiftSignatureItem extends Item {
    public static final String ID = "rift_signature";

    public RiftSignatureItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasEnchantmentGlint(ItemStack stack) {
        return stack.getTag() != null && stack.getTag().contains("destination");
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext itemUsageContext) {
        PlayerEntity player = itemUsageContext.getPlayer();
        World world = itemUsageContext.getWorld();
        BlockPos pos = itemUsageContext.getBlockPos();
        Hand hand = itemUsageContext.getHand();
        Direction side = itemUsageContext.getSide();

        ItemPlacementContext placementContext = new ItemPlacementContext(itemUsageContext);

        ItemStack stack = player.getStackInHand(hand);
        pos = world.getBlockState(pos).getBlock().canReplace(world.getBlockState(pos), placementContext) ? pos : pos.offset(side);

        // Fail if the player can't place a block there
        if (!player.canPlaceOn(pos, side.getOpposite(), stack)) {
            return ActionResult.FAIL;
        }

        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        RotatedLocation target = getSource(stack);

        if (target == null) {
            // The link signature has not been used. Store its current target as the first location.
            setSource(stack, new RotatedLocation(world, pos, player.yaw, 0));
            player.addChatMessage(new TranslatableText(getTranslationKey() + ".stored"), true);
            world.playSound(null, player.getBlockPos(), ModSoundEvents.RIFT_START, SoundCategory.BLOCKS, 0.6f, 1);
        } else {
            // Place a rift at the saved point
            if (target.getBlockState().getBlock() != ModBlocks.DETACHED_RIFT) {
                if (!target.getBlockState().getBlock().canMobSpawnInside()) {
                    DimDoors.sendTranslatedMessage(player, "tools.target_became_block");
                    clearSource(stack); // TODO: But is this fair? It's a rather hidden way of unbinding your signature!
                    return ActionResult.FAIL;
                }
                World sourceWorld = ((Location) target).world;
                sourceWorld.setBlockState(((Location) target).getBlockPos(), ModBlocks.DETACHED_RIFT.getDefaultState());
                DetachedRiftBlockEntity rift1 = (DetachedRiftBlockEntity) target.getBlockEntity();
                rift1.setDestination(RiftReference.tryMakeRelative(target, new Location(world, pos)));
                rift1.setTeleportTargetRotation(target.yaw, 0); // setting pitch to 0 because player is always facing down to place rift
                rift1.register();
            }

            // Place a rift at the target point
            world.setBlockState(pos, ModBlocks.DETACHED_RIFT.getDefaultState());
            DetachedRiftBlockEntity rift2 = (DetachedRiftBlockEntity) world.getBlockEntity(pos);
            rift2.setDestination(RiftReference.tryMakeRelative(new Location(world, pos), target));
            rift2.setTeleportTargetRotation(player.yaw, 0);
            rift2.register();

            stack.damage(1, player, a -> {}); // TODO: calculate damage based on position?

            clearSource(stack);
            player.addChatMessage(new TranslatableText(getTranslationKey() + ".created"), true);
            // null = send sound to the player too, we have to do this because this code is not run client-side
            world.playSound(null, player.getBlockPos(), ModSoundEvents.RIFT_END, SoundCategory.BLOCKS, 0.6f, 1);
        }

        return ActionResult.SUCCESS;
    }

    public static void setSource(ItemStack itemStack, RotatedLocation destination) {
        if (!itemStack.hasTag()) itemStack.setTag(new CompoundTag());
        itemStack.getTag().put("destination", destination.serialize());
    }

    public static void clearSource(ItemStack itemStack) {
        if (itemStack.hasTag()) {
            itemStack.getTag().remove("destination");
        }
    }

    public static RotatedLocation getSource(ItemStack itemStack) {
        if (itemStack.hasTag() && itemStack.getTag().contains("destination")) {
            RotatedLocation transform = RotatedLocation.deserialize(itemStack.getTag().getCompound("destination"));
            return transform;
        } else {
            return null;
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack itemStack, @Nullable World world, List<Text> list, TooltipContext tooltipContext) {
        RotatedLocation transform = getSource(itemStack);
        if (transform != null) {
            list.add(new TranslatableText(getTranslationKey() + ".bound.info", transform.getX(), transform.getY(), transform.getZ(), transform.getWorldId()));
        } else {
            list.add(new TranslatableText(getTranslationKey() + ".unbound.info"));
        }
    }
}
