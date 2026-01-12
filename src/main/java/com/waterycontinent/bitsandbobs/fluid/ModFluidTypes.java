package com.waterycontinent.bitsandbobs.fluid;

import com.waterycontinent.bitsandbobs.BitsandBobs;
import com.waterycontinent.bitsandbobs.fluid.types.MintExtractFluidType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;// BitsandbobsModFluidTypes.java
public class ModFluidTypes {
    public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, BitsandBobs.MODID);
    public static final DeferredHolder<FluidType, FluidType> MINT_EXTRACT_TYPE = REGISTRY.register("mint_extract", () -> new MintExtractFluidType());

    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}