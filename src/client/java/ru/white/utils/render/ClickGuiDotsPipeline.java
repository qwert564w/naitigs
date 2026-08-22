package ru.white.utils.render;

public class ClickGuiDotsPipeline implements DrawBatcher.Batched {
    public static final ClickGuiDotsPipeline INSTANCE = new ClickGuiDotsPipeline();
    public void draw(float x, float y, float w, float h, float progress) {}
    public void flush() {}
}
