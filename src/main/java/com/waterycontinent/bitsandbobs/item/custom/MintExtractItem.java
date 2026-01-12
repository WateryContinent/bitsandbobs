package com.waterycontinent.bitsandbobs.item.custom;

import com.waterycontinent.bitsandbobs.fluid.ModFluid;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class MintExtractItem extends BucketItem {
    public MintExtractItem() {
        super(ModFluid.MINT_EXTRACT.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)

        );
    }
}