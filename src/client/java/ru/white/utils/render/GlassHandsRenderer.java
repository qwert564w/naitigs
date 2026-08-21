package ru.white.utils.render;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import org.joml.Matrix4f;
public class GlassHandsRenderer {
    public static void render(Matrix4f matrix, float width, float height) {
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.enableBlend(); RenderSystem.defaultBlendFunc();
        Tessellator t = Tessellator.getInstance(); BufferBuilder b = t.getBuffer();
        b.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        b.vertex(matrix, 0, 0, 0).color(0.5f, 0.7f, 1.0f, 0.3f).next();
        b.vertex(matrix, width, 0, 0).color(0.5f, 0.7f, 1.0f, 0.3f).next();
        b.vertex(matrix, width, height, 0).color(0.5f, 0.7f, 1.0f, 0.3f).next();
        b.vertex(matrix, 0, height, 0).color(0.5f, 0.7f, 1.0f, 0.3f).next();
        t.draw(); RenderSystem.disableBlend();
    }
    public static void cleanup() {}
}