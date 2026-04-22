package com.unioncraftmod.block.entity;

import com.unioncraftmod.UnionCraftMod;
import com.unioncraftmod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

    public class ModBlockEntities {

        public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
                DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, UnionCraftMod.MOD_ID);

        public static final RegistryObject<BlockEntityType<CrafterCompressorBlockEntity>> CRAFTER_COMPRESSOR_BE =
                BLOCK_ENTITIES.register("crafter_compressor",
                        () -> BlockEntityType.Builder.of(CrafterCompressorBlockEntity::new,
                                ModBlocks.CRAFTER_COMPRESSOR.get()).build(null));

        public static void register(IEventBus modEventBus) {
            BLOCK_ENTITIES.register(modEventBus);
        }
    }


