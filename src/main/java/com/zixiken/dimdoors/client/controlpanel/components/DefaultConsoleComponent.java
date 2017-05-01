package com.zixiken.dimdoors.client.controlpanel.components;

import com.flowpowered.math.vector.Vector4f;
import com.zixiken.dimdoors.client.controlpanel.Component;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glColor4f;

public class DefaultConsoleComponent extends Component {

    private final Vector4f frameColor = new Vector4f(198.00f / 255.00f, 198.00f / 255.00f, 198.00f / 255.00f, 1);
    private final Vector4f backgroundColor = new Vector4f(.25f, .25f, .25f, 1);
    private final Vector4f foregroundColor = new Vector4f(.25f, .25f, .25f, 1);

    private int lastClickLocationX;
    private int lastClickLocationY;

    public DefaultConsoleComponent() {
        this.setPositionX(10);
        this.setPositionY(10);
        this.setHeight(100);
        this.setWidth(300);
        this.pinned = true;
    }

    @Override
    public void render() {
        renderFrame();

    }

    private void renderFrame() {
        glColor4f(frameColor.getX(), frameColor.getY(), frameColor.getZ(), frameColor.getW());
        vertexBuffer.begin(GL_QUADS, DefaultVertexFormats.POSITION);
        vertexBuffer.pos(0, 0, 0.0D).endVertex();
        vertexBuffer.pos(0, getHeight(), 0.0D).endVertex();
        vertexBuffer.pos(getWidth(), getHeight(), 0.0D).endVertex();
        vertexBuffer.pos(getWidth(), 0, 0.0D).endVertex();
        tessellator.draw();

        final double insetDistance = 5;
//        glColor4f(backgroundColor.getX(), backgroundColor.getY(), backgroundColor.getZ(), backgroundColor.getW());
        glColor4f(0, 0, 0, 1);
        vertexBuffer.begin(GL_QUADS, DefaultVertexFormats.POSITION);
        vertexBuffer.pos(insetDistance, insetDistance, 0.0D).endVertex();
        vertexBuffer.pos(insetDistance, getHeight() - insetDistance, 0.0D).endVertex();
        vertexBuffer.pos(getWidth() - insetDistance, getHeight() - insetDistance, 0.0D).endVertex();
        vertexBuffer.pos(getWidth() - insetDistance, insetDistance, 0.0D).endVertex();
        tessellator.draw();

        renderText("This is an example!", ((float) (insetDistance + 1)), ((float) (insetDistance + 1)), new Vector4f(1, 1, 1, 1));
    }

    @Override
    public void mouseClicked(int x, int y, int buttonCode) {

    }

    @Override
    public void mouseReleased(int x, int y, int buttonCode) {

    }

    @Override
    public void mouseDrag(int x, int y, int buttonCode) {

    }

    @Override
    public Component revalidate() {
        return null;
    }

    @Override
    public void mouseEntered(int x, int y) {

    }

    @Override
    public void mouseExited(int x, int y) {

    }

    @Override
    public void mouseMoved(int x, int y) {

    }

    @Override
    public boolean tryClick(int x, int y, int buttonCode) {
        return false;
    }

    @Override
    public String toString() {
        return "DefaultConsoleComponent{" +
                "frameColor=" + frameColor +
                ", backgroundColor=" + backgroundColor +
                ", foregroundColor=" + foregroundColor +
                ", lastClickLocationX=" + lastClickLocationX +
                ", lastClickLocationY=" + lastClickLocationY +
                '}';
    }
}
