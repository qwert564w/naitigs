package ru.white.manager.rotation;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import ru.white.manager.event_impl.EventMoveInput;
import ru.white.manager.event_impl.EventRender3D;
import ru.white.manager.event_impl.EventTick;
import ru.white.manager.event_impl.WorldLoadEvent;
import ru.white.manager.events.orbit.EventHandler;
import ru.white.manager.events.orbit.EventPriority;
import ru.white.module.impl.combat.AttackAura;
import ru.white.module.impl.combat.aura.rotation.FunTimeRotation;
import ru.white.module.impl.player.ClickHelper;
import ru.white.utils.animation.Animation;
import ru.white.utils.aura.GCDUtil;
import ru.white.utils.aura.RayTraceUtil;
import ru.white.utils.aura.UAttack;
import ru.white.utils.math.MathUtil;
import ru.white.utils.math.ServerUtil;
import ru.white.utils.player.MoveUtil;
import net.minecraft.util.math.MathHelper;

import java.util.concurrent.ThreadLocalRandom;

import static net.minecraft.util.math.MathHelper.wrapDegrees;

public class RotationProcess extends Component {

    public enum RotationTask {
        IDLE, COMBAT, MOVEMENT
    }
    
    public static RotationTask currentTask = RotationTask.IDLE;
    public static float currentYawSpeed;
    public static float currentPitchSpeed;
    
    public RotationProcess() {
        super("Rotation");
    }
    
    @EventHandler
    public void onTick(EventTick event) {}
    
    @EventHandler
    public void onRender3D(EventRender3D event) {}
    
    @EventHandler
    public void onMoveInput(EventMoveInput event) {}
    
    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {}
}
