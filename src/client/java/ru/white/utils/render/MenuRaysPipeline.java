package ru.white.utils.render;

public class MenuRaysPipeline implements DrawBatcher.Batched {
    public static final MenuRaysPipeline INSTANCE = new MenuRaysPipeline();
    public void draw(float x, float y, float w, float h, float progress) {}
    public void flush() {}
}
