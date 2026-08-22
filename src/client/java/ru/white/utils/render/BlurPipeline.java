package ru.white.utils.render;

public class BlurPipeline implements DrawBatcher.Batched {
    public static final BlurPipeline INSTANCE = new BlurPipeline();
    public void drawBlur(float x, float y, float width, float height, float radius, float[] radii, int color) {}
    public void drawBlur(float x, float y, float width, float height, float radius, float[] radii, int color, float distortion, float waveSize, float edgeLight, float shine) {}
    public void drawGlassBlur(float x, float y, float width, float height, float alpha, float[] radii, int tintColor, float distortion, float waveSize, float edgeLight, float shine) {}
    public void flush() {}
}
