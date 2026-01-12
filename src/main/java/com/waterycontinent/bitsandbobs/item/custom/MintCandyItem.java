package com.waterycontinent.bitsandbobs.item.custom;

import com.waterycontinent.bitsandbobs.item.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

public class MintCandyItem extends Item {
    public MintCandyItem(Item.Properties properties) {
        super(properties);

    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        ItemStack itemstack = super.finishUsingItem(stack, level, entityLiving);
        if (!level.isClientSide) {
            if (entityLiving instanceof Player) {
                Player player = (Player) entityLiving;
                player.resetCurrentImpulseContext();
                player.getCooldowns().addCooldown(this, 200);
                if (entityLiving instanceof Player _plrCldCheck0 && _plrCldCheck0.getCooldowns().isOnCooldown(ModItems.MINT_CANDY.asItem())) {
                    //player.sendSystemMessage(Component.literal("You ate Candy"));
                }
                }
            }

            return itemstack;
        }
    }

