package com.waterycontinent.bitsandbobs.block.entity;

import com.waterycontinent.bitsandbobs.BitsandBobs;
import com.waterycontinent.bitsandbobs.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, BitsandBobs.MODID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BeverageMachineBlockEntity>> BEVERAGE_MACHINE = REGISTRY.register("beverage_machine",
            () -> BlockEntityType.Builder.of(BeverageMachineBlockEntity::new, ModBlocks.BEVERAGE_MACHINE.get()).build(null));

    private ModBlockEntities() {}

    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}
