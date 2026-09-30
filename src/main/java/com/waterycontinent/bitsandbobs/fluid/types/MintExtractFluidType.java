package com.waterycontinent.bitsandbobs.fluid.types;

import com.waterycontinent.bitsandbobs.BitsandBobs;
import com.waterycontinent.bitsandbobs.fluid.ModFluidTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;


@EventBusSubscriber(modid = BitsandBobs.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class MintExtractFluidType extends FluidType {
	public MintExtractFluidType() {
		super(Properties.create().fallDistanceModifier(0F).canExtinguish(false).canSwim(false).canDrown(false).supportsBoating(true).canHydrate(false).motionScale(0.007D).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
				.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH));
	}

	@SubscribeEvent
	public static void registerFluidTypeExtensions(RegisterClientExtensionsEvent event) {
		event.registerFluidType(new IClientFluidTypeExtensions() {
			private static final ResourceLocation STILL_TEXTURE = ResourceLocation.parse("bitsandbobs:block/mint_still");
			private static final ResourceLocation FLOWING_TEXTURE = ResourceLocation.parse("bitsandbobs:block/mint_flow");

			@Override
			public ResourceLocation getStillTexture() {
				return STILL_TEXTURE;
			}

			@Override
			public ResourceLocation getFlowingTexture() {
				return FLOWING_TEXTURE;
			}

			@Override
			public ResourceLocation getRenderOverlayTexture(Minecraft minecraft) {
				return ResourceLocation.parse("bitsandbobs:textures/fluid/mint_still.png");
			}
		}, ModFluidTypes.MINT_EXTRACT_TYPE.get());
	}
}
