package com.unioncraftmod.item.custom;

import com.unioncraftmod.util.ModFuelValues;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FuelItem extends Item {

    private final int burnTime;
    private final boolean infinite;

    public FuelItem(Properties properties, int burnTime, boolean infinite) {
        super(properties);
        this.burnTime = burnTime;
        this.infinite = infinite;
    }

    // Mantém item após crafting (somente infinito)
    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return infinite ? stack.copy() : ItemStack.EMPTY;
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return infinite;
    }

    // Define tempo de queima
    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return burnTime;
    }

    // Torna invulnerável se for infinito
    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (infinite) {
            entity.setInvulnerable(true);
        }
        return super.onEntityItemUpdate(stack, entity);
    }

    // Tooltip completo
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {

        super.appendHoverText(stack, level, tooltip, flag);

        String itemName = stack.getItem().getDescriptionId();

        // Combustível infinito
        if (infinite) {
            tooltip.add(Component.translatable("tooltip.unioncraftmod.infinite_fuel")
                    .withStyle(ChatFormatting.LIGHT_PURPLE));
        } else {
            // 🔥 Equivalência com carvão
            int coalEquivalent = burnTime / ModFuelValues.COAL;

            if (coalEquivalent > 0) {
                tooltip.add(Component.translatable("tooltip.unioncraftmod.coal_equivalent", coalEquivalent)
                        .withStyle(ChatFormatting.AQUA));
            }
        }

        // 💬 Sistema de lore com SHIFT
        if (Screen.hasShiftDown()) {

            for (int i = 1; i <= 10; i++) {
                String key = itemName + ".lore" + i;
                Component line = Component.translatable(key);

                // Para quando não existir mais tradução
                if (line.getString().equals(key)) break;

                tooltip.add(line);
            }

        } else {
            String testKey = itemName + ".lore1";

            if (!Component.translatable(testKey).getString().equals(testKey)) {
                tooltip.add(Component.translatable("tooltip.unioncraftmod.shift")
                        .withStyle(ChatFormatting.GRAY));
            }
        }
    }
}