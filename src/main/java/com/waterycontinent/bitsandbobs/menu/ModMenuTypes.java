package com.waterycontinent.bitsandbobs.menu;

import com.waterycontinent.bitsandbobs.BitsandBobs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, BitsandBobs.MODID);
    public static final DeferredHolder<MenuType<?>, MenuType<BeverageMachineMenu>> BEVERAGE_MACHINE = REGISTRY.register("beverage_machine",
            () -> IMenuTypeExtension.create(BeverageMachineMenu::new));

    private ModMenuTypes() {}

    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}
