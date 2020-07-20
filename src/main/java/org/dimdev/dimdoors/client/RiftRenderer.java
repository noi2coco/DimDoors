package org.dimdev.dimdoors.client;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.dimdev.dimdoors.entity.RiftEntity;

import java.util.ArrayList;

public class RiftRenderer extends EntityRenderer<RiftEntity> {
    public RiftRenderer(EntityRenderDispatcher dispatcher, EntityRendererRegistry.Context context) {
        super(dispatcher);
    }

    @Override
    public void render(RiftEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vcp, int i) {
        VertexConsumer vc = vcp.getBuffer(TexturedRenderLayers.getEntitySolid());
        ArrayList<RiftCurves.Point> points = RiftCurves.CURVES.get(0).points;

        for (RiftCurves.Point point : points) {
            vc.vertex(point.x, point.y, 0).color(0, 0, 0, 0).next();
        }
    }

    @Override
    public Identifier getTexture(RiftEntity entity) {
        return null;
    }
}
