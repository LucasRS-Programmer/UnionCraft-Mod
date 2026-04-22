package com.unioncraftmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.unioncraftmod.UnionCraftMod;
import com.unioncraftmod.menu.CompressorMenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CompressorScreen extends AbstractContainerScreen<CompressorMenu> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(
                    UnionCraftMod.MOD_ID,
                    "textures/gui/crafter_compressor_gui.png"
            );
    public CompressorScreen(CompressorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        int progress = getScaledProgress();

        guiGraphics.blit(TEXTURE,
                x + 89, y + 25,   // posição da barra
                176, 0,           // posição da textura (onde está a barra no PNG)
                progress, 26);    // tamanho
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics); // escurece o fundo

        super.render(guiGraphics, mouseX, mouseY, partialTick);

        this.renderTooltip(guiGraphics, mouseX, mouseY);
}
    private int getScaledProgress() {
        int progress = this.menu.getData().get(0);
        int maxProgress = this.menu.getData().get(1);

        int progressBarSize = 24; // altura ou largura da barra

        return maxProgress != 0 && progress != 0
                ? progress * progressBarSize / maxProgress
                : 0;
    }

}