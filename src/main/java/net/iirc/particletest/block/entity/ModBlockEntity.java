package net.iirc.particletest.block.entity;

import net.iirc.particletest.ParticleTest;
import net.iirc.particletest.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntity {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ParticleTest.MODID);




    public static final RegistryObject<BlockEntityType<GemPolisherBlockEntity>> GEN_POLISHER_BE =
            BLOCK_ENTITIES.register("gem_polisher_be", () ->
                    BlockEntityType.Builder.of(GemPolisherBlockEntity::new, ModBlocks.GEM_POLISHER.get()).build(null));




    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
