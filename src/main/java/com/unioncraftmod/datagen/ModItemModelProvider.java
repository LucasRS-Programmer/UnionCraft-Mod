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
        simpleItem(ModItems.RUBY);
        simpleItem(ModItems.SAPPHIRE);

        simpleItem(ModItems.GUAVA);

        simpleItem(ModItems.BIG_BANG);

        simpleItem(ModItems.ROSE_SWORD);
        simpleItem(ModItems.SUNFLOWER_SWORD);
        simpleItem(ModItems.TORCHFLOWER_SWORD);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
         return withExistingParent(item.getId().getPath(),
                 ResourceLocation.parse("item/generated")).texture("layer0",
                 ResourceLocation.fromNamespaceAndPath(UnionCraftMod.MOD_ID, "item/" + item.getId().getPath()));
    }

}
