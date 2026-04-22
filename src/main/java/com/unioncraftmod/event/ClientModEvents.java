package com.unioncraftmod.event;


import com.unioncraftmod.UnionCraftMod;
import com.unioncraftmod.menu.ModMenusTypes;
import com.unioncraftmod.screen.CompressorScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = UnionCraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(ModMenusTypes.COMPRESSOR_MENU.get(), CompressorScreen::new);
    }
}