package ru.white.utils.render;

public class GlassCompositePipeline implements DrawBatcher.Batched {
    public static final GlassCompositePipeline INSTANCE = new GlassCompositePipeline();
    public void flush() {}
}
