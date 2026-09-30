package com.waterycontinent.bitsandbobs.client;

import com.waterycontinent.bitsandbobs.BitsandBobs;
import com.waterycontinent.bitsandbobs.menu.BeverageMachineMenu;
import com.waterycontinent.bitsandbobs.menu.ModMenuTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.fluids.FluidStack;

@EventBusSubscriber(modid = BitsandBobs.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class BeverageMachineScreen extends AbstractContainerScreen<BeverageMachineMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BitsandBobs.MODID, "textures/gui/beverage_machine.png");
    // Pixel shapes from the supplied GUI, starting at (102, 36).
    private static final String[] FLAME_ROWS = {
            " #         # ", " #         # ", "  #   #   #  ", "  #   #   #  ",
            " ##    #  ## ", " ##    #  ## ", "###   ##  ###", "##    ##   ##",
            "##   ###   ##", "###  ##   ###", " ##  ##   ## ", " ##  ###  ## ",
            "###  ###  ###"
    };
    public BeverageMachineScreen(BeverageMachineMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        imageWidth = 176;
        imageHeight = 190;
        titleLabelY = 6;
        inventoryLabelY = 96;
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.BEVERAGE_MACHINE.get(), BeverageMachineScreen::new);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(TEXTURE, leftPos, topPos, 0.0F, 0.0F, imageWidth, imageHeight, 256, 256);
        for (int tank = 0; tank < 2; tank++) {
            int amount = menu.tankAmount(tank);
            if (amount <= 0) continue;
            var fluid = BuiltInRegistries.FLUID.byId(menu.tankFluidId(tank));
            FluidStack stack = new FluidStack(fluid, amount);
            if (stack.isEmpty()) continue;
            var extensions = IClientFluidTypeExtensions.of(fluid);
            ResourceLocation texture = extensions.getStillTexture(stack);
            if (texture == null) continue;
            var sprite = minecraft.getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(texture);
            int color = extensions.getTintColor(stack);
            int height = Math.max(1, Math.min(38, 38 * amount / 1000));
            int x = leftPos + (tank == 0 ? 13 : 36);
            int bottom = topPos + 55;

            // Tile at native size and clip to the inside of the supplied tank artwork.
            graphics.enableScissor(x, bottom - height, x + 14, bottom);
            for (int y = bottom - 16; y > bottom - height - 16; y -= 16) {
                graphics.blit(x, y, 0, 16, 16, sprite,
                        ((color >> 16) & 255) / 255.0F, ((color >> 8) & 255) / 255.0F,
                        (color & 255) / 255.0F, ((color >>> 24) & 255) / 255.0F);
            }
            graphics.disableScissor();
        }

        if (menu.isProcessing() && menu.burnTime() > 0 && menu.burnTotal() > 0) {
            // Like a furnace, the lit portion shrinks from the top as fuel runs out.
            int height = Math.min(13, (13 * menu.burnTime() + menu.burnTotal() - 1) / menu.burnTotal());
            for (int row = 13 - height; row < FLAME_ROWS.length; row++) {
                int color = menu.coolingMode()
                        ? (row < 7 ? 0xff59bdea : 0xffc7f5ff)
                        : (row < 7 ? 0xffff8b22 : 0xffffe66b);
                for (int column = 0; column < FLAME_ROWS[row].length(); column++) {
                    if (FLAME_ROWS[row].charAt(column) != '#') continue;
                    int x = leftPos + 102 + column;
                    int y = topPos + 36 + row;
                    graphics.fill(x, y, x + 1, y + 1, color);
                }
            }
        }

        // A recessed vanilla-style track in the space above the player inventory.
        int x = leftPos + 8;
        int y = topPos + 90;
        graphics.fill(x, y, x + 160, y + 5, 0xff373737);
        graphics.fill(x + 1, y + 1, x + 159, y + 4, 0xff8b8b8b);
        graphics.fill(x, y + 5, x + 161, y + 6, 0xffffffff);
        graphics.fill(x + 160, y, x + 161, y + 5, 0xffffffff);
        int width = menu.totalTime() <= 0 ? 0
                : Math.min(158, 158 * menu.progress() / menu.totalTime());
        if (width > 0) {
            int color = !menu.isProcessing() ? 0xffb0b0b0
                    : menu.coolingMode() ? 0xff79d8f2 : 0xffffc65b;
            graphics.fill(x + 1, y + 1, x + 1 + width, y + 4, color);
            graphics.fill(x + 1, y + 1, x + 1 + width, y + 2, 0xffeeeeee);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderLabels(graphics, mouseX, mouseY);
        String status = menu.totalTime() <= 0
                ? (menu.slots.get(7).hasItem() ? "ready" : "idle")
                : !menu.isProcessing() ? "paused" : menu.coolingMode() ? "cooling" : "heating";
        graphics.drawString(font, Component.translatable("gui.bitsandbobs.beverage_machine." + status),
                8, 80, 0xff404040, false);
        if (menu.totalTime() > 0) {
            // Round up to tenths so a drink never shows zero seconds before finishing.
            int tenths = (Math.max(0, menu.totalTime() - menu.progress()) + 1) / 2;
            Component remaining = Component.translatable("gui.bitsandbobs.beverage_machine.remaining",
                    tenths / 10 + "." + tenths % 10);
            graphics.drawString(font, remaining, 168 - font.width(remaining), 80, 0xff404040, false);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        int tank = hoveredTank(mouseX, mouseY);
        if (tank >= 0) {
            int amount = menu.tankAmount(tank);
            Component content = amount == 0 ? Component.literal("Empty")
                    : new FluidStack(BuiltInRegistries.FLUID.byId(menu.tankFluidId(tank)), amount).getHoverName();
            graphics.renderTooltip(font, java.util.List.of(Component.literal("Tank " + (tank + 1)), content,
                    Component.literal(amount + " / 1000 mB")), java.util.Optional.empty(), mouseX, mouseY);
            return;
        }

        renderTooltip(graphics, mouseX, mouseY);
    }

    private int hoveredTank(int mouseX, int mouseY) {
        int x = mouseX - leftPos;
        int y = mouseY - topPos;
        if (x >= 10 && x < 30 && y >= 12 && y < 57) return 0;
        if (x >= 32 && x < 52 && y >= 12 && y < 57) return 1;
        return -1;
    }
}
