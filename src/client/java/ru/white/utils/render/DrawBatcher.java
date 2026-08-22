package ru.white.utils.render;

import java.util.ArrayList;
import java.util.List;

public class DrawBatcher {
    public interface Batched {}
    private static final List<Batched> pending = new ArrayList<>();
    private static boolean batching = false;
    public static void begin() { batching = true; pending.clear(); }
    public static void end() { batching = false; pending.clear(); }
    public static boolean isBatching() { return batching; }
    public static void submit(Batched b) { if (batching) pending.add(b); }
    public static void flush() { pending.clear(); }
}
