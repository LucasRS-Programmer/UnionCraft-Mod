package com.unioncraftmod.datagen;

import com.google.gson.JsonArray;
import com.unioncraftmod.block.ModBlocks;
import com.unioncraftmod.item.ModItems;
import com.unioncraftmod.recipe.ModRecipes;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    public void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RUBY_BLOCK.get())
                .pattern("RRR")
                .pattern("RRR")
                .pattern("RRR")
                .define('R', ModItems.RUBY.get())
                .unlockedBy(getHasName(ModItems.RUBY.get()), has(ModItems.RUBY.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RUBY.get(), 9)
                .requires(ModBlocks.RUBY_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.RUBY_BLOCK.get()), has(ModBlocks.RUBY_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SAPPHIRE_BLOCK.get())
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.SAPPHIRE.get())
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 9)
                .requires(ModBlocks.SAPPHIRE_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.SAPPHIRE_BLOCK.get()), has(ModBlocks.SAPPHIRE_BLOCK.get()))
                .save(pWriter);

        //RECIPE CRAFTER COMPRESSOR - RECEITA DO CRAFT
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CRAFTER_COMPRESSOR.get())
                .pattern("IPI")
                .pattern("ICI")
                .pattern("IBI")
                .define('I', Items.IRON_INGOT)
                .define('P', Items.PISTON)
                .define('C', Items.CRAFTING_TABLE)
                .define('B', Items.IRON_BLOCK)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);

        // 1 BLOCO CARVÃO COMPRIMIDO = 9 CARVÃO COMPRIMIDO
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COMPRESSED_COAL.get(), 9)
                .requires(ModBlocks.COMPRESSED_COAL_BLOCK.get())
                .unlockedBy(getHasName(ModItems.CRAFTER_COMPRESSOR.get()), has(ModItems.CRAFTER_COMPRESSOR.get()))
                .save(pWriter);


        // COMPRESSOR RECIPES

        compressing(pWriter, "compressed_coal_block_to_diamond",
                ModBlocks.COMPRESSED_COAL_BLOCK.get(),
                1,
                net.minecraft.world.item.Items.DIAMOND,
                1);

        compressing(pWriter, "coal_block_to_compressed_coal_block",
                Items.COAL_BLOCK,
                9,
                ModBlocks.COMPRESSED_COAL_BLOCK.get(),
                1);


        // ARMORS - ARMADURAS

            //RUBY
        armorSet(pWriter,
                ModItems.RUBY.get(),
                ModItems.RUBY_HELMET.get(),
                ModItems.RUBY_CHESTPLATE.get(),
                ModItems.RUBY_LEGGINGS.get(),
                ModItems.RUBY_BOOTS.get(),
                "ruby");

        //SAPPHIRE
        armorSet(pWriter,
                ModItems.SAPPHIRE.get(),
                ModItems.SAPPHIRE_HELMET.get(),
                ModItems.SAPPHIRE_CHESTPLATE.get(),
                ModItems.SAPPHIRE_LEGGINGS.get(),
                ModItems.SAPPHIRE_BOOTS.get(),
                "sapphire");


        // SWORDS AND TOOLS - ESPADAS E FERRAMENTAS

        //RUBY - RUBI
        toolSet(pWriter,
                ModItems.RUBY.get(),
                ModItems.RUBY_PICKAXE.get(),
                ModItems.RUBY_AXE.get(),
                ModItems.RUBY_SHOVEL.get(),
                ModItems.RUBY_SWORD.get(),
                ModItems.RUBY_HOE.get(),
                "ruby");

        //SAPPHIRE - SÁFIRA
        toolSet(pWriter,
                ModItems.SAPPHIRE.get(),
                ModItems.SAPPHIRE_PICKAXE.get(),
                ModItems.SAPPHIRE_AXE.get(),
                ModItems.SAPPHIRE_SHOVEL.get(),
                ModItems.SAPPHIRE_SWORD.get(),
                ModItems.SAPPHIRE_HOE.get(),
                "sapphire");

    }


    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }

    private static String getItemId(ItemLike item) {
        ResourceLocation key = net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(item.asItem());

        if (key == null) {
            throw new IllegalStateException("Item não registrado: " + item);
        }

        return key.toString();
    }

    protected static void compressing(Consumer<FinishedRecipe> consumer,
                                      String name,
                                      ItemLike input,
                                      int inputCount,
                                      ItemLike output,
                                      int outputCount) {

        consumer.accept(new FinishedRecipe() {

            @Override
            public void serializeRecipeData(JsonObject json) {
                json.addProperty("type", "unioncraftmod:compressing");

                JsonObject ingredient = new JsonObject();
                ingredient.addProperty("item", getItemId(input));

                JsonArray ingredients = new JsonArray();
                JsonObject ingredientWrapper = new JsonObject();
                ingredientWrapper.add("ingredient", ingredient);
                ingredientWrapper.addProperty("count", inputCount);

                ingredients.add(ingredientWrapper);

                json.add("ingredients", ingredients);

                JsonObject result = new JsonObject();
                result.addProperty("item", getItemId(output));

                json.add("result", result);

                json.addProperty("count", outputCount);
            }

            @Override
            public ResourceLocation getId() {
                return ResourceLocation.fromNamespaceAndPath("unioncraftmod", name);
            }

            @Override
            public RecipeSerializer<?> getType() {
                return ModRecipes.COMPRESSOR_SERIALIZER.get();
            }

            @Override
            public JsonObject serializeAdvancement() {
                return null;
            }

            @Override
            public ResourceLocation getAdvancementId() {
                return null;
            }
        });
    }

    //METÓDO PARA CRIAR RECEITAS DE ARMADURAS
    protected static void armorSet(Consumer<FinishedRecipe> writer,
                                   ItemLike material,
                                   ItemLike helmet,
                                   ItemLike chestplate,
                                   ItemLike leggings,
                                   ItemLike boots,
                                   String namePrefix) {

        // HELMET - CAPACETE
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, helmet)
                .pattern("MMM")
                .pattern("M M")
                .define('M', material)
                .unlockedBy(getHasName(material), has(material))
                .save(writer, namePrefix + "_helmet");

        // CHESTPLATE - PEITORAL
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, chestplate)
                .pattern("M M")
                .pattern("MMM")
                .pattern("MMM")
                .define('M', material)
                .unlockedBy(getHasName(material), has(material))
                .save(writer, namePrefix + "_chestplate");

        // LEGGINGS - CALÇAS
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, leggings)
                .pattern("MMM")
                .pattern("M M")
                .pattern("M M")
                .define('M', material)
                .unlockedBy(getHasName(material), has(material))
                .save(writer, namePrefix + "_leggings");

        // BOOTS - BOTAS
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, boots)
                .pattern("M M")
                .pattern("M M")
                .define('M', material)
                .unlockedBy(getHasName(material), has(material))
                .save(writer, namePrefix + "_boots");
    }

    protected static void customArmorPiece(Consumer<FinishedRecipe> writer,
                                           ItemLike result,
                                           ItemLike material,
                                           String[] pattern,
                                           String name) {

        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result);

        for (String line : pattern) {
            builder.pattern(line);
        }

        builder.define('M', material)
                .unlockedBy(getHasName(material), has(material))
                .save(writer, name);
    }

    //METÓDO PARA CRIAR RECEITAS DE ARMAS E FERRAMENTAS
    protected static void toolSet(Consumer<FinishedRecipe> writer,
                                  ItemLike material,
                                  ItemLike pickaxe,
                                  ItemLike axe,
                                  ItemLike shovel,
                                  ItemLike sword,
                                  ItemLike hoe,
                                  String namePrefix) {
        // SWORD - ESPADA
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sword)
                .pattern("M")
                .pattern("M")
                .pattern("S")
                .define('M', material)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(material), has(material))
                .save(writer, namePrefix + "_sword");

        // PICKAXE - PICARETA
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxe)
                .pattern("MMM")
                .pattern(" S ")
                .pattern(" S ")
                .define('M', material)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(material), has(material))
                .save(writer, namePrefix + "_pickaxe");

        // AXE - MACHADO
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe)
                .pattern("MM ")
                .pattern("MS ")
                .pattern(" S ")
                .define('M', material)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(material), has(material))
                .save(writer, namePrefix + "_axe");

        // AXE (esquerda - espelhado)
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe)
                .pattern(" MM")
                .pattern(" SM")
                .pattern(" S ")
                .define('M', material)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(material), has(material))
                .save(writer, namePrefix + "_axe_mirrored");

        // SHOVEL
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel)
                .pattern(" M ")
                .pattern(" S ")
                .pattern(" S ")
                .define('M', material)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(material), has(material))
                .save(writer, namePrefix + "_shovel");

        // HOE
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoe)
                .pattern("MM ")
                .pattern(" S ")
                .pattern(" S ")
                .define('M', material)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(material), has(material))
                .save(writer, namePrefix + "_hoe");
    }

}
