package net.iirc.particletest.sounds;

import net.iirc.particletest.ParticleTest;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ParticleTest.MODID);


    public static final RegistryObject<SoundEvent> AMBIENT_SCARY = registerSoundEvents("ambient_scary");
    public static final RegistryObject<SoundEvent> METAL_EXPLOSION = registerSoundEvents("metal_explosion");


    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ParticleTest.MODID, name)));
    }


    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
