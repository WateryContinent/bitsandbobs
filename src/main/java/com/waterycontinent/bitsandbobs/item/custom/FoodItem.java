package com.waterycontinent.bitsandbobs.item.custom;

import com.waterycontinent.bitsandbobs.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class FoodItem {
    public static final FoodProperties CRUSHED_MINT = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f)
            .fast().alwaysEdible().effect(() -> new MobEffectInstance(ModEffects.FRESH, 50), 1f).build();

    public static final FoodProperties MINT_CANDY = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f)
            .fast().alwaysEdible().effect(() -> new MobEffectInstance(ModEffects.FRESH, 200), 1f).build();

    public static final FoodProperties MINT_CAKE_SLICE = new FoodProperties.Builder().nutrition(4).saturationModifier(1f).build();
}
