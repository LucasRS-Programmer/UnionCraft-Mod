package com.unioncraftmod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public class CompressorRecipeSerializer implements RecipeSerializer<CompressorRecipe> {

    @Override
    public CompressorRecipe fromJson(ResourceLocation id, JsonObject json) {

        // 🔹 LER LISTA DE INGREDIENTES (COM COUNT)
        NonNullList<CountedIngredient> ingredients = NonNullList.create();
        JsonArray jsonIngredients = json.getAsJsonArray("ingredients");

        for (int i = 0; i < jsonIngredients.size(); i++) {
            JsonObject obj = jsonIngredients.get(i).getAsJsonObject();

            // 🔹 SEPARA O ITEM DO COUNT
            Ingredient ingredient = Ingredient.fromJson(obj.get("ingredient"));

            // 🔹 COUNT
            int count = obj.has("count") ? obj.get("count").getAsInt() : 1;

            ingredients.add(new CountedIngredient(ingredient, count));
        }

        // 🔹 RESULTADO
        ItemStack result = ShapedRecipe.itemStackFromJson(json.getAsJsonObject("result"));

        // 🔹 COUNT DO RESULTADO (CASO USE)
        int count = json.has("count") ? json.get("count").getAsInt() : 1;

        return new CompressorRecipe(id, ingredients, result, count);
    }

    @Override
    public CompressorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {

        // 🔹 TAMANHO DA LISTA
        int size = buf.readInt();
        NonNullList<CountedIngredient> ingredients = NonNullList.create();

        for (int i = 0; i < size; i++) {

            // 🔹 INGREDIENTE
            Ingredient ingredient = Ingredient.fromNetwork(buf);

            // 🔹 COUNT
            int count = buf.readInt();

            ingredients.add(new CountedIngredient(ingredient, count));
        }

        // 🔹 RESULTADO
        ItemStack result = buf.readItem();

        // 🔹 COUNT DO RESULTADO
        int count = buf.readInt();

        return new CompressorRecipe(id, ingredients, result, count);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, CompressorRecipe recipe) {

        // 🔹 ESCREVE TAMANHO
        buf.writeInt(recipe.getCountedIngredients().size());

        // 🔹 ESCREVE INGREDIENTES + COUNT
        for (CountedIngredient counted : recipe.getCountedIngredients()) {
            counted.getIngredient().toNetwork(buf);
            buf.writeInt(counted.getCount());
        }

        // 🔹 RESULTADO
        buf.writeItem(recipe.getResultItem(null).copy());

        // 🔹 COUNT DO RESULTADO
        buf.writeInt(recipe.getCount());
    }
}