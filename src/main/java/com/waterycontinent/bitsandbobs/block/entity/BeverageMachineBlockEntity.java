package com.waterycontinent.bitsandbobs.block.entity;

import com.waterycontinent.bitsandbobs.block.custom.BeverageMachineBlock;
import com.waterycontinent.bitsandbobs.menu.BeverageMachineMenu;
import com.waterycontinent.bitsandbobs.recipe.BeverageRecipe;
import com.waterycontinent.bitsandbobs.recipe.BeverageRecipeInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.ContainerHelper;
import net.minecraft.resources.ResourceLocation;
import com.waterycontinent.bitsandbobs.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.inventory.ContainerData;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import java.util.Objects;

public class BeverageMachineBlockEntity extends BaseContainerBlockEntity {
    private NonNullList<ItemStack> items = NonNullList.withSize(8, ItemStack.EMPTY);
    private final FluidTank[] tanks = {new FluidTank(1000), new FluidTank(1000)};
    private int progress;
    private int totalTime;
    private int burnTime;
    private int burnTotal;
    private int processingMode;
    private boolean processing;
    private ResourceLocation activeRecipeId;
    private final ContainerData data = new ContainerData() {
        public int get(int index) {
            return switch (index) {
                case 0 -> progress;
                case 1 -> totalTime;
                case 2 -> burnTime;
                case 3 -> burnTotal;
                case 4 -> tanks[0].getFluidAmount();
                case 5 -> BuiltInRegistries.FLUID.getId(tanks[0].getFluid().getFluid());
                case 6 -> tanks[1].getFluidAmount();
                case 7 -> BuiltInRegistries.FLUID.getId(tanks[1].getFluid().getFluid());
                case 8 -> processingMode == 2 ? 1 : 0;
                case 9 -> processing ? 1 : 0;
                default -> 0;
            };
        }

        public void set(int index, int value) {
            switch (index) {
                case 0 -> progress = value;
                case 1 -> totalTime = value;
                case 2 -> burnTime = value;
                case 3 -> burnTotal = value;
            }
        }

        public int getCount() { return 10; }
    };

    public BeverageMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BEVERAGE_MACHINE.get(), pos, state);
    }

    @Override protected Component getDefaultName() { return Component.translatable("container.bitsandbobs.beverage_machine"); }
    @Override protected NonNullList<ItemStack> getItems() { return items; }
    @Override protected void setItems(NonNullList<ItemStack> items) { this.items = items; }
    @Override public int getContainerSize() { return 8; }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return isItemValid(slot, stack);
    }

    public static boolean isTankContainer(ItemStack stack) {
        return stack.getItem() instanceof net.minecraft.world.item.BucketItem
                || stack.getItem() instanceof net.minecraft.world.item.PotionItem
                || stack.is(Items.MILK_BUCKET) || stack.is(Items.GLASS_BOTTLE) || stack.is(Items.HONEY_BOTTLE)
                || stack.is(net.neoforged.neoforge.common.Tags.Items.DRINK_CONTAINING_BOTTLE);
    }

    public static boolean isItemValid(int slot, ItemStack stack) {
        if (stack.isEmpty()) return false;
        if (slot >= 0 && slot < 3) return true;
        if (slot == 3) return stack.is(ModItems.DRINK_CONTAINER.get());
        if (slot == 4 || slot == 5) return isTankContainer(stack);
        if (slot == 6) return stack.getBurnTime(null) > 0 || stack.is(Items.SNOWBALL);
        return false;
    }

    @Override protected BeverageMachineMenu createMenu(int id, Inventory inventory) {
        return new BeverageMachineMenu(id, inventory, this, data, worldPosition);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items, registries);
        activeRecipeId = ResourceLocation.tryParse(tag.getString("ActiveRecipe"));
        progress = tag.getInt("Progress");
        totalTime = tag.getInt("TotalTime");
        burnTime = tag.getInt("BurnTime");
        burnTotal = tag.getInt("BurnTotal");
        processingMode = tag.getInt("ProcessingMode");
        if (tag.contains("Tank0")) tanks[0].readFromNBT(registries, tag.getCompound("Tank0"));
        if (tag.contains("Tank1")) tanks[1].readFromNBT(registries, tag.getCompound("Tank1"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
        if (activeRecipeId != null) tag.putString("ActiveRecipe", activeRecipeId.toString());
        tag.putInt("Progress", progress);
        tag.putInt("TotalTime", totalTime);
        tag.putInt("BurnTime", burnTime);
        tag.putInt("BurnTotal", burnTotal);
        tag.putInt("ProcessingMode", processingMode);
        tag.put("Tank0", tanks[0].writeToNBT(registries, new CompoundTag()));
        tag.put("Tank1", tanks[1].writeToNBT(registries, new CompoundTag()));
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, BeverageMachineBlockEntity machine) {
        if (level.isClientSide) return;
        machine.fillTankFromSlot(4, 0);
        machine.fillTankFromSlot(5, 1);

        BeverageRecipeInput input = new BeverageRecipeInput(machine.getItem(0), machine.getItem(1),
                machine.getItem(2), machine.getItem(3));
        RecipeHolder<BeverageRecipe> holder = level.getRecipeManager().getRecipesFor(BeverageRecipe.TYPE, input, level)
                .stream().filter(recipe -> machine.hasRecipeFluids(recipe.value())).findFirst().orElse(null);
        ResourceLocation recipeId = holder == null ? null : holder.id();
        if (!Objects.equals(machine.activeRecipeId, recipeId)) {
            machine.activeRecipeId = recipeId;
            machine.progress = 0;
            machine.setChanged();
        }
        machine.totalTime = holder == null ? 0 : holder.value().processingTime();
        boolean running = false;
        if (holder != null && machine.hasRoom(7, holder.value().result())) {
            BeverageRecipe recipe = holder.value();
            boolean cold = recipe.temperature().equals("cold");
            int mode = cold ? 2 : 1;
            // Stored heat cannot power a cold recipe, or vice versa.
            if (machine.processingMode != mode) machine.burnTime = 0;
            if (machine.burnTime == 0) machine.startProcessing(cold);
            if (machine.burnTime > 0) {
                running = true;
                machine.burnTime--;
                if (++machine.progress >= machine.totalTime) {
                    machine.addOutput(7, recipe.result());
                    // Matching assigns one ingredient to each occupied input, in any order.
                    for (int slot = 0; slot < 3; slot++) {
                        if (!machine.getItem(slot).isEmpty()) machine.removeItem(slot, 1);
                    }
                    machine.removeItem(3, 1);
                    recipe.firstFluid().ifPresent(fluid -> machine.tanks[0].drain(fluid.amount(),
                            net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE));
                    recipe.secondFluid().ifPresent(fluid -> machine.tanks[1].drain(fluid.amount(),
                            net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE));
                    machine.progress = 0;
                }
                machine.setChanged();
            }
        }

        machine.processing = running;
        boolean lit = running && machine.processingMode == 1;
        boolean cooling = running && machine.processingMode == 2;
        if (state.getValue(BeverageMachineBlock.LIT) != lit || state.getValue(BeverageMachineBlock.COOLING) != cooling) {
            level.setBlock(pos, state.setValue(BeverageMachineBlock.LIT, lit).setValue(BeverageMachineBlock.COOLING, cooling), 3);
        }
    }

    private void fillTankFromSlot(int slot, int tankIndex) {
        ItemStack input = getItem(slot);
        if (input.isEmpty() || input.getCount() != 1 || !isTankContainer(input)) return;
        // This also handles vanilla milk buckets once enableMilkFluid() is called.
        // Only replace the input after the tank actually accepts fluid.
        var result = FluidUtil.tryEmptyContainer(input, tanks[tankIndex], Integer.MAX_VALUE, null, true);
        if (result.isSuccess()) {
            setItem(slot, result.getResult());
            return;
        }
        // Vanilla bottles do not expose a fluid capability. Transfer their full
        // 250 mB contents before replacing the input with an empty glass bottle.
        FluidStack fluid = FluidStack.EMPTY;
        if (input.is(Items.POTION) && input.getOrDefault(net.minecraft.core.component.DataComponents.POTION_CONTENTS,
                net.minecraft.world.item.alchemy.PotionContents.EMPTY).is(net.minecraft.world.item.alchemy.Potions.WATER)) {
            fluid = new FluidStack(net.minecraft.world.level.material.Fluids.WATER, 250);
        } else if (input.is(Items.HONEY_BOTTLE)) {
            fluid = new FluidStack(BuiltInRegistries.FLUID.get(ResourceLocation.parse("create:honey")), 250);
        } else if (input.is(net.neoforged.neoforge.common.Tags.Items.DRINK_CONTAINING_BOTTLE)
                && input.is(net.neoforged.neoforge.common.Tags.Items.DRINKS_MILK)) {
            fluid = new FluidStack(net.neoforged.neoforge.common.NeoForgeMod.MILK.get(), 250);
        }
        if (!fluid.isEmpty() && tanks[tankIndex].fill(fluid,
                net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction.SIMULATE) == fluid.getAmount()) {
            tanks[tankIndex].fill(fluid, net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE);
            setItem(slot, new ItemStack(Items.GLASS_BOTTLE));
        }
    }

    private void startProcessing(boolean cold) {
        ItemStack fuel = getItem(6);
        int duration = cold ? 200 : fuel.getBurnTime(null);
        if (cold && !fuel.is(Items.SNOWBALL)) return;
        if (duration <= 0) return;
        burnTime = burnTotal = duration;
        processingMode = cold ? 2 : 1;
        if (cold) {
            fuel.shrink(1);
            setChanged();
            return;
        }
        ItemStack remainder = fuel.getCraftingRemainingItem();
        fuel.shrink(1);
        if (fuel.isEmpty() && !remainder.isEmpty()) setItem(6, remainder);
        setChanged();
    }

    private boolean hasRecipeFluids(BeverageRecipe recipe) {
        return recipe.firstFluid().map(fluid -> fluid.test(tanks[0].getFluid())).orElse(true)
                && recipe.secondFluid().map(fluid -> fluid.test(tanks[1].getFluid())).orElse(true);
    }

    public FluidStack tankFluid(int index) { return tanks[index].getFluid(); }
    public ContainerData containerData() { return data; }

    private boolean hasRoom(int slot, ItemStack output) {
        if (output.isEmpty()) return true;
        ItemStack existing = getItem(slot);
        if (output.getCount() > Math.min(getMaxStackSize(), output.getMaxStackSize())) return false;
        return existing.isEmpty() || (ItemStack.isSameItemSameComponents(existing, output)
                && existing.getCount() + output.getCount() <= Math.min(getMaxStackSize(), output.getMaxStackSize()));
    }

    private void addOutput(int slot, ItemStack output) {
        if (output.isEmpty()) return;
        ItemStack existing = getItem(slot);
        if (existing.isEmpty()) setItem(slot, output.copy());
        else existing.grow(output.getCount());
    }
}
