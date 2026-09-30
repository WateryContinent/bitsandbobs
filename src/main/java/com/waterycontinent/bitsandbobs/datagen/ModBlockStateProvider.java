package com.waterycontinent.bitsandbobs.datagen;

import com.waterycontinent.bitsandbobs.BitsandBobs;
import com.waterycontinent.bitsandbobs.block.MintCropBlock;
import com.waterycontinent.bitsandbobs.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BitsandBobs.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
    makeCrop(((CropBlock) ModBlocks.MINT_CROP.get()),"mint_crop_stage", "mint_crop_stage");
    simpleBlockWithItem(ModBlocks.BLOCK_OF_CLARBON.get(), cubeAll(ModBlocks.BLOCK_OF_CLARBON.get()));

    }

    public void makeCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((MintCropBlock) block).getAgeProperty()),
                ResourceLocation.fromNamespaceAndPath(BitsandBobs.MODID, "block/" + textureName + state.getValue(((MintCropBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }


}
