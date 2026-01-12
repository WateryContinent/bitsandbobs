package com.waterycontinent.bitsandbobs.fluid;

import com.waterycontinent.bitsandbobs.block.ModBlocks;
import com.waterycontinent.bitsandbobs.item.ModItems;
import com.waterycontinent.bitsandbobs.fluid.ModFluidTypes;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;

public abstract class MintExtractFluid extends BaseFlowingFluid {
    public static final BaseFlowingFluid.Properties PROPERTIES = new BaseFlowingFluid.Properties(() -> ModFluidTypes.MINT_EXTRACT_TYPE.get(), () -> ModFluid.MINT_EXTRACT.get(),
            () -> ModFluid.FLOWING_MINT_EXTRACT.get()).explosionResistance(100f).bucket(() -> ModItems.MINT_EXTRACT_BUCKET.get()).block(() -> (LiquidBlock) ModBlocks.MINT_EXTRACT.get());

    private MintExtractFluid() {
        super(PROPERTIES);
    }

    public static class Source extends MintExtractFluid {
        public int getAmount(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }

    public static class Flowing extends MintExtractFluid {
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        public boolean isSource(FluidState state) {
            return false;
        }
    }
}