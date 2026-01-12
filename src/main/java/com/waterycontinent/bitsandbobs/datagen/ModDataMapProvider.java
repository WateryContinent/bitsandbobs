package com.waterycontinent.bitsandbobs.datagen;

import com.waterycontinent.bitsandbobs.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    // Depreciated
    protected void gather() {
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(ModItems.COCOCABRIQUETTE.getId(), new FurnaceFuel(1200), false);

        this.builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(ModItems.MINT_SEEDS.getId(), new Compostable(0.25f), false)
                .add(ModItems.CRUSHED_MINT.getId(), new Compostable(0.35f), false)
                .add(ModItems.MINT.getId(), new Compostable(0.45f), false);
    }
}