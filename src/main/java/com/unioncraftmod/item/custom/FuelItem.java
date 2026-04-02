package com.unioncraftmod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import net.minecraft.client.gui.screens.Screen;
import java.util.List;
import net.minecraft.client.gui.screens.Screen;

import net.minecraft.world.item.Item.Properties;


public class FuelItem extends Item {

    private final int burnTime;

    public FuelItem(Properties pProperties, int burnTime) {
        super(pProperties);
        this.burnTime = burnTime;
    }

    //Impede do Item ser Consumido
    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return stack.copy();
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return burnTime;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        entity.setInvulnerable(true);
        return super.onEntityItemUpdate(stack, entity);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {

        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.unioncraftmod.big_bang.lore1"));
            tooltip.add(Component.translatable("tooltip.unioncraftmod.big_bang.lore2"));
            tooltip.add(Component.translatable("tooltip.unioncraftmod.big_bang.lore3"));
        } else {
            tooltip.add(Component.translatable("tooltip.unioncraftmod.big_bang.shift"));
        }

        super.appendHoverText(stack, level, tooltip, flag);
    }

}



