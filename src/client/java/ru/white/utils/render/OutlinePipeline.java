package ru.white.utils.render;

public class OutlinePipeline implements DrawBatcher.Batched {
    public static final OutlinePipeline INSTANCE = new OutlinePipeline();
    public void draw(float x, float y, float w, float h, float lineWidth, int color) {}
    public void flush() {}
}
