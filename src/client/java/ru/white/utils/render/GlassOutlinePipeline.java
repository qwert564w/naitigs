package ru.white.utils.render;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import org.joml.Matrix4f;
public class GlassOutlinePipeline {
    private Framebuffer fb; private int w, h;
    public GlassOutlinePipeline(int w, int h) { this.w = w; this.h = h; this.fb = new SimpleFramebuffer(w, h, false, MinecraftClient.IS_SYSTEM_MAC); }
    public void render(Matrix4f matrix) {
        if (fb == null) return;
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.enableBlend(); RenderSystem.defaultBlendFunc();
        Tessellator t = Tessellator.getInstance(); BufferBuilder b = t.getBuffer();
        b.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        b.vertex(matrix, 0, 0, 0).color(1, 1, 1, 0.5f).next();
        b.vertex(matrix, w, 0, 0).color(1, 1, 1, 0.5f).next();
        b.vertex(matrix, w, h, 0).color(1, 1, 1, 0.5f).next();
        b.vertex(matrix, 0, h, 0).color(1, 1, 1, 0.5f).next();
        t.draw(); RenderSystem.disableBlend();
    }
    public void resize(int w, int h) { this.w = w; this.h = h; if (fb != null) fb.resize(w, h, MinecraftClient.IS_SYSTEM_MAC); }
    public void cleanup() { if (fb != null) fb.delete(); }
}