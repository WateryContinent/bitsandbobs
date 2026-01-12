package com.waterycontinent.bitsandbobs.effect;

import com.waterycontinent.bitsandbobs.BitsandBobs;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, BitsandBobs.MODID);

        // Registering the "Fresh" Effect
        public static final Holder<MobEffect> FRESH = MOB_EFFECTS.register("fresh",
                () -> new FreshEffect(MobEffectCategory.NEUTRAL, 12438015));


    public static void register(IEventBus eventbus){
        MOB_EFFECTS.register(eventbus);
    }
}
