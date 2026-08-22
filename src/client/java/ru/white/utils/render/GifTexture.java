package ru.white.utils.render;

import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;

public class GifTexture {
    public static Identifier load(String url) {
        return Identifier.of("client", "textures/missing.png");
    }
    
    public static void unload(Identifier id) {}
}
