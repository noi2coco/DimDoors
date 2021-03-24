package org.dimdev.dimdoors.client;

import java.util.function.BiFunction;

import net.minecraft.class_5944;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;

public class ModShaders {
	private static class_5944 DIMENSIONAL_PORTAL_PROGRAM = null;
	private static class_5944 TESSERACT_PROGRAM = null;

	private static RenderPhase.class_5942 DIMENSIONAL_PORTAL_SHADER = null;
	private static RenderPhase.class_5942 TESSERACT_SHADER = null;

	private static GlUniform TESSERACT_UNIFORM = null;

	public static void init(BiFunction<String, VertexFormat, class_5944> shaderFunction) {
		DIMENSIONAL_PORTAL_PROGRAM = shaderFunction.apply("dimensional_portal", VertexFormats.POSITION);
		TESSERACT_PROGRAM = shaderFunction.apply("tesseract", ModRenderLayers.POSITION_COLOR_TEXTURE);

		DIMENSIONAL_PORTAL_SHADER = new RenderPhase.class_5942(() -> DIMENSIONAL_PORTAL_PROGRAM);
		TESSERACT_SHADER = new RenderPhase.class_5942(() -> TESSERACT_PROGRAM);

		TESSERACT_UNIFORM = TESSERACT_PROGRAM.method_34582("Transform4d");

		ModRenderLayers.initShaderLayers();
	}

	public static RenderPhase.class_5942 getDimensionalPortalShader() {
		return DIMENSIONAL_PORTAL_SHADER;
	}

	public static RenderPhase.class_5942 getTesseractShader() {
		return TESSERACT_SHADER;
	}

	public static GlUniform getTesseractUniform() {
		return TESSERACT_UNIFORM;
	}
}
