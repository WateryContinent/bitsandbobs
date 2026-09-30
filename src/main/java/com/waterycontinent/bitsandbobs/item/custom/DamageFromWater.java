package com.waterycontinent.bitsandbobs.item.custom;

import com.waterycontinent.bitsandbobs.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class DamageFromWater {
    public static void execute(Entity entity) {
        if (!(entity instanceof LivingEntity living) || living.level().isClientSide() || !living.isInWater()) return;
        MobEffectInstance fresh = living.getEffect(ModEffects.FRESH);
        if (fresh != null && fresh.getAmplifier() == 1) {
            living.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 2));
        }
    }
}
