package com.waterycontinent.bitsandbobs.item.custom;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import net.minecraft.SharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;

import static net.minecraft.world.item.Item.Properties.validateComponents;

public class CrushedMintItem extends Item {
    public CrushedMintItem(Item.Properties properties) {
        super(properties);

    }



    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        ItemStack itemstack = super.finishUsingItem(stack, level, entityLiving);
        if (!level.isClientSide) {
            if (entityLiving instanceof Player) {
                Player player = (Player)entityLiving;
                player.resetCurrentImpulseContext();
                player.getCooldowns().addCooldown(this, 200);

            }
        }

        return itemstack;
    }
}
