package net.iirc.particletest.item;

import net.iirc.particletest.ParticleTest;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ParticleTest.MODID);


    public static final RegistryObject<Item> RUBY_SCYTHE = ITEMS.register("ruby_scythe",
            () -> new PickaxeItem(Tiers.NETHERITE, 15, 2f,
                    new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> METH = ITEMS.register("meth",
            () -> new Item(new Item.Properties().food(ModFood.METH)));

    public static final RegistryObject<Item> RED_PHOSPHORUS = ITEMS.register("red_phosphorus",
            () -> new Item(new Item.Properties().food(ModFood.RED_PHOSPHORUS)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}