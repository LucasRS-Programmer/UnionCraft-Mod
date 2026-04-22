package com.unioncraftmod.event;

import com.mojang.brigadier.CommandDispatcher;
import com.unioncraftmod.command.CheckModOresCommand;
import com.unioncraftmod.item.ModItems;
import com.unioncraftmod.item.custom.FuelBlockItem;
import com.unioncraftmod.item.custom.FuelItem;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = "unioncraftmod")
public class ModEvents {

    private static final Map<UUID, Boolean> rubyActive = new HashMap<>();

    private static boolean isWearingFullSet(Player player, Item helmet, Item chest, Item legs, Item boots) {
        return player.getInventory().getArmor(3).is(helmet) &&
                player.getInventory().getArmor(2).is(chest) &&
                player.getInventory().getArmor(1).is(legs) &&
                player.getInventory().getArmor(0).is(boots);
    }

    // =====================
    // COMBUSTÍVEL
    // =====================
    @SubscribeEvent
    public static void onFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        var stack = event.getItemStack();

        if (stack.getItem() instanceof FuelBlockItem fuelBlock) {
            event.setBurnTime(fuelBlock.getBurnTime());
        } else if (stack.getItem() instanceof FuelItem fuelItem) {
            event.setBurnTime(fuelItem.getBurnTime(stack, null));
        }
    }

    // =====================
    // COMBATE RUBY
    // =====================
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) return;

        if (!isWearingFullSet(player,
                ModItems.RUBY_HELMET.get(),
                ModItems.RUBY_CHESTPLATE.get(),
                ModItems.RUBY_LEGGINGS.get(),
                ModItems.RUBY_BOOTS.get())) return;

        ItemStack weapon = player.getMainHandItem();
        if (!weapon.is(ModItems.RUBY_SWORD.get())) return;

        player.addEffect(new MobEffectInstance(
                MobEffects.DAMAGE_BOOST, 20, 0, false, false, true
        ));

        event.setAmount(event.getAmount() * 1.15F);

        // CRÍTICO
        if (player.level().random.nextFloat() < 0.25F) {
            event.setAmount(event.getAmount() * 1.4F);

            player.level().playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.PLAYER_ATTACK_CRIT,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );
        }

        // WITHER
        if (player.level().random.nextFloat() < 0.20F) {
            event.getEntity().addEffect(
                    new MobEffectInstance(MobEffects.WITHER, 80, 0)
            );

            ((ServerLevel) player.level()).sendParticles(
                    ParticleTypes.SOUL_FIRE_FLAME,
                    event.getEntity().getX(),
                    event.getEntity().getY() + 1,
                    event.getEntity().getZ(),
                    10,
                    0.3, 0.3, 0.3,
                    0.1
            );
        }
    }

    // =====================
    // TICK (BUFFS PASSIVOS)
    // =====================
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        if (event.phase != TickEvent.Phase.END) return;
        if (player.level().isClientSide) return;
        if (player.tickCount % 20 != 0) return;

        // =====================
        // RUBY
        // =====================
        boolean isWearingRuby = isWearingFullSet(player,
                ModItems.RUBY_HELMET.get(),
                ModItems.RUBY_CHESTPLATE.get(),
                ModItems.RUBY_LEGGINGS.get(),
                ModItems.RUBY_BOOTS.get());

        boolean wasActive = rubyActive.getOrDefault(player.getUUID(), false);

        // SPEED I (sem sobrescrever efeito melhor)
        MobEffectInstance current = player.getEffect(MobEffects.MOVEMENT_SPEED);
        if (isWearingRuby && (current == null || current.getAmplifier() < 0)) {
            player.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SPEED,
                    40,
                    0,
                    false,
                    false,
                    true
            ));
        }

        // SOM AO EQUIPAR
        if (isWearingRuby && !wasActive) {
            player.level().playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.BEACON_ACTIVATE,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );
        }

        // SOM AO REMOVER (opcional, mas recomendado)
        if (!isWearingRuby && wasActive) {
            player.level().playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.BEACON_DEACTIVATE,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );
        }

        rubyActive.put(player.getUUID(), isWearingRuby);

        // =====================
        // ARMADURA DE SAFIRA
        // =====================
        if (isWearingFullSet(player,
                ModItems.SAPPHIRE_HELMET.get(),
                ModItems.SAPPHIRE_CHESTPLATE.get(),
                ModItems.SAPPHIRE_LEGGINGS.get(),
                ModItems.SAPPHIRE_BOOTS.get())) {

            player.addEffect(new MobEffectInstance(
                    MobEffects.DIG_SPEED, 40, 0, false, false, true
            ));

            player.addEffect(new MobEffectInstance(
                    MobEffects.LUCK, 40, 0, false, false, true
            ));
        }
    }

    // =====================
    // ARMADURA DE SAFIRA - MINERAÇÃO
    // =====================
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Level level = (Level) event.getLevel();

        if (level.isClientSide) return;

        Player player = event.getPlayer();

        if (player.isCreative() || player.isSpectator()) return;

        if (!isWearingFullSet(player,
                ModItems.SAPPHIRE_HELMET.get(),
                ModItems.SAPPHIRE_CHESTPLATE.get(),
                ModItems.SAPPHIRE_LEGGINGS.get(),
                ModItems.SAPPHIRE_BOOTS.get())) return;

        BlockPos pos = event.getPos();
        var state = level.getBlockState(pos);
        ItemStack tool = player.getMainHandItem();

        if (tool.isEmpty()) return;

        int silk = EnchantmentHelper.getItemEnchantmentLevel(
                Enchantments.SILK_TOUCH,
                tool
        );

        if (silk > 0) return;

        ItemStack fortuneTool = tool.copy();

        int currentFortune = EnchantmentHelper.getItemEnchantmentLevel(
                Enchantments.BLOCK_FORTUNE,
                tool
        );

        fortuneTool.enchant(Enchantments.BLOCK_FORTUNE, currentFortune + 2);

        var blockEntity = level.getBlockEntity(pos);

        var drops = Block.getDrops(
                state,
                (ServerLevel) level,
                pos,
                blockEntity,
                player,
                fortuneTool
        );

        if (drops.size() == 1 && drops.get(0).isEmpty()) return;

        event.setCanceled(true);

        for (ItemStack drop : drops) {
            Block.popResource(level, pos, drop);
        }

        level.destroyBlock(pos, false, player);
    }

    //comando debug para checar se minérios estão spawnando no mundo!
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        CheckModOresCommand.register(dispatcher);
    }
}
