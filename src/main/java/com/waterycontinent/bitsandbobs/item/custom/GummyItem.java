package com.waterycontinent.bitsandbobs.item.custom;

import com.waterycontinent.bitsandbobs.item.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GummyItem extends Item {
    public GummyItem(Properties properties) {
        super(properties);

    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        ItemStack itemstack = super.finishUsingItem(stack, level, entityLiving);
        if (!level.isClientSide) {
            if (entityLiving instanceof Player) {
                Player player = (Player) entityLiving;
                //player.resetCurrentImpulseContext();
                //player.getCooldowns().addCooldown(this, 200);
                if (entityLiving instanceof Player _plrCldCheck0 && _plrCldCheck0.getCooldowns().isOnCooldown(ModItems.MINT_CANDY.asItem())) {
                    //player.sendSystemMessage(Component.literal("You ate Candy"));
                }
                }
            }

            return itemstack;
        }
    }

