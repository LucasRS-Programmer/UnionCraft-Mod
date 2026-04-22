package com.unioncraftmod.item.custom;

import com.unioncraftmod.util.ModFuelValues;
import net.minecraft.ChatFormatting;
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

public class FuelItem extends Item {

    private final int burnTime;
    private final boolean infinite;

    public FuelItem(Properties properties, int burnTime, boolean infinite) {
        super(properties);
        this.burnTime = burnTime;
        this.infinite = infinite;
    }

    // 🔁 Só mantém item se for infinito
    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return infinite ? stack.copy() : ItemStack.EMPTY;
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return infinite;
    }

    // 🔥 Combustível
    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return burnTime;
    }

    // 🛡️ Invulnerável (opcional só pra infinito)
    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (infinite) {
            entity.setInvulnerable(true);
        }
        return super.onEntityItemUpdate(stack, entity);
    }

    // 💬 Tooltip inteligente
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {

        int coalEquivalent = burnTime / ModFuelValues.COAL;

        if (infinite) {
            tooltip.add(Component.translatable("tooltip.unioncraftmod.infinite_fuel")
                    .withStyle(ChatFormatting.GOLD));

        }

        else {
            tooltip.add(Component.translatable("tooltip.unioncraftmod.coal_equivalent", coalEquivalent)
                    .withStyle(ChatFormatting.AQUA));
        }

        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.unioncraftmod.big_bang.lore1"));
            tooltip.add(Component.translatable("tooltip.unioncraftmod.big_bang.lore2"));
        } else {
            tooltip.add(Component.translatable("tooltip.unioncraftmod.big_bang.shift"));
        }
    }
}