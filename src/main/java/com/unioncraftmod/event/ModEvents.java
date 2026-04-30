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
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.CropBlock;
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
    private static final Map<UUID, Boolean> sapphireActive = new HashMap<>();

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

        // BONUS LEVE
        event.setAmount(event.getAmount() * 1.05F);

        float roll = player.level().random.nextFloat();

        // CRÍTICO FORTE
        if (roll < 0.05F) {
            event.setAmount(event.getAmount() * 1.6F);

            event.getEntity().addEffect(
                    new MobEffectInstance(MobEffects.WITHER, 60, 0)
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

            player.level().playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.PLAYER_ATTACK_CRIT,
                    SoundSource.PLAYERS,
                    1.2F,
                    0.8F
            );

            // CRÍTICO NORMAL
        } else if (roll < 0.20F) {
            event.setAmount(event.getAmount() * 1.3F);

            player.level().playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.PLAYER_ATTACK_CRIT,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );
        }
    }

    // =====================
    // TICK (BUFFS PASSIVOS + SOM)
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

        // SPEED I
        if (isWearingRuby) {
            MobEffectInstance current = player.getEffect(MobEffects.MOVEMENT_SPEED);

            if (current == null || current.getDuration() < 10) {
                player.addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SPEED,
                        40,
                        0,
                        false,
                        false,
                        true
                ));
            }
        }

        // 🔊 ATIVAÇÃO
        if (isWearingRuby && !wasActive) {
            player.level().playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.BEACON_ACTIVATE,
                    SoundSource.PLAYERS,
                    1.1F,
                    1.1F
            );
        }

        // 🔊 DESATIVAÇÃO
        if (!isWearingRuby && wasActive) {
            player.level().playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.BEACON_DEACTIVATE,
                    SoundSource.PLAYERS,
                    1.1F,
                    1.1F
            );
        }

        // Atualiza estado
        if (isWearingRuby != wasActive) {
            rubyActive.put(player.getUUID(), isWearingRuby);
        }

        // =====================
// SAFIRA
// =====================
        boolean isWearingSapphire = isWearingFullSet(player,
                ModItems.SAPPHIRE_HELMET.get(),
                ModItems.SAPPHIRE_CHESTPLATE.get(),
                ModItems.SAPPHIRE_LEGGINGS.get(),
                ModItems.SAPPHIRE_BOOTS.get());

        boolean wasSapphireActive = sapphireActive.getOrDefault(player.getUUID(), false);

// Buffs
        if (isWearingSapphire) {

            // HASTE (Mineração)
            MobEffectInstance haste = player.getEffect(MobEffects.DIG_SPEED);
            if (haste == null || haste.getAmplifier() < 0 || haste.getDuration() < 10) {
                player.addEffect(new MobEffectInstance(
                        MobEffects.DIG_SPEED,
                        40,
                        0,
                        false,
                        false,
                        true
                ));
            }

            // LUCK (Loot)
            MobEffectInstance luck = player.getEffect(MobEffects.LUCK);
            if (luck == null || luck.getDuration() < 10) {
                player.addEffect(new MobEffectInstance(
                        MobEffects.LUCK,
                        40,
                        0,
                        false,
                        false,
                        true
                ));
            }
        }

// 🔊 ATIVAÇÃO
        if (isWearingSapphire && !wasSapphireActive) {
            player.level().playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.BEACON_ACTIVATE,
                    SoundSource.PLAYERS,
                    0.9F,
                    1.1F
            );
        }

// 🔊 DESATIVAÇÃO
        if (!isWearingSapphire && wasSapphireActive) {
            player.level().playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.BEACON_DEACTIVATE,
                    SoundSource.PLAYERS,
                    0.9F,
                    1.1F
            );
        }

// Atualiza estado
        if (isWearingSapphire != wasSapphireActive) {
            sapphireActive.put(player.getUUID(), isWearingSapphire);
        }
    }

    // =====================
    // SAFIRA - MINERAÇÃO
    // =====================
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Level level = (Level) event.getLevel();

        if (!(level instanceof ServerLevel serverLevel)) return;

        Player player = event.getPlayer();

        if (player.isCreative() || player.isSpectator()) return;

        if (!isWearingFullSet(player,
                ModItems.SAPPHIRE_HELMET.get(),
                ModItems.SAPPHIRE_CHESTPLATE.get(),
                ModItems.SAPPHIRE_LEGGINGS.get(),
                ModItems.SAPPHIRE_BOOTS.get())) return;

        ItemStack tool = player.getMainHandItem();
        if (tool.isEmpty()) return;

        BlockPos pos = event.getPos();
        var state = level.getBlockState(pos);

        boolean isOre = state.getBlock().getDescriptionId().contains("ore");
        boolean isCrop = state.getBlock() instanceof CropBlock;

        if (!isOre && !isCrop) return;

        if (serverLevel.random.nextFloat() > 0.15F) return;

        var drops = Block.getDrops(
                state,
                serverLevel,
                pos,
                level.getBlockEntity(pos),
                player,
                tool
        );

        for (ItemStack drop : drops) {
            if (drop.isEmpty()) continue;

            int original = drop.getCount();
            if (original <= 0) continue;

            int extra = serverLevel.random.nextInt(original + 1);

            if (extra > 0) {
                ItemStack bonus = drop.copy();
                bonus.setCount(extra);

                Block.popResource(level, pos, bonus);
            }
        }
    }

    // =====================
    // COMANDO DEBUG
    // =====================
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        CheckModOresCommand.register(dispatcher);
    }
}