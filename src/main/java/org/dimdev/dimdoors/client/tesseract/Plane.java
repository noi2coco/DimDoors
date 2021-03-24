package org.dimdev.dimdoors.client.tesseract;

import com.flowpowered.math.matrix.Matrix4f;
import com.flowpowered.math.vector.Vector3f;
import com.flowpowered.math.vector.Vector4f;
import org.dimdev.dimdoors.util.RGBA;

import net.minecraft.client.render.BufferVertexConsumer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormatElement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import static com.flowpowered.math.TrigMath.cos;
import static com.flowpowered.math.TrigMath.sin;

@Environment(EnvType.CLIENT)
public class Plane {
    Vector4f[] vectors;

    public Plane(Vector4f vec1, Vector4f vec2, Vector4f vec3, Vector4f vec4) {
        this.vectors = new Vector4f[]{vec1, vec2, vec3, vec4};
    }

    public void draw(BufferVertexConsumer vc, RGBA color, double radian) {
        drawVertex(vc, this.vectors[0], 0, 0, color);
        drawVertex(vc, this.vectors[1], 0, 1, color);
        drawVertex(vc, this.vectors[2], 1, 1, color);
        drawVertex(vc, this.vectors[3], 1, 0, color);
    }

    private static void drawVertex(BufferVertexConsumer vc, Vector4f vector, int u, int v, RGBA color) {
        double scalar = 1d / (vector.getW() + 1);
        Vector3f scaled = vector.toVector3().mul(scalar);
        vertex(vc, vector.getX(), vector.getY(), vector.getZ(), vector.getW())
                .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                .texture(u, v)
                .next();
    }

    public static BufferVertexConsumer vertex(BufferVertexConsumer vc, float x, float y, float z, float w) {
        if (vc.getCurrentElement().getType() != VertexFormatElement.Type.POSITION) {
            return vc;
        } else if (vc.getCurrentElement().getFormat() == VertexFormatElement.Format.FLOAT && vc.getCurrentElement().method_34451() == 4) {
            vc.putFloat(0, x);
            vc.putFloat(4, y);
            vc.putFloat(8, z);
            vc.putFloat(12, w);
            vc.nextElement();
            return vc;
        } else {
            throw new IllegalStateException();
        }
    }


    private static float[] rotXW(Vector4f v, double angle) {
        return new float[] {
                cos(angle), 0, 0, sin(angle),
                0, 1, 0, 0,
                0, 0, 1, 0,
                -sin(angle), 0, 0, cos(angle)
        };
    }

    private static float[] rotZW(Vector4f v, double angle) {
        return new float[] {
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, cos(angle), -sin(angle),
                0, 0, sin(angle), cos(angle)
        };
    }

    private static float[] rotYW(Vector4f v, double angle) {
            return new float[] {
                1, 0, 0, 0,
                0, cos(angle), 0, sin(angle),
                0, 0, 1, 0,
                0, -sin(angle), 0, cos(angle)
            };
    }

    private static float[] rotXY(Vector4f v, double angle) {
        return new float[] {
                cos(angle), -sin(angle), 0, 0,
                sin(angle), cos(angle), 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        };
    }
}
