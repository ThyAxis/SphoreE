package net.iirc.particletest;

import com.mojang.logging.LogUtils;
import net.iirc.particletest.block.ModBlocks;
import net.iirc.particletest.datagen.ModWorldGenProvider;
import net.iirc.particletest.gui.OrbFogUI;
import net.iirc.particletest.item.ModItems;
import net.iirc.particletest.item.custom.ModCreativeModTab;
import net.iirc.particletest.screen.GemPolishingStationScreen;
import net.iirc.particletest.screen.ModMenuTypes;
import net.iirc.particletest.sounds.ModSounds;
import net.iirc.particletest.worldgen.biome.ModBiomes;
import net.iirc.particletest.worldgen.dimension.ModDimensions;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ParticleTest.MODID)
public class ParticleTest
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "particletest";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public ParticleTest()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeModTab.register(modEventBus);
        ModSounds.register(modEventBus);
        ModBlocks.register(modEventBus);



        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(ModMenuTypes.GEM_POLISHING_MENU.get(), GemPolishingStationScreen::new);
        }

//        @SubscribeEvent
//        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
//            event.registerAboveAll("orb_fog", OrbFogUI.OVERLAY);
//        }
//    }
}
}
