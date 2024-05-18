package net.iirc.particletest.block;

import net.iirc.particletest.ParticleTest;
import net.iirc.particletest.item.ModItems;
import net.iirc.particletest.sounds.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.Material;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.GlowstoneFeature;
import net.minecraftforge.client.model.obj.ObjMaterialLibrary;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ParticleTest.MODID);

    public static final RegistryObject<Block> TILED_GROUND = registerBlock("tiled_ground",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> DARK_TILED_GROUND = registerBlock("dark_tiled_ground",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> CRACKED_TILED_GROUND = registerBlock("cracked_tiled_ground",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> RED_BRICK_WALLPAPER = registerBlock("red_brick_wallpaper",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> RED_DIRTY_BRICK_WALLPAPER = registerBlock("red_dirty_brick_wallpaper",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> CRACKED_STAIR_TILED_GROUND = registerBlock("cracked_stair_tiled_ground",
            () -> new StairBlock(() -> ModBlocks.CRACKED_TILED_GROUND.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE_STAIRS)));

    public static final RegistryObject<Block> CRACKED_SLAB_TILED_GROUND = registerBlock("cracked_slab_tiled_ground",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    private static final ToIntFunction<BlockState> lightLevelFunction = (blockstate) -> 90;

    private static final ToIntFunction<BlockState> lightLevelFunction2 = (blockstate) -> 90;




    public static final RegistryObject<Block> EYE_LAMP = registerBlock("eye_lamp",
            () -> new RedstoneLampBlock(BlockBehaviour.Properties.of().lightLevel(lightLevelFunction)));

    public static final RegistryObject<Block> EYE_LAMP_CORRUPTED = registerBlock("eye_lamp_corrupted",
            () -> new RedstoneLampBlock(BlockBehaviour.Properties.of().lightLevel(lightLevelFunction2)));





    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
