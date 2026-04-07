package com.unioncraftmod.item.material;

import com.unioncraftmod.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {

    RUBY(
            new EnumMap<>(ArmorItem.Type.class) {{
                put(ArmorItem.Type.BOOTS, 3);
                put(ArmorItem.Type.LEGGINGS, 6);
                put(ArmorItem.Type.CHESTPLATE, 8);
                put(ArmorItem.Type.HELMET, 3);
            }},
            18,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            () -> Ingredient.of(ModItems.RUBY.get()),
            2.5F,
            0.1F
    );

    private final EnumMap<ArmorItem.Type, Integer> defense;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;
    private final float toughness;
    private final float knockbackResistance;
    private final net.minecraft.sounds.SoundEvent equipSound;

    ModArmorMaterials(EnumMap<ArmorItem.Type, Integer> defense,
                      int enchantmentValue,
                      net.minecraft.sounds.SoundEvent equipSound,
                      Supplier<Ingredient> repairIngredient,
                      float toughness,
                      float knockbackResistance) {
        this.defense = defense;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.repairIngredient = repairIngredient;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        int[] baseDurability = {13, 15, 16, 11}; // boots, leggings, chestplate, helmet
        return baseDurability[type.ordinal()] * 25; // igual diamante
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return defense.get(type);
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public net.minecraft.sounds.SoundEvent getEquipSound() {
        return equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }

    @Override
    public String getName() {
        return "unioncraft:ruby";
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }
}