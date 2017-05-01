package com.zixiken.dimdoors.client.controlpanel;

import com.zixiken.dimdoors.DimDoors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static org.lwjgl.opengl.GL11.*;

public class ControlPanelRenderManager {

    private static final ControlPanelRenderManager INSTANCE = new ControlPanelRenderManager();
    private final Tessellator tessellator = Tessellator.getInstance();
    private final VertexBuffer vertexBuffer = this.tessellator.getBuffer();
    private final Minecraft minecraft = Minecraft.getMinecraft();
    private final ScaledResolution scaledResolution = new ScaledResolution(this.minecraft);

    private ControlPanelRenderManager() {
    }

    public static ControlPanelRenderManager getInstance() {
        return INSTANCE;
    }


    public void startGlScissor(int x, int y, int width, int height) {
        final double scaleW = (double) this.minecraft.displayWidth / this.scaledResolution.getScaledWidth_double();
        final double scaleH = (double) this.minecraft.displayHeight / this.scaledResolution.getScaledHeight_double();

        if (width <= 0 || height <= 0) return;
        if (x < 0) x = 0;
        if (y < 0) y = 0;

        glEnable(GL_SCISSOR_TEST);
        glScissor((int) Math.floor((double) x * scaleW), (int) Math.floor((double) this.minecraft.displayHeight - ((double) (y + height) * scaleH)), (int) Math.floor((double) (x + width) * scaleW) - (int) Math.floor((double) x * scaleW), (int) Math.floor((double) this.minecraft.displayHeight - ((double) y * scaleH)) - (int) Math.floor((double) this.minecraft.displayHeight - ((double) (y + height) * scaleH))); //starts from lower left corner (minecraft starts from upper left)
    }

    public void endGlScissor() {
        glDisable(GL_SCISSOR_TEST);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;
        glPushAttrib(GL_CURRENT_BIT | GL_ENABLE_BIT | GL_COLOR_BUFFER_BIT);
        glPushMatrix();

        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        if (DimDoors.instance.isControlPanelActive()) {
            glColor4f(0, 0, 0, .8f);

            this.vertexBuffer.begin(GL_QUADS, DefaultVertexFormats.POSITION);
            this.vertexBuffer.pos(0, 0, 0).endVertex();
            this.vertexBuffer.pos(0, this.minecraft.displayHeight, 0).endVertex();
            this.vertexBuffer.pos(this.minecraft.displayWidth, this.minecraft.displayHeight, 0).endVertex();
            this.vertexBuffer.pos(this.minecraft.displayWidth, 0, 0).endVertex();
            this.tessellator.draw();

            ComponentRegistry.getInstance().getComponentList().reverseStream().forEachOrdered(this::protectedRender);
        } else {
            ComponentRegistry.getInstance().getComponentList().reverseStream().filter(Component::isPinned).forEachOrdered(this::protectedRender);
        }

        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glPopMatrix();
        glPopAttrib();
    }

    private void protectedRender(@Nonnull final Component component) {
        glPushMatrix();
        glTranslated(component.getPositionX(), component.getPositionY(), 0);
//        startGlScissor(component.getPositionX(), component.getPositionY(), component.getWidth(), component.getHeight());
        component.render();
//        endGlScissor();
        glPopMatrix();
    }
}
