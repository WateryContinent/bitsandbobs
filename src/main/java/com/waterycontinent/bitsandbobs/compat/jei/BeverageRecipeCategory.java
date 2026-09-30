package com.waterycontinent.bitsandbobs.compat.jei;

import com.waterycontinent.bitsandbobs.BitsandBobs;
import com.waterycontinent.bitsandbobs.item.ModItems;
import com.waterycontinent.bitsandbobs.recipe.BeverageRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class BeverageRecipeCategory implements IRecipeCategory<BeverageRecipe> {
    public static final RecipeType<BeverageRecipe> TYPE = RecipeType.create(BitsandBobs.MODID, "beverage_making", BeverageRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;
    private final List<ItemStack> heatingFuels;

    public BeverageRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(ResourceLocation.fromNamespaceAndPath(BitsandBobs.MODID,
                "textures/gui/beverage_machine.png"), 8, 14, 160, 64);
        icon = guiHelper.createDrawableItemStack(new ItemStack(ModItems.BEVERAGE_MACHINE.get()));
        heatingFuels = BuiltInRegistries.ITEM.stream().map(ItemStack::new)
                .filter(stack -> stack.getBurnTime(null) > 0).toList();
    }

    @Override public RecipeType<BeverageRecipe> getRecipeType() { return TYPE; }
    @Override public Component getTitle() { return Component.translatable("container.bitsandbobs.beverage_machine"); }
    @Override public IDrawable getIcon() { return icon; }
    @Override public int getWidth() { return 160; }
    @Override public int getHeight() { return 92; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BeverageRecipe recipe, IFocusGroup focuses) {
        // Keep the four item inputs first, in machine slot order, including optional empty inputs.
        builder.addSlot(RecipeIngredientRole.INPUT, 51, 2).addIngredients(recipe.firstIngredient());
        builder.addSlot(RecipeIngredientRole.INPUT, 51, 22).addIngredients(recipe.secondIngredient());
        builder.addSlot(RecipeIngredientRole.INPUT, 51, 42).addIngredients(recipe.thirdIngredient());
        builder.addSlot(RecipeIngredientRole.INPUT, 92, 2).addIngredients(recipe.container());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 136, 24).addItemStack(recipe.result());

        recipe.firstFluid().ifPresent(fluid -> addTank(builder, fluid, 0));
        recipe.secondFluid().ifPresent(fluid -> addTank(builder, fluid, 1));
        builder.addSlot(RecipeIngredientRole.CATALYST, 93, 38)
                .addItemStacks(recipe.temperature().equals("cold") ? List.of(new ItemStack(Items.SNOWBALL)) : heatingFuels);
    }

    private static void addTank(IRecipeLayoutBuilder builder, SizedFluidIngredient ingredient, int tank) {
        builder.addSlot(RecipeIngredientRole.INPUT, tank == 0 ? 5 : 28, 3)
                .setSlotName("tank_" + tank).setFluidRenderer(1000, false, 14, 38)
                .addIngredients(NeoForgeTypes.FLUID_STACK, Arrays.asList(ingredient.getFluids()));
    }

    @Override
    public void draw(BeverageRecipe recipe, IRecipeSlotsView slots, GuiGraphics graphics, double mouseX, double mouseY) {
        background.draw(graphics);
        var font = Minecraft.getInstance().font;
        Component mode = Component.translatable("gui.bitsandbobs.beverage_machine."
                + (recipe.temperature().equals("cold") ? "cooling" : "heating"));
        Component duration = Component.translatable("jei.bitsandbobs.beverage_machine.duration", mode,
                String.format(Locale.ROOT, "%.1f", recipe.processingTime() / 20.0));
        graphics.drawString(font, duration, (160 - font.width(duration)) / 2, 66, 0xff404040, false);
        graphics.drawString(font, Component.translatable("jei.bitsandbobs.beverage_machine.manual_loading"),
                0, 81, 0xff606060, false);
    }
}
