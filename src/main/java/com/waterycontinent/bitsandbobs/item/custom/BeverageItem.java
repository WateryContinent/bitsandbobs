package com.waterycontinent.bitsandbobs.item.custom;

import com.waterycontinent.bitsandbobs.item.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class BeverageItem extends Item {
    public BeverageItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack remaining = super.finishUsingItem(stack, level, entity);
        if (remaining.isEmpty()) return new ItemStack(ModItems.DRINK_CONTAINER.get());

        // Keep the remaining drinks in hand and return the empty container separately.
        if (!level.isClientSide && entity instanceof Player player && !player.hasInfiniteMaterials()) {
            ItemStack container = new ItemStack(ModItems.DRINK_CONTAINER.get());
            if (!player.getInventory().add(container)) player.drop(container, false);
        }
        return remaining;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }
}
