package com.unioncraftmod.recipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "unioncraftmod");

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, "unioncraftmod");

    public static final RegistryObject<RecipeSerializer<CompressorRecipe>> COMPRESSOR_SERIALIZER =
            SERIALIZERS.register("compressing", CompressorRecipeSerializer::new);

    public static final RegistryObject<RecipeType<CompressorRecipe>> COMPRESSOR_TYPE =
            TYPES.register("compressing", () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "unioncraftmod:compressing";
                }
            });
}