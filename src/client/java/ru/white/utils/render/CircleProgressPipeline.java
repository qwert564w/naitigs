package ru.white.utils.render;

public class CircleProgressPipeline implements DrawBatcher.Batched {
    public static final CircleProgressPipeline INSTANCE = new CircleProgressPipeline();
    public void draw(float x, float y, float radius, float progress, int color) {}
    public void flush() {}
}
