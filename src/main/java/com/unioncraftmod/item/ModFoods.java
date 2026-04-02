package com.unioncraftmod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties GUAVA = new FoodProperties.Builder()
            .nutrition(4).saturationMod(0.4F)
            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 60, 0), 0.10F).build();
}
