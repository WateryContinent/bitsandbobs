package com.waterycontinent.bitsandbobs.compat.jei;

import com.waterycontinent.bitsandbobs.menu.BeverageMachineMenu;
import com.waterycontinent.bitsandbobs.menu.ModMenuTypes;
import com.waterycontinent.bitsandbobs.recipe.BeverageRecipe;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;

import java.util.Optional;

public class BeverageRecipeTransferHandler implements IRecipeTransferHandler<BeverageMachineMenu, BeverageRecipe> {
    private final IRecipeTransferHandlerHelper helper;
    private final IRecipeTransferHandler<BeverageMachineMenu, BeverageRecipe> itemTransfer;

    public BeverageRecipeTransferHandler(IRecipeTransferHandlerHelper helper) {
        this.helper = helper;
        itemTransfer = helper.createUnregisteredRecipeTransferHandler(helper.createBasicRecipeTransferInfo(
                BeverageMachineMenu.class, ModMenuTypes.BEVERAGE_MACHINE.get(), BeverageRecipeCategory.TYPE,
                0, 4, 8, 36));
    }

    @Override public Class<BeverageMachineMenu> getContainerClass() { return BeverageMachineMenu.class; }
    @Override public Optional<MenuType<BeverageMachineMenu>> getMenuType() { return Optional.of(ModMenuTypes.BEVERAGE_MACHINE.get()); }
    @Override public RecipeType<BeverageRecipe> getRecipeType() { return BeverageRecipeCategory.TYPE; }

    @Override
    public IRecipeTransferError transferRecipe(BeverageMachineMenu menu, BeverageRecipe recipe, IRecipeSlotsView slots,
                                              Player player, boolean maxTransfer, boolean doTransfer) {
        // JEI's basic handler moves item stacks, so exclude fluid requirements from that view.
        var itemSlots = slots.getSlotViews().stream()
                .filter(slot -> !slot.getSlotName().orElse("").startsWith("tank_")).toList();
        return itemTransfer.transferRecipe(menu, recipe, helper.createRecipeSlotsView(itemSlots), player, maxTransfer, doTransfer);
    }
}
