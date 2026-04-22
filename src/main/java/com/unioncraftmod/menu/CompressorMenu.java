package com.unioncraftmod.menu;


import com.unioncraftmod.block.entity.CrafterCompressorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraft.world.inventory.SimpleContainerData;


public class CompressorMenu extends AbstractContainerMenu {

    private final CrafterCompressorBlockEntity blockEntity;
    private final ContainerData data;

    // Construtor chamado pelo client
    public CompressorMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, getBlockEntity(inv, extraData));
    }

    private static CrafterCompressorBlockEntity getBlockEntity(Inventory inv, FriendlyByteBuf data) {
        BlockPos pos = data.readBlockPos();
        BlockEntity entity = inv.player.level().getBlockEntity(pos);

        if (entity instanceof CrafterCompressorBlockEntity be) {
            return be;
        }

        throw new IllegalStateException("BlockEntity inválido em: " + pos);
    }

    // Construtor principal
    public CompressorMenu(int id, Inventory inv, CrafterCompressorBlockEntity entity) {
        super(ModMenusTypes.COMPRESSOR_MENU.get(), id);

        this.blockEntity = entity;
        this.data = entity.getData();


        // 🔥 GRID 3x3 (slots 0-8)
        int startX = 30;
        int startY = 17;

        int slotIndex = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                this.addSlot(new SlotItemHandler(entity.getItemHandler(),
                        slotIndex,
                        startX + col * 18,
                        startY + row * 18));
                slotIndex++;
            }
        }

        // SLOT DE SAÍDA (slot 9)
        this.addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 9, 124, 35) {

            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        // 🔥 INVENTÁRIO DO PLAYER
        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        addDataSlots(data);
    }

    private void addPlayerInventory(Inventory inv) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inv,
                        col + row * 9 + 9,
                        8 + col * 18,
                        84 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inv) {
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inv,
                    col,
                    8 + col * 18,
                    142));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    // 🔥 SHIFT CLICK
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();

            // Slots do bloco (0-9)
            if (index < 10) {
                if (!this.moveItemStackTo(stack, 10, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            // Slots do player
            else {
                if (!this.moveItemStackTo(stack, 0, 9, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    public ContainerData getData() {
        return this.data;
    }
}