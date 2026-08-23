package ru.white.module.impl.render;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import ru.white.manager.event_impl.EventPacket;
import ru.white.manager.events.orbit.EventHandler;
import ru.white.module.api.Category;
import ru.white.module.api.Module;
import ru.white.module.api.ModuleInfo;

@ModuleInfo(
        name = "Anti Darkness",
        desc = "Убирает эффект темноты от Вардена и визуальный огонь при горении",
        category = Category.RENDER
)
public class AntiDarkness extends Module {

    @EventHandler
    public void onPacket(EventPacket event) {
        if (!isEnabled() || event.getPacket() == null) return;

        if (event.getPacket() instanceof EntityStatusEffectS2CPacket effectPacket) {
            try {
                var effectType = effectPacket.getEffectType();
                // ПРАВИЛЬНО для 1.21.11: сравниваем RegistryEntry напрямую
                if (effectType != null && effectType == StatusEffects.DARKNESS) {
                    event.cancel();
                    return;
                }
            } catch (Exception e) {}
        }
    }

    @Override
    public void onEnable() {
        if (mc.player != null && mc.player.hasStatusEffect(StatusEffects.DARKNESS)) {
            mc.player.removeStatusEffect(StatusEffects.DARKNESS);
        }
        if (mc.player != null && mc.player.isOnFire()) {
            mc.player.setFireTicks(0);
        }
    }

    @Override
    public void onUpdate() {
        if (!isEnabled() || mc.player == null) return;
        if (mc.player.hasStatusEffect(StatusEffects.DARKNESS)) {
            mc.player.removeStatusEffect(StatusEffects.DARKNESS);
        }
        if (mc.player.isOnFire() && mc.player.getFireTicks() > 0) {
            mc.player.setFireTicks(0);
        }
    }
}