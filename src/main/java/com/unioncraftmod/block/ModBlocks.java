package com.unioncraftmod.block;

import com.unioncraftmod.UnionCraftMod;
import com.unioncraftmod.block.custom.CrafterCompressorBlock;
import com.unioncraftmod.item.ModItems;
import com.unioncraftmod.item.custom.FuelBlockItem;
import net.minecraft.client.resources.model.Material;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.server.command.ModIdArgument;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, UnionCraftMod.MOD_ID);

    public static final RegistryObject<Block> RUBY_BLOCK = registerBlock("ruby_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> SAPPHIRE_BLOCK = registerBlock("sapphire_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> RUBY_ORE = registerBlock("ruby_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.AMETHYST)
                    .strength(2f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));

    public static final RegistryObject<Block> DEEPSLATE_RUBY_ORE = registerBlock("deepslate_ruby_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).sound(SoundType.AMETHYST)
                    .strength(3f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));

    public static final RegistryObject<Block> SAPPHIRE_ORE = registerBlock("sapphire_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.AMETHYST)
                    .strength(2f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));

    public static final RegistryObject<Block> DEEPSLATE_SAPPHIRE_ORE = registerBlock("deepslate_sapphire_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).sound(SoundType.AMETHYST)
                    .strength(3f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));

    public static final RegistryObject<Block> CRAFTER_COMPRESSOR =
            BLOCKS.register("crafter_compressor",
                    () -> new CrafterCompressorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                            .strength(4f)
                            .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> COMPRESSED_COAL_BLOCK =
            registerFuelBlock("compressed_coal_block",
                    () -> new Block(
                            BlockBehaviour.Properties.copy(Blocks.COAL_BLOCK)
                                    .sound(SoundType.DEEPSLATE)
                                    .strength(5f)
                                    .requiresCorrectToolForDrops()
                            ),
                    1600 * 81
            );

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    };

    //BLOCOS DE COMBUSTIVEL
    private static <T extends Block> RegistryObject<T> registerFuelBlock(String name, Supplier<T> block, int burnTime) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);

        ModItems.ITEMS.register(name,
                () -> new FuelBlockItem(toReturn.get(), new Item.Properties(), burnTime));

        return toReturn;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
