package net.iirc.particletest.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFood {
    public static final FoodProperties METH = new FoodProperties.Builder().nutrition(10)
            .saturationMod(0.6f).effect(() -> new MobEffectInstance(MobEffects.HUNGER,20), 0.1f).meat().build();


    public static final FoodProperties RED_PHOSPHORUS = new FoodProperties.Builder().nutrition(10)
            .saturationMod(0.6f).effect(() -> new MobEffectInstance(MobEffects.HARM,200, 10), 0.1f).meat().build();
}
