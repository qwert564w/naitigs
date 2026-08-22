package ru.white.utils.render;

public class TexturePipeline implements DrawBatcher.Batched {
    public static final TexturePipeline INSTANCE = new TexturePipeline();
    public void draw(float x, float y, float w, float h, Object texture, int color) {}
    public void flush() {}
}
