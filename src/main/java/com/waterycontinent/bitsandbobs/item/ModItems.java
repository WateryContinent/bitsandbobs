package com.waterycontinent.bitsandbobs.item;

import com.waterycontinent.bitsandbobs.BitsandBobs;
import com.waterycontinent.bitsandbobs.block.ModBlocks;
import com.waterycontinent.bitsandbobs.item.custom.*;
import com.waterycontinent.bitsandbobs.sound.ModSounds;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BitsandBobs.MODID);

    public static final DeferredItem<Item> COCOCABRIQUETTE = ITEMS.register("cocoa_briquette",
            () -> new FuelItem(new Item.Properties(), 2000));

    public static final DeferredItem<Item> DRAGONEGGSLIVER = ITEMS.register("dragon_egg_sliver",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> MINT = ITEMS.register("mint",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CRUSHED_MINT = ITEMS.register("crushed_mint",
            () -> new CrushedMintItem(new Item.Properties().food(FoodItem.CRUSHED_MINT)));

    public static final DeferredItem<Item> MINT_SEEDS = ITEMS.register("mint_seeds",
            () -> new ItemNameBlockItem(ModBlocks.MINT_CROP.get(), new Item.Properties()));

    public static final DeferredItem<Item> MINT_CANDY = ITEMS.register("mint_candy",
            () -> new MintCandyItem(new Item.Properties().food(FoodItem.MINT_CANDY)));

    public static final DeferredItem<Item> GUMMY_BERRY = ITEMS.register("berry_gummy",
            () -> new GummyItem(new Item.Properties().food(FoodItem.BERRY_GUMMY)));

    public static final DeferredItem<Item> GUMMY_MINT = ITEMS.register("mint_gummy",
            () -> new GummyItem(new Item.Properties().food(FoodItem.MINT_GUMMY)));

    public static final DeferredItem<Item> CHARCOAL_DUST = ITEMS.register("charcoal_dust",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> UNFIRED_RECORD = ITEMS.register("unfired_record",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> FIRED_RECORD = ITEMS.register("fired_record",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> THE_END_MUSIC_DISC = ITEMS.register("the_end_music_disc",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.THE_END_KEY).rarity(Rarity.EPIC).stacksTo(1)));

    public static final DeferredItem<Item> ARIA_MATH_MUSIC_DISC = ITEMS.register("aria_math_music_disc",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.ARIA_MATH_KEY).rarity(Rarity.EPIC).stacksTo(1)));

    public static final DeferredItem<Item> MINT_CAKE_BASE = ITEMS.register("mint_cake_base",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CLARBON = ITEMS.register("clarbon",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<BlockItem> BLOCK_OF_CLARBON = ITEMS.register("block_of_clarbon",
            () -> new BlockItem(ModBlocks.BLOCK_OF_CLARBON.get(), new Item.Properties()));
    public static final DeferredItem<Item> WET_GELATIN = ITEMS.register("wet_gelatin",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GELATIN_SHEET = ITEMS.register("gelatin_sheet",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GELATIN_POWDER = ITEMS.register("gelatin_powder",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> UNFIRED_CLARBON = ITEMS.register("unfired_clarbon",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> MINT_CAKE = ITEMS.register("mint_cake",
            () -> new ItemNameBlockItem(ModBlocks.MINT_CAKE_BLOCK.get(), new Item.Properties()));

    public static final DeferredItem<Item> MINT_CAKE_SLICE = ITEMS.register("mint_cake_slice",
            () -> new Item(new Item.Properties().food(FoodItem.MINT_CAKE_SLICE)));

    public static final DeferredItem<BlockItem> BEVERAGE_MACHINE = ITEMS.register("beverage_machine",
            () -> new BlockItem(ModBlocks.BEVERAGE_MACHINE.get(), new Item.Properties()));

    public static final DeferredItem<Item> DRINK_CONTAINER = ITEMS.register("drink_container",
            () -> new Item(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> CHOCOLATE_MINT_MILKSHAKE = ITEMS.register("chocolate_mint_milkshake",
            () -> new BeverageItem(new Item.Properties().stacksTo(1).food(FoodItem.CHOCOLATE_MINT_MILKSHAKE)));
    public static final DeferredItem<Item> WATERMELON_SLUSHIE = ITEMS.register("watermelon_slushie",
            () -> new BeverageItem(new Item.Properties().stacksTo(1).food(FoodItem.WATERMELON_SLUSHIE)));


    // USED TO MAKE BUCKETS OF FLUIDS
    public static final DeferredItem<Item> MINT_EXTRACT_BUCKET;
    static {
        MINT_EXTRACT_BUCKET = ITEMS.register("mint_extract_bucket", MintExtractItem::new);
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
