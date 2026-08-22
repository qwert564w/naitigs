package ru.white.utils.render;

public class GrayscalePipeline implements DrawBatcher.Batched {
    public static final GrayscalePipeline INSTANCE = new GrayscalePipeline();
    public void draw(float x, float y, float w, float h, float amount) {}
    public void apply(float amount) {}
    public void flush() {}
}
