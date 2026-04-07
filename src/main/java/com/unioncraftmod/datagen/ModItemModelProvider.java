package com.unioncraftmod.datagen;

import com.unioncraftmod.UnionCraftMod;
import com.unioncraftmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, UnionCraftMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        registerItems();
        registerTools();
        registerArmor();
    }
    private void registerItems() {
        simpleItem(ModItems.RUBY);
        simpleItem(ModItems.SAPPHIRE);
        simpleItem(ModItems.GUAVA);
        simpleItem(ModItems.BIG_BANG);
    }

    private void registerTools() {
        handheldItem(ModItems.ROSE_SWORD);
        handheldItem(ModItems.SUNFLOWER_SWORD);
        handheldItem(ModItems.TORCHFLOWER_SWORD);
        handheldItem(ModItems.RUBY_SWORD);

        handheldItem(ModItems.RUBY_PICKAXE);
        handheldItem(ModItems.RUBY_AXE);
        handheldItem(ModItems.RUBY_SHOVEL);
        handheldItem(ModItems.RUBY_HOE);
    }

    private void registerArmor() {
        armorItem(ModItems.RUBY_HELMET);
        armorItem(ModItems.RUBY_CHESTPLATE);
        armorItem(ModItems.RUBY_LEGGINGS);
        armorItem(ModItems.RUBY_BOOTS);
    }


    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
         return withExistingParent(item.getId().getPath(),
                 ResourceLocation.parse("item/generated")).texture("layer0",
                 ResourceLocation.fromNamespaceAndPath(UnionCraftMod.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(UnionCraftMod.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder armorItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(UnionCraftMod.MOD_ID, "item/" + item.getId().getPath()));
    }
}
