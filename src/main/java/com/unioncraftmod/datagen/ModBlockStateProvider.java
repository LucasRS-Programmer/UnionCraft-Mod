package com.unioncraftmod.datagen;

import com.unioncraftmod.UnionCraftMod;
import com.unioncraftmod.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, UnionCraftMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.RUBY_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_RUBY_ORE);
        blockWithItem(ModBlocks.RUBY_BLOCK);

        blockWithItem(ModBlocks.SAPPHIRE_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_SAPPHIRE_ORE);
        blockWithItem(ModBlocks.SAPPHIRE_BLOCK);

        blockWithItem(ModBlocks.COMPRESSED_COAL_BLOCK);


        ModelFile model = models().withExistingParent("crafter_compressor", mcLoc("block/cube"))
                .texture("down", modLoc("block/crafter_compressor_bottom"))
                .texture("up", modLoc("block/crafter_compressor_top"))
                .texture("north", modLoc("block/crafter_compressor_front"))
                .texture("south", modLoc("block/crafter_compressor_side"))
                .texture("west", modLoc("block/crafter_compressor_side"))
                .texture("east", modLoc("block/crafter_compressor_side"));

        horizontalBlock(ModBlocks.CRAFTER_COMPRESSOR.get(), model);
        simpleBlockItem(ModBlocks.CRAFTER_COMPRESSOR.get(), model);

    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}