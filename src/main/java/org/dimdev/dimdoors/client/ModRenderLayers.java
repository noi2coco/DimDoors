package org.dimdev.dimdoors.client;

import com.google.common.collect.ImmutableMap;

import java.util.Random;

import com.flowpowered.math.vector.VectorNi;
import com.mojang.blaze3d.systems.RenderSystem;
import org.dimdev.dimdoors.mixin.client.accessor.RenderLayerAccessor;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormatElement;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.util.Identifier;

import static net.minecraft.client.render.VertexFormats.COLOR_ELEMENT;
import static net.minecraft.client.render.VertexFormats.POSITION_ELEMENT;
import static net.minecraft.client.render.VertexFormats.TEXTURE_ELEMENT;import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_DST_COLOR;
import static org.lwjgl.opengl.GL11.GL_ZERO;

public class ModRenderLayers extends RenderLayer {
	public static final Identifier WARP_PATH = new Identifier("dimdoors:textures/other/warp.png");
	public static final VectorNi COLORLESS = new VectorNi(255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255);
	private static final Identifier KEY_PATH = new Identifier("dimdoors:textures/other/keyhole.png");
	private static final Identifier KEYHOLE_LIGHT = new Identifier("dimdoors:textures/other/keyhole_light.png");
	private static final Random RANDOM = new Random(31100L);

	private static final VertexFormatElement POSITION_ELEMENT = new VertexFormatElement(0, VertexFormatElement.Format.FLOAT, VertexFormatElement.Type.POSITION, 4);
	public static VertexFormat POSITION_COLOR_TEXTURE = new VertexFormat(ImmutableMap.<String, VertexFormatElement>builder().put("Position", POSITION_ELEMENT).put("Color", COLOR_ELEMENT).put("UV0", TEXTURE_ELEMENT).build());

	public ModRenderLayers(String string, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int j, boolean bl, boolean bl2, Runnable runnable, Runnable runnable2) {
		super(string, vertexFormat, drawMode, j, bl, bl2, runnable, runnable2);
	}

	public static RenderLayer CRACK = RenderLayerAccessor.callOf("crack",
			VertexFormats.POSITION_COLOR,
			VertexFormat.DrawMode.QUADS,
			256,
			false,
			false,
			MultiPhaseParameters.builder()
					.cull(DISABLE_CULLING)
					.lightmap(RenderPhase.DISABLE_LIGHTMAP)
					.method_34577(NO_TEXTURE)
					.transparency(new Transparency("crack_transparency",
							() -> {
								RenderSystem.enableBlend();
								RenderSystem.blendFunc(GL_ONE_MINUS_DST_COLOR, GL_ZERO);
							},
							() -> {
								RenderSystem.disableBlend();
								RenderSystem.defaultBlendFunc();
							})
					)
					.build(false)
	);

	public static RenderLayer TESSERACT;
	public static RenderLayer DIMENSIONAL_PORTAL_LAYER;

	public static RenderLayer getMonolith(Identifier texture) {
		RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder().method_34577(new RenderPhase.Texture(texture, false, false)).transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY).cull(DISABLE_CULLING).lightmap(ENABLE_LIGHTMAP).depthTest(RenderPhase.ALWAYS_DEPTH_TEST).overlay(ENABLE_OVERLAY_COLOR).build(false);
		return RenderLayerAccessor.callOf("monolith", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, true, multiPhaseParameters);
	}

	public static void initShaderLayers() {
		TESSERACT = RenderLayerAccessor.callOf("tesseract",
				POSITION_COLOR_TEXTURE,
				VertexFormat.DrawMode.QUADS,
				256,
				false,
				false,
				MultiPhaseParameters.builder()
						.cull(DISABLE_CULLING)
						.lightmap(RenderPhase.DISABLE_LIGHTMAP)
						.method_34578(ModShaders.getTesseractShader())
						.method_34577(RenderPhase.class_5940.method_34560()
								.method_34563(DetachedRiftBlockEntityRenderer.TESSERACT_PATH, false, false)
								.method_34562()
						)
						.build(false)
		);

		DIMENSIONAL_PORTAL_LAYER = RenderLayerAccessor.callOf(
				"dimensional_portal",
				VertexFormats.POSITION,
				VertexFormat.DrawMode.QUADS,
				256,
				false,
				false,
				RenderLayer.MultiPhaseParameters.builder()
						.method_34578(ModShaders.getDimensionalPortalShader())
						.method_34577(
								RenderPhase.class_5940.method_34560()
										.method_34563(EndPortalBlockEntityRenderer.SKY_TEXTURE, false, false)
										.method_34563(WARP_PATH, false, false)
										.method_34562()
						)
						.build(false)
		);
	}
}
