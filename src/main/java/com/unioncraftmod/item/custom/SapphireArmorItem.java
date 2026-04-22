package com.unioncraftmod.item.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class SapphireArmorItem extends ArmorItem {

    public SapphireArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return slot == EquipmentSlot.LEGS
                ? "unioncraftmod:textures/models/armor/sapphire_layer_2.png"
                : "unioncraftmod:textures/models/armor/sapphire_layer_1.png";
    }
}