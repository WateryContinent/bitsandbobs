package com.waterycontinent.bitsandbobs.block.custom;

import com.waterycontinent.bitsandbobs.fluid.ModFluid;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class MintExtractBlock extends LiquidBlock {
    public MintExtractBlock() {
        super(ModFluid.MINT_EXTRACT.get(), BlockBehaviour.Properties.of().mapColor(MapColor.WATER).strength(100f).noCollission().noLootTable().liquid().pushReaction(PushReaction.DESTROY).sound(SoundType.EMPTY).replaceable());
    }
}