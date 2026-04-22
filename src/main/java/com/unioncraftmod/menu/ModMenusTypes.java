package com.unioncraftmod.menu;

import com.unioncraftmod.UnionCraftMod;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenusTypes {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, UnionCraftMod.MOD_ID);

    public static final RegistryObject<MenuType<CompressorMenu>> COMPRESSOR_MENU =
            MENUS.register("compressor_menu",
                    () -> IForgeMenuType.create(CompressorMenu::new));
}