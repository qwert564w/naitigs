package ru.white.module.impl.combat.aura.rotation;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import ru.white.manager.rotation.Rotation;
import ru.white.manager.rotation.RotationProcess;
import ru.white.module.impl.combat.AttackAura;
import ru.white.module.impl.combat.aura.RotationAura;
import ru.white.utils.math.MathUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public class IntpolRotation implements RotationAura {
    
    private static final Map<String, Function<Double, Double>> EASINGS = new LinkedHashMap<>();
    
    static {
        EASINGS.put("easeInSine", t -> 1 - Math.cos((t * Math.PI) / 2));
        EASINGS.put("easeOutSine", t -> Math.sin((t * Math.PI) / 2));
        EASINGS.put("easeInOutSine", t -> -(Math.cos(Math.PI * t) - 1) / 2);
        
        EASINGS.put("easeInQuad", t -> t * t);
        EASINGS.put("easeOutQuad", t -> 1 - (1 - t) * (1 - t));
        EASINGS.put("easeInOutQuad", t -> t < 0.5 ? 2 * t * t : 1 - Math.pow(-2 * t + 2, 2) / 2);
        
        EASINGS.put("easeInCubic", t -> t * t * t);
        EASINGS.put("easeOutCubic", t -> 1 - Math.pow(1 - t, 3));
        EASINGS.put("easeInOutCubic", t -> t < 0.5 ? 4 * t * t * t : 1 - Math.pow(-2 * t + 2, 3) / 2);
        
        EASINGS.put("easeInQuart", t -> t * t * t * t);
        EASINGS.put("easeOutQuart", t -> 1 - Math.pow(1 - t, 4));
        EASINGS.put("easeInOutQuart", t -> t < 0.5 ? 8 * t * t * t * t : 1 - Math.pow(-2 * t + 2, 4) / 2);
        
        EASINGS.put("easeInQuint", t -> t * t * t * t * t);
        EASINGS.put("easeOutQuint", t -> 1 - Math.pow(1 - t, 5));
        EASINGS.put("easeInOutQuint", t -> t < 0.5 ? 16 * t * t * t * t * t : 1 - Math.pow(-2 * t + 2, 5) / 2);
        
        EASINGS.put("easeInExpo", t -> t == 0 ? 0 : Math.pow(2, 10 * t - 10));
        EASINGS.put("easeOutExpo", t -> t == 1 ? 1 : 1 - Math.pow(2, -10 * t));
        EASINGS.put("easeInOutExpo", t -> t == 0 ? 0 : t == 1 ? 1 : t < 0.5 
            ? Math.pow(2, 20 * t - 10) / 2 : (2 - Math.pow(2, -20 * t + 10)) / 2);
        
        EASINGS.put("easeInCirc", t -> 1 - Math.sqrt(1 - Math.pow(t, 2)));
        EASINGS.put("easeOutCirc", t -> Math.sqrt(1 - Math.pow(t - 1, 2)));
        EASINGS.put("easeInOutCirc", t -> t < 0.5 
            ? (1 - Math.sqrt(1 - Math.pow(2 * t, 2))) / 2 
            : (Math.sqrt(1 - Math.pow(-2 * t + 2, 2)) + 1) / 2);
        
        double c1 = 1.70158; double c2 = c1 * 1.525; double c3 = c1 + 1;
        EASINGS.put("easeInBack", t -> c3 * t * t * t - c1 * t * t);
        EASINGS.put("easeOutBack", t -> 1 + c3 * Math.pow(t - 1, 3) + c1 * Math.pow(t - 1, 2));
        EASINGS.put("easeInOutBack", t -> t < 0.5 
            ? (Math.pow(2 * t, 2) * ((c2 + 1) * 2 * t - c2)) / 2 
            : (Math.pow(2 * t - 2, 2) * ((c2 + 1) * (t * 2 - 2) + c2) + 2) / 2);
        
        double c4 = (2 * Math.PI) / 3; double c5 = (2 * Math.PI) / 4.5;
        EASINGS.put("easeInElastic", t -> t == 0 ? 0 : t == 1 ? 1 
            : -Math.pow(2, 10 * t - 10) * Math.sin((t * 10 - 10.75) * c4));
        EASINGS.put("easeOutElastic", t -> t == 0 ? 0 : t == 1 ? 1 
            : Math.pow(2, -10 * t) * Math.sin((t * 10 - 0.75) * c4) + 1);
        EASINGS.put("easeInOutElastic", t -> t == 0 ? 0 : t == 1 ? 1 : t < 0.5 
            ? -(Math.pow(2, 20 * t - 10) * Math.sin((20 * t - 11.125) * c5)) / 2 
            : (Math.pow(2, -20 * t + 10) * Math.sin((20 * t - 11.125) * c5)) / 2 + 1);
        
        EASINGS.put("easeOutBounce", t -> {
            double n1 = 7.5625, d1 = 2.75;
            if (t < 1 / d1) return n1 * t * t;
            else if (t < 2 / d1) return n1 * (t -= 1.5 / d1) * t + 0.75;
            else if (t < 2.5 / d1) return n1 * (t -= 2.25 / d1) * t + 0.9375;
            else return n1 * (t -= 2.625 / d1) * t + 0.984375;
        });
        EASINGS.put("easeInBounce", t -> 1 - EASINGS.get("easeOutBounce").apply(1 - t));
        EASINGS.put("easeInOutBounce", t -> t < 0.5 
            ? (1 - EASINGS.get("easeOutBounce").apply(1 - 2 * t)) / 2 
            : (1 + EASINGS.get("easeOutBounce").apply(2 * t - 1)) / 2);
    }
    
    private String currentEasing;
    private long easingStartTime;
    private long easingDuration;
    private float startYaw, startPitch;
    private float targetYaw, targetPitch;
    private float lastAppliedYaw, lastAppliedPitch;
    private final Random random = new Random();
    
    private float baseHorizontalSpeed = 12.0f;
    private float baseVerticalSpeed = 6.0f;
    private float jitterAmplitude = 0.08f;
    private float microTremorFreq = 17.3f;
    
    @Override
    public void onRotation(AttackAura aura, LivingEntity target, float[] ranges, boolean canAttack) {
        if (mc.player == null || target == null) return;
        
        float[] floatingPoint = getFloatingHitPoint(target);
        float desiredYaw = floatingPoint[0];
        float desiredPitch = floatingPoint[1];
        
        float angleDiff = Math.abs(deltaAngle(mc.player.getYaw(), desiredYaw));
        if (angleDiff < 0.5f && Math.abs(mc.player.getPitch() - desiredPitch) < 0.3f) {
            return;
        }
        
        if (currentEasing == null || System.currentTimeMillis() > easingStartTime + easingDuration) {
            pickNewEasingPattern();
            startYaw = mc.player.getYaw();
            startPitch = mc.player.getPitch();
            targetYaw = desiredYaw;
            targetPitch = desiredPitch;
            easingStartTime = System.currentTimeMillis();
            
            float dist = (float) Math.sqrt(mc.player.squaredDistanceTo(target));
            float speedMult = calculateAdaptiveSpeed(dist, angleDiff, canAttack);
            easingDuration = (long)(1000.0 / (baseHorizontalSpeed * speedMult));
        }
        
        double elapsed = System.currentTimeMillis() - easingStartTime;
        double t = Math.min(1.0, elapsed / (double)easingDuration);
        double easedT = EASINGS.get(currentEasing).apply(t);
        
        float yawDelta = deltaAngle(startYaw, targetYaw);
        float pitchDelta = targetPitch - startPitch;
        
        double verticalFactor = 0.6;
        float newYaw = startYaw + (float)(yawDelta * easedT);
        float newPitch = startPitch + (float)(pitchDelta * easedT * verticalFactor);
        
        if (angleDiff > 30f && random.nextFloat() < 0.15f && t > 0.7) {
            float overshoot = (random.nextFloat() * 3.0f + 1.0f) * (random.nextBoolean() ? 1 : -1);
            newYaw += overshoot;
        }
        
        double time = System.currentTimeMillis() / 1000.0;
        float tremorX = (float)(Math.sin(time * microTremorFreq) * jitterAmplitude);
        float tremorY = (float)(Math.cos(time * microTremorFreq * 1.37) * jitterAmplitude * 0.5);
        
        if (random.nextInt(40) == 0) {
            tremorX += (random.nextFloat() - 0.5f) * 0.6f;
            tremorY += (random.nextFloat() - 0.5f) * 0.3f;
        }
        
        newYaw += tremorX;
        newPitch += tremorY;
        
        newYaw = normalizeAngle(newYaw);
        newPitch = MathHelper.clamp(newPitch, -90f, 90f);
        
        lastAppliedYaw = newYaw;
        lastAppliedPitch = newPitch;
        
        RotationProcess.update(new Rotation(newYaw, newPitch),
            baseHorizontalSpeed * 3.5f, baseVerticalSpeed * 2.5f,
            MathUtil.random(360, 390), MathUtil.random(360, 390),
            (int) MathUtil.random(3, 5), 1, false);
    }
    
    private float calculateAdaptiveSpeed(float dist, float angleDiff, boolean canAttack) {
        float mult = 1.0f;
        
        if (dist < 2.5f) mult *= 0.6f;
        else if (dist < 5.0f) mult *= 1.3f;
        else if (dist < 8.0f) mult *= 1.0f;
        else mult *= 0.7f;
        
        if (angleDiff < 5f) mult *= 0.3f;
        else if (angleDiff < 20f) mult *= 0.8f;
        else if (angleDiff > 60f) mult *= 1.4f;
        
        Vec3d velocity = mc.player.getVelocity();
        double speed = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
        mult *= (0.8f + speed * 0.5f);
        
        if (mc.player.fallDistance > 1.0f) mult *= 0.85f;
        
        if (mc.player.handSwingProgress > 0.0f && mc.player.handSwingProgress < 0.3f) mult *= 0.7f;
        
        return Math.max(0.2f, Math.min(mult, 2.5f));
    }
    
    private float[] getFloatingHitPoint(LivingEntity target) {
        Vec3d targetPos = target.getPos();
        float width = target.getWidth();
        float height = target.getHeight();
        
        double xOff = (random.nextDouble() - 0.5) * width * 0.6;
        double yOff = (random.nextDouble() - 0.5) * height * 0.4 + height * 0.1;
        double zOff = (random.nextDouble() - 0.5) * width * 0.6;
        
        double hitX = targetPos.x + xOff;
        double hitY = targetPos.y + height * 0.5 + yOff;
        double hitZ = targetPos.z + zOff;
        
        Vec3d eyePos = mc.player.getEyePos();
        double dx = hitX - eyePos.x;
        double dy = hitY - eyePos.y;
        double dz = hitZ - eyePos.z;
        
        float yaw = (float) Math.toDegrees(Math.atan2(-dx, dz));
        float pitch = (float) -Math.toDegrees(Math.atan2(dy, Math.hypot(dx, dz)));
        
        return new float[]{yaw, pitch};
    }
    
    private void pickNewEasingPattern() {
        String[] keys = EASINGS.keySet().toArray(new String[0]);
        currentEasing = keys[random.nextInt(keys.length)];
    }
    
    private float deltaAngle(float from, float to) {
        float diff = to - from;
        while (diff > 180f) diff -= 360f;
        while (diff < -180f) diff += 360f;
        return diff;
    }
    
    private float normalizeAngle(float angle) {
        angle %= 360f;
        if (angle >= 180f) angle -= 360f;
        if (angle < -180f) angle += 360f;
        return angle;
    }
}
