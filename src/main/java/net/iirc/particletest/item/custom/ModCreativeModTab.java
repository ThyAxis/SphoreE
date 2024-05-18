package net.iirc.particletest.item.custom;

import net.iirc.particletest.ParticleTest;
import net.iirc.particletest.block.ModBlocks;
import net.iirc.particletest.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB_DEFERRED_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ParticleTest.MODID);
    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TAB_DEFERRED_REGISTER.register("tutorial_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.RUBY_SCYTHE.get()))
                    .title(Component.translatable("creativetab.tutorial_tab"))
                    .displayItems(((itemDisplayParameters, output) -> {



                        // ITEMS
                        output.accept(ModItems.METH.get());
                        output.accept(ModItems.RED_PHOSPHORUS.get());
                        output.accept(ModItems.RUBY_SCYTHE.get());

                        // BLOCKS
                        output.accept(ModBlocks.EYE_LAMP.get());
                        output.accept(ModBlocks.TILED_GROUND.get());
                        output.accept(ModBlocks.CRACKED_TILED_GROUND.get());
                        output.accept(ModBlocks.DARK_TILED_GROUND.get());
                        output.accept(ModBlocks.CRACKED_SLAB_TILED_GROUND.get());
                        output.accept(ModBlocks.CRACKED_STAIR_TILED_GROUND.get());
                        output.accept(ModBlocks.RED_BRICK_WALLPAPER.get());
                        output.accept(ModBlocks.RED_DIRTY_BRICK_WALLPAPER.get());
                        output.accept(ModBlocks.GEM_POLISHER.get());

                    })).build());


    public static void register(IEventBus bus) {
        CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(bus);
    }
}
