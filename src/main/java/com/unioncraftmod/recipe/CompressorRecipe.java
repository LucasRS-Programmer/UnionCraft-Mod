package com.unioncraftmod.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class CompressorRecipe implements Recipe<Container> {

    private final ResourceLocation id;

    // 🔹 AGORA USA CountedIngredient
    private final NonNullList<CountedIngredient> ingredients;

    private final ItemStack output;

    // 🔹 COUNT DO RESULTADO (OPCIONAL)
    private final int count;

    public CompressorRecipe(ResourceLocation id,
                            NonNullList<CountedIngredient> ingredients,
                            ItemStack output,
                            int count) {
        this.id = id;
        this.ingredients = ingredients;
        this.output = output;
        this.count = count;
    }

    // 🔴 MATCHES (AGORA COM QUANTIDADE)
    @Override
    public boolean matches(Container container, Level level) {

        for (CountedIngredient counted : ingredients) {

            int needed = counted.getCount();
            int found = 0;

            for (int i = 0; i < container.getContainerSize(); i++) {
                var stack = container.getItem(i);

                if (counted.getIngredient().test(stack)) {
                    found += stack.getCount();
                }
            }

            if (found < needed) return false;
        }

        return true;
    }

    // 🔴 RESULTADO
    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output;
    }

    // 🔴 GETTERS
    public NonNullList<CountedIngredient> getCountedIngredients() {
        return ingredients;
    }

    public int getCount() {
        return count;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.COMPRESSOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.COMPRESSOR_TYPE.get();
    }


}