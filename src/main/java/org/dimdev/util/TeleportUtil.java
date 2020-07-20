package org.dimdev.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class TeleportUtil {
    public static void teleport(Entity entity, ServerWorld world, BlockPos pos, int yawOffset) {
        teleport(entity, world, Vec3d.method_24955(pos), yawOffset);
    }

    public static void teleport(Entity entity, ServerWorld world, Vec3d pos, float yawOffset) {
        teleport(entity, world, pos.x, pos.y, pos.z, entity.yaw + yawOffset, entity.pitch);
    }

    public static void teleport(Entity entity, ServerWorld world, double x, double y, double z, float yaw, float pitch) {
        yaw = MathHelper.wrapDegrees(yaw);
        pitch = MathHelper.wrapDegrees(pitch);

        if (entity instanceof ServerPlayerEntity) {
            ChunkPos chunkPos = new ChunkPos(new BlockPos(x, y, z));
            world.getChunkManager().addTicket(ChunkTicketType.POST_TELEPORT, chunkPos, 1, entity.getEntityId());
            entity.stopRiding();

            if (((ServerPlayerEntity) entity).isSleeping()) {
                ((ServerPlayerEntity) entity).wakeUp(true, true);
            }

            if (world == entity.world) {
                ((ServerPlayerEntity) entity).networkHandler.teleportRequest(x, y, z, yaw, pitch, PlayerPositionLookS2CPacket.Flag.getFlags(0b11111));
            } else {
                ((ServerPlayerEntity) entity).teleport(world, x, y, z, yaw, pitch);
            }

            entity.setHeadYaw(yaw);
        } else {
            if (world == entity.world) {
                entity.refreshPositionAndAngles(x, y, z, yaw, pitch);
                entity.setHeadYaw(yaw);
            } else {
                entity.detach();
                entity.dimension = world.dimension.getType();
                Entity oldEntity = entity;
                entity = entity.getType().create(world);

                if (entity == null) {
                    return;
                }

                entity.copyFrom(oldEntity);
                entity.refreshPositionAndAngles(x, y, z, yaw, pitch);
                entity.setHeadYaw(yaw);
                world.onDimensionChanged(entity);
                oldEntity.removed = true;
            }
        }

        if (!(entity instanceof LivingEntity) || !((LivingEntity) entity).isFallFlying()) {
            entity.setVelocity(entity.getVelocity().multiply(1, 0, 1));
            entity.method_24830(true);
        }
    }
}
