package com.unioncraftmod.recipe;

import net.minecraft.world.item.crafting.Ingredient;

public class CountedIngredient {

    private final Ingredient ingredient;
    private final int count;

    public CountedIngredient(Ingredient ingredient, int count) {
        this.ingredient = ingredient;
        this.count = count;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public int getCount() {
        return count;
    }
}