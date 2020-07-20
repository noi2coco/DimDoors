package org.dimdev.dimdoors.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.BlockView;

public final class RayTraceHelper {
    public static final int REACH_DISTANCE = 5;

    public static boolean hitsRift(HitResult hit, BlockView world) {
        return false;
    }

    public static boolean hitsLivingEntity(HitResult hit) {
        return hit != null && hit.getType() == HitResult.Type.ENTITY && ((EntityHitResult) hit).getEntity() instanceof LivingEntity;
    }
}
