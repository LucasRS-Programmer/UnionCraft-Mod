package com.unioncraftmod.item.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class FlowerSwordItem extends SwordItem {

    private final MobEffect effect;
    private final int duration;
    private final int amplifier;

    private final Enchantment enchantment;
    private final int enchantLevel;

    private final boolean daySpeed;
    private final boolean fireAspect;

    public FlowerSwordItem(Tier tier, int attackDamage, float attackSpeed,
                           Properties props,
                           MobEffect effect, int duration, int amplifier,
                           Enchantment enchantment, int enchantLevel,
                           boolean daySpeed, boolean fireAspect) {

        super(tier, attackDamage, attackSpeed, props);

        this.effect = effect;
        this.duration = duration;
        this.amplifier = amplifier;

        this.enchantment = enchantment;
        this.enchantLevel = enchantLevel;

        this.daySpeed = daySpeed;
        this.fireAspect = fireAspect;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        if (!attacker.level().isClientSide && effect != null) {
            attacker.addEffect(new MobEffectInstance(effect, duration, amplifier));
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {

        // 🔥 Torchflower Sword sempre com Fire Aspect II
        if(fireAspect && !stack.isEnchanted()) {
            stack.enchant(Enchantments.FIRE_ASPECT, 2);
        }

        // 🌻 Sunflower Sword dá Speed durante o dia ao segurar
        if(daySpeed && !level.isClientSide && isSelected && entity instanceof Player player) {

            if(level.isDay()) {

                player.addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SPEED,
                        40,
                        1,
                        true,
                        false
                ));

            }

        }

        super.inventoryTick(stack, level, entity, slot, isSelected);
    }
}