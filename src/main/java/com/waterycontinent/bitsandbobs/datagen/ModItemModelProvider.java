package com.waterycontinent.bitsandbobs.datagen;

import com.waterycontinent.bitsandbobs.BitsandBobs;
import com.waterycontinent.bitsandbobs.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BitsandBobs.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.MINT_SEEDS.get());
        basicItem(ModItems.MINT.get());
        basicItem(ModItems.DRAGONEGGSLIVER.get());
        basicItem(ModItems.COCOCABRIQUETTE.get());
        basicItem(ModItems.CRUSHED_MINT.get());
        basicItem(ModItems.MINT_EXTRACT_BUCKET.get());
        basicItem(ModItems.MINT_CANDY.get());
        basicItem(ModItems.CHARCOAL_DUST.get());
        basicItem(ModItems.UNFIRED_RECORD.get());
        basicItem(ModItems.FIRED_RECORD.get());
        basicItem(ModItems.THE_END_MUSIC_DISC.get());
        basicItem(ModItems.MINT_CAKE_BASE.get());
        basicItem(ModItems.MINT_CAKE.get());
        basicItem(ModItems.MINT_CAKE_SLICE.get());
        basicItem(ModItems.UNFIRED_CLARBON.get());
        basicItem(ModItems.CLARBON.get());
    }
}