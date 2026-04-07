package com.unioncraftmod.tier;

import com.unioncraftmod.item.ModItems;
import com.unioncraftmod.util.ModTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModToolTiers {

    public static final Tier ROSE = new ForgeTier(
            0,
            80,
            2F,
            3F,
            22,
            BlockTags.NEEDS_STONE_TOOL,
            () -> Ingredient.of(ModTags.Items.ROSE_FLOWERS)
    );


    public static final Tier TORCHFLOWER = new ForgeTier(
            0,
            100,
            2F,
            3F,
            22,
            BlockTags.NEEDS_STONE_TOOL,
            () -> Ingredient.of(ModTags.Items.TORCHFLOWERS)
    );

    public static final Tier YELLOW_FLOWERS = new ForgeTier(
            0,
            80,
            2F,
            3F,
            22,
            BlockTags.NEEDS_STONE_TOOL,
            () -> Ingredient.of(ModTags.Items.YELLOW_FLOWERS)
    );

    public static final Tier RUBY_ITEMS = new ForgeTier(
            4,
            1250,
            8.5F,
            3.5F,
            18,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(ModItems.RUBY.get())
    );

    public static final Tier SAPPHIRE_ITEMS = new ForgeTier(
            4,
            1750,
            9F,
            3F,
            22,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(ModItems.RUBY.get())
    );


}