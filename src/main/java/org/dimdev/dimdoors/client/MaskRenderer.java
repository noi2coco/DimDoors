package org.dimdev.dimdoors.client;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.util.Identifier;

import org.dimdev.dimdoors.entity.MaskEntity;

public class MaskRenderer extends EntityRenderer<MaskEntity> {
	public MaskRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public Identifier getTexture(MaskEntity entity) {
        return new Identifier("dimdoors:mask");
    }
}
