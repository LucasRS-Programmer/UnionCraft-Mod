package com.unioncraftmod.item;

import com.unioncraftmod.UnionCraftMod;
import com.unioncraftmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UnionCraftMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> UNIONCRAFT_TAB = CREATIVE_MODE_TAB.register("unioncraft_tab",
        () -> CreativeModeTab.builder()
                .icon(() -> new ItemStack(ModItems.RUBY.get()))
                .title(Component.translatable("creativetab.unioncraft_tab"))
                .displayItems((itemDisplayParameters, output) -> {
                    output.accept(ModItems.RUBY.get());
                    output.accept(ModItems.SAPPHIRE.get());

                    output.accept(ModItems.ROSE_SWORD.get());
                    output.accept(ModItems.TORCHFLOWER_SWORD.get());
                    output.accept(ModItems.SUNFLOWER_SWORD.get());

                    output.accept(ModBlocks.RUBY_ORE.get());
                    output.accept(ModBlocks.DEEPSLATE_RUBY_ORE.get());
                    output.accept(ModBlocks.SAPPHIRE_ORE.get());
                    output.accept(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get());
                    output.accept(ModBlocks.RUBY_BLOCK.get());
                    output.accept(ModBlocks.SAPPHIRE_BLOCK.get());

                    output.accept(ModItems.GUAVA.get());

                    output.accept(ModItems.BIG_BANG.get());

                } )
                .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
