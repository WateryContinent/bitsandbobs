package com.waterycontinent.bitsandbobs.item;

import com.waterycontinent.bitsandbobs.BitsandBobs;
import com.waterycontinent.bitsandbobs.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BitsandBobs.MODID);

    public static final Supplier<CreativeModeTab> BITS_AND_BOBS = CREATIVE_MODE_TAB.register("bits_and_bobs",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.COCOCABRIQUETTE.get()))
                    .title(Component.translatable("creative.bitsandbobs.bits_and_bobs"))
                    .displayItems((itemDisplayParameters, output) -> {
                     output.accept(ModItems.COCOCABRIQUETTE);
                     output.accept(ModItems.DRAGONEGGSLIVER);
                     output.accept(ModItems.MINT_SEEDS);
                     output.accept(ModItems.MINT);
                     output.accept(ModItems.CRUSHED_MINT);
                     output.accept(ModItems.MINT_EXTRACT_BUCKET);
                     //output.accept(ModItems.MINT_CANDY);
                     output.accept(ModItems.UNFIRED_RECORD);
                     output.accept(ModItems.FIRED_RECORD);
                     output.accept(ModItems.THE_END_MUSIC_DISC);
                     output.accept(ModItems.CHARCOAL_DUST);
                     output.accept(ModItems.MINT_CAKE_BASE);
                     output.accept(ModItems.MINT_CAKE);
                     output.accept(ModItems.MINT_CAKE_SLICE);
                     output.accept(ModItems.CLARBON);
                     output.accept(ModItems.UNFIRED_CLARBON);

                     output.accept(ModBlocks.MINT_CAKE_BLOCK);
                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
