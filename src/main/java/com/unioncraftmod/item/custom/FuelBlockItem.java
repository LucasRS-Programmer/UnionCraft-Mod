package com.unioncraftmod.item.custom;

import com.unioncraftmod.util.ModFuelValues;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.List;

public class FuelBlockItem extends BlockItem {

    private final int burnTime;

    public FuelBlockItem(Block block, Properties properties, int burnTime) {
        super(block, properties);
        this.burnTime = burnTime;
    }

    public int getBurnTime() {
        return burnTime;
    }

    @Override
    public int getBurnTime(ItemStack stack, @Nullable net.minecraft.world.item.crafting.RecipeType<?> type) {
        return burnTime;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {

        int coalEquivalent = burnTime / ModFuelValues.COAL;

        tooltip.add(Component.translatable("tooltip.unioncraftmod.coal_equivalent", coalEquivalent)
                .withStyle(ChatFormatting.AQUA));
    }
}