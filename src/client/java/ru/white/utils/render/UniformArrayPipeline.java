package ru.white.utils.render;

public class UniformArrayPipeline implements DrawBatcher.Batched {
    public static final UniformArrayPipeline INSTANCE = new UniformArrayPipeline();
    public void setUniform(String name, float[] values) {}
    public void flush() {}
}
