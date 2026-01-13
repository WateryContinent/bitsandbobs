package com.waterycontinent.bitsandbobs.block;

import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlock;
import com.simibubi.create.content.kinetics.simpleRelays.CogWheelBlock;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.infrastructure.config.CStress;
import com.waterycontinent.bitsandbobs.BitsandBobs;
import com.waterycontinent.bitsandbobs.block.custom.MintCakeBlock;
import com.waterycontinent.bitsandbobs.item.ModItems;
import com.waterycontinent.bitsandbobs.block.custom.MintExtractBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.mrh0.createaddition.CreateAddition.REGISTRATE;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

public class ModBlocks {

    // Creating the DeferredRegister of Blocks
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(BitsandBobs.MODID);


    // Mint Crop Block
    public static final DeferredBlock<Block> MINT_CROP = BLOCKS.register("mint_plant",
            () -> new MintCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CARROTS)));

    // Mint Cake Block
    public static final DeferredBlock<Block> MINT_CAKE_BLOCK = BLOCKS.register("mint_cake_block",
            () -> new MintCakeBlock(BlockBehaviour.Properties.of().strength(0.5f).sound(SoundType.WOOL)));

    // Mint Extract Fluid Block
    public static final DeferredBlock<Block> MINT_EXTRACT = BLOCKS.register("mint_extract", MintExtractBlock::new);

    // Registers the block
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        return toReturn;
    }

    // Registers the item associated with Block
    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    // Calling the DeferredRegister of Blocks
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
