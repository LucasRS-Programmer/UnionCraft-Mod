package com.unioncraftmod.tier;

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


}