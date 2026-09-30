package com.waterycontinent.bitsandbobs.menu;

import com.waterycontinent.bitsandbobs.block.ModBlocks;
import com.waterycontinent.bitsandbobs.block.entity.BeverageMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BeverageMachineMenu extends AbstractContainerMenu {
    private final Container machine;
    private final ContainerLevelAccess access;
    private final ContainerData data;

    public BeverageMachineMenu(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
        this(id, playerInventory, buffer.readBlockPos());
    }

    private BeverageMachineMenu(int id, Inventory playerInventory, BlockPos pos) {
        // Keep every incoming server field, including tank amount, fluid ID and cooling mode.
        this(id, playerInventory, new SimpleContainer(8), new SimpleContainerData(10),
                ContainerLevelAccess.create(playerInventory.player.level(), pos));
    }

    public BeverageMachineMenu(int id, Inventory playerInventory, BeverageMachineBlockEntity machine, ContainerData data, BlockPos pos) {
        this(id, playerInventory, machine, data, ContainerLevelAccess.create(playerInventory.player.level(), pos));
    }

    private BeverageMachineMenu(int id, Inventory playerInventory, Container machine, ContainerData data, ContainerLevelAccess access) {
        super(ModMenuTypes.BEVERAGE_MACHINE.get(), id);
        checkContainerSize(machine, 8);
        checkContainerDataCount(data, 10);
        this.machine = machine;
        this.data = data;
        this.access = access;
        machine.startOpen(playerInventory.player);

        addSlot(inputSlot(machine, 0, 59, 16)); // Top recipe ingredient
        addSlot(inputSlot(machine, 1, 59, 36)); // Middle recipe ingredient
        addSlot(inputSlot(machine, 2, 59, 56)); // Bottom recipe ingredient
        addSlot(inputSlot(machine, 3, 100, 16)); // Drink container
        addSlot(inputSlot(machine, 4, 12, 61)); // Left tank fluid input
        addSlot(inputSlot(machine, 5, 35, 61)); // Right tank fluid input
        addSlot(inputSlot(machine, 6, 101, 52)); // Fuel or snowball cooling input
        addSlot(new Slot(machine, 7, 144, 38) { // Beverage output
            @Override public boolean mayPlace(ItemStack stack) { return false; }
        });

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 108 + row * 18));
            }
        }
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInventory, column, 8 + column * 18, 166));
        }
        addDataSlots(data);
    }

    private static Slot inputSlot(Container machine, int index, int x, int y) {
        return new Slot(machine, index, x, y) {
            @Override public boolean mayPlace(ItemStack stack) {
                return BeverageMachineBlockEntity.isItemValid(index, stack);
            }

            @Override public int getMaxStackSize() {
                return index == 4 || index == 5 ? 1 : super.getMaxStackSize();
            }
        };
    }

    public int progress() { return data.get(0); }
    public int totalTime() { return data.get(1); }
    public int burnTime() { return data.get(2); }
    public int burnTotal() { return data.get(3); }
    public int tankAmount(int tank) { return data.get(tank == 0 ? 4 : 6); }
    public int tankFluidId(int tank) { return data.get(tank == 0 ? 5 : 7); }
    public boolean coolingMode() { return data.get(8) == 1; }
    public boolean isProcessing() { return data.get(9) == 1; }

    @Override public boolean stillValid(Player player) { return stillValid(access, player, ModBlocks.BEVERAGE_MACHINE.get()); }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack original = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            original = stack.copy();
            if (index < 8) {
                if (!moveItemStackTo(stack, 8, 44, true)) return ItemStack.EMPTY;
            } else {
                int start = stack.is(com.waterycontinent.bitsandbobs.item.ModItems.DRINK_CONTAINER.get()) ? 3
                        : BeverageMachineBlockEntity.isTankContainer(stack) ? 4
                        : BeverageMachineBlockEntity.isItemValid(6, stack) ? 6 : 0;
                int end = start == 0 ? 3 : start == 4 ? 6 : start + 1;
                if (!moveItemStackTo(stack, start, end, false)) return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
            slot.onTake(player, stack);
        }
        return original;
    }
    @Override
    public void removed(Player player) {
        super.removed(player);
        machine.stopOpen(player);
    }
}
