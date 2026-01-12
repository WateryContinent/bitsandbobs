package com.waterycontinent.bitsandbobs.item.custom;

import com.waterycontinent.bitsandbobs.BitsandBobs;
import com.waterycontinent.bitsandbobs.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;

public class DamageFromWater{
        public static void execute(Entity entity) {
            if (entity == null)
                return;
            if ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(ModEffects.FRESH) ? _livEnt.getEffect(MobEffects.ABSORPTION).getAmplifier() : 0) == 1) {
                if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                    _entity.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 2));
            }
        }

    }
