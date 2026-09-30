package com.waterycontinent.bitsandbobs.sound;

import com.waterycontinent.bitsandbobs.BitsandBobs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, BitsandBobs.MODID);

    public static final ResourceKey<JukeboxSong> createSong(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(BitsandBobs.MODID, name));
    }

    public static final Supplier<SoundEvent> THE_END = registerSoundEvent("the_end");
    public static final ResourceKey<JukeboxSong> THE_END_KEY = createSong("the_end");
    public static final Supplier<SoundEvent> ARIA_MATH = registerSoundEvent("aria_math");
    public static final ResourceKey<JukeboxSong> ARIA_MATH_KEY = createSong("aria_math");

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(BitsandBobs.MODID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
