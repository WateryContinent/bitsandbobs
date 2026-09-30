package com.waterycontinent.bitsandbobs.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record BeverageRecipeInput(ItemStack firstIngredient, ItemStack secondIngredient, ItemStack thirdIngredient, ItemStack container) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> firstIngredient;
            case 1 -> secondIngredient;
            case 2 -> thirdIngredient;
            case 3 -> container;
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public int size() {
        return 4;
    }
}
