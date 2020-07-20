package org.dimdev.dimdoors.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.dimdev.util.reference.BlockReference;

import java.util.List;

public class StabilizedRiftSignatureItem extends Item {
    public StabilizedRiftSignatureItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasEnchantmentGlint(ItemStack stack) {
        return stack.getTag() != null && stack.getTag().contains("destination");
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        return new TypedActionResult<>(ActionResult.FAIL, player.getStackInHand(hand));
    }

    private BlockReference getTarget(ItemStack stack) {
        return null;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> list, TooltipContext tooltipContext) {
        BlockReference target = getTarget(itemStack);

        if (target != null) {
            list.add(new TranslatableText(getTranslationKey() + ".bound.info", target.x, target.y, target.z, target.world.dimension.getType().toString()));
        } else {
            list.add(new TranslatableText(getTranslationKey() + ".unbound.info"));
        }
    }
}
