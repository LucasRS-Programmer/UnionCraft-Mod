package com.unioncraftmod.util;
import com.unioncraftmod.UnionCraftMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Blocks {

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(UnionCraftMod.MOD_ID, name));
        }

        public static final TagKey<Block> MOD_ORE =
                BlockTags.create(ResourceLocation.fromNamespaceAndPath("unioncraftmod", "mod_ore"));

    }

    public static class Items {

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(UnionCraftMod.MOD_ID, name));
        }

        public static final TagKey<Item> ROSE_FLOWERS =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath("unioncraftmod", "rose_flowers"));

        public static final TagKey<Item> TORCHFLOWERS =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath("unioncraftmod", "torchflowers"));

        public static final TagKey<Item> YELLOW_FLOWERS =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath("unioncraftmod", "yellow_flowers"));

    }

}