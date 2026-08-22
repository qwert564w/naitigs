package ru.white.utils.render;

public class RectPipeline implements DrawBatcher.Batched {
    public static final RectPipeline INSTANCE = new RectPipeline();
    public void draw(float x, float y, float w, float h, int color) {}
    public void flush() {}
}
