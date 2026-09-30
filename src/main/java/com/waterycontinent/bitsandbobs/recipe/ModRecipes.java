package com.waterycontinent.bitsandbobs.recipe;

import com.waterycontinent.bitsandbobs.BitsandBobs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModRecipes {
    public static final DeferredRegister<net.minecraft.world.item.crafting.RecipeType<?>> TYPES =
            DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, BitsandBobs.MODID);
    public static final DeferredHolder<net.minecraft.world.item.crafting.RecipeType<?>, net.minecraft.world.item.crafting.RecipeType<BeverageRecipe>> BEVERAGE_TYPE =
            TYPES.register("beverage_making", () -> BeverageRecipe.TYPE);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, BitsandBobs.MODID);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BeverageRecipe>> BEVERAGE_SERIALIZER = SERIALIZERS.register("beverage_making",
            () -> new RecipeSerializer<>() {
                @Override
                public com.mojang.serialization.MapCodec<BeverageRecipe> codec() {
                    return BeverageRecipe.CODEC;
                }

                @Override
                public net.minecraft.network.codec.StreamCodec<net.minecraft.network.RegistryFriendlyByteBuf, BeverageRecipe> streamCodec() {
                    return BeverageRecipe.STREAM_CODEC;
                }
            });

    private ModRecipes() {}

    public static void register(IEventBus eventBus) {
        TYPES.register(eventBus);
        SERIALIZERS.register(eventBus);
    }
}
