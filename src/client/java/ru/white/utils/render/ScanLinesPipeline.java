package ru.white.utils.render;

public class ScanLinesPipeline implements DrawBatcher.Batched {
    public static final ScanLinesPipeline INSTANCE = new ScanLinesPipeline();
    public void draw(float x, float y, float w, float h, float intensity) {}
    public void flush() {}
}
