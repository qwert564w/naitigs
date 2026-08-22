package ru.white.utils.render;

public class FogBlurPipeline implements DrawBatcher.Batched {
    public static final FogBlurPipeline INSTANCE = new FogBlurPipeline();
    public void draw(float x, float y, float w, float h, float radius) {}
    public void flush() {}
}
