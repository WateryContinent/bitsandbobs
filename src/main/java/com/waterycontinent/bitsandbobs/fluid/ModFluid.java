package com.waterycontinent.bitsandbobs.fluid;

import com.waterycontinent.bitsandbobs.BitsandBobs;
import com.waterycontinent.bitsandbobs.fluid.types.MintExtractFluidType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

// From MCreator File - BitsandbobsModFluids.java
public class ModFluid {
    public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(BuiltInRegistries.FLUID, BitsandBobs.MODID);
    public static final DeferredHolder<Fluid, FlowingFluid> MINT_EXTRACT = REGISTRY.register("mint_extract", () -> new MintExtractFluid.Source());
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_MINT_EXTRACT = REGISTRY.register("flowing_mint_extract", () -> new MintExtractFluid.Flowing());

    @EventBusSubscriber(Dist.CLIENT)
    public static class FluidsClientSideHandler {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(MINT_EXTRACT.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_MINT_EXTRACT.get(), RenderType.translucent());
        }
    }

    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}

