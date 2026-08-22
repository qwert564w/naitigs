package ru.white.utils.render;

public class HalftoneDotsPipeline implements DrawBatcher.Batched {
    public static final HalftoneDotsPipeline INSTANCE = new HalftoneDotsPipeline();
    public void draw(float x, float y, float w, float h, float progress) {}
    public void flush() {}
}
