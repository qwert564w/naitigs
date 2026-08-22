package ru.white.utils.render;

public class ScreenBlur implements DrawBatcher.Batched {
    public static final ScreenBlur INSTANCE = new ScreenBlur();
    public void flush() {}
}
