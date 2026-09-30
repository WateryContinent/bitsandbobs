package com.waterycontinent.bitsandbobs.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public final class MintExtractEffects {
    private MintExtractEffects() {}

    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Player player) || !(player.level() instanceof ServerLevel level)) {
            return;
        }

        // Damage depends on body contact; blindness requires the eyes below the surface.
        if (player.getFluidTypeHeight(ModFluidTypes.MINT_EXTRACT_TYPE.get()) > 0 && player.tickCount % 10 == 0) {
            player.hurt(player.damageSources().lava(), 4.0F);
            player.setRemainingFireTicks(200);
        }
        BlockPos eyePos = BlockPos.containing(player.getX(), player.getEyeY(), player.getZ());
        var fluid = level.getFluidState(eyePos);
        if (fluid.getFluidType() == ModFluidTypes.MINT_EXTRACT_TYPE.get()
                && player.getEyeY() < eyePos.getY() + fluid.getHeight(level, eyePos)) {
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 0, false, false, true));
        }
    }
}
