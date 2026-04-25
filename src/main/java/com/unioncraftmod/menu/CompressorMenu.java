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

    //blockEntity → é o bloco no mundo (onde estão os itens)
    private final CrafterCompressorBlockEntity blockEntity;

    //data → dados sincronizados (tipo progresso da máquina, energia, etc.)
    private final ContainerData data;

    // Recebe a posição do bloco (BlockPos)
    // Busca o BlockEntity no mundo
    public CompressorMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, getBlockEntity(inv, extraData));
    }

    //Método auxiliar
    private static CrafterCompressorBlockEntity getBlockEntity(Inventory inv, FriendlyByteBuf data) {
        BlockPos pos = data.readBlockPos(); //Lê a posição do bloco
        BlockEntity entity = inv.player.level().getBlockEntity(pos); //pega o bloco no mundo

        //Verifica se é o bloco correto!
        //Se FOR → retorna
        //Se NÃO FOR → erro (crasha)
        if (entity instanceof CrafterCompressorBlockEntity be) {
            return be;
        }

        throw new IllegalStateException("BlockEntity inválido em: " + pos);
    }

    // Construtor principal
    public CompressorMenu(int id, Inventory inv, CrafterCompressorBlockEntity entity) {
        super(ModMenusTypes.COMPRESSOR_MENU.get(), id);

        //Conecta o menu com o bloco
        this.blockEntity = entity;
        this.data = entity.getData();


        // 🔥 GRID 3x3 (slots 0-8)
        //[0][1][2]
        //[3][4][5]
        //[6][7][8]
        int startX = 30;
        int startY = 17;

        int slotIndex = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                this.addSlot(new SlotItemHandler(entity.getItemHandler(), //Usa o inventário interno do bloco (ItemHandler)
                        slotIndex,
                        startX + col * 18,
                        startY + row * 18));
                slotIndex++;
            }
        }

        // SLOT DE SAÍDA (slot 9)
        // → [9]

        this.addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 9, 124, 35) {

            //Não permite o jogador colocar itens no slot 9
            //Só pode retirar
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        // Cria o INVENTÁRIO DO PLAYER
        //27 slots (3x9)
        addPlayerInventory(inv);

        //Cria a Hotbar - 9 slots (barra das "ferramentas")
        addPlayerHotbar(inv);

        //Faz o client enxergar dados do servidor - Progresso da máquina, tempo de crafting, etc.
        addDataSlots(data);
    }

    //INVENTÁRIO DO PLAYER
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

    //HOTBAR DO PLAYER
    private void addPlayerHotbar(Inventory inv) {
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inv,
                    col,
                    8 + col * 18,
                    142));
        }
    }

    //stillValid - Define se o menu pode continuar aberto
    @Override
    public boolean stillValid(Player player) {
        return stillValid(
                ContainerLevelAccess.create(
                        blockEntity.getLevel(), //Mundo onde o bloco está
                        blockEntity.getBlockPos() //Posição do bloco
                ),
                player,
                blockEntity.getBlockState().getBlock() //qual bloco é (se ainda é o CrafterCompressor)
        );
    }

    //SHIFT CLICK - Essa parte controla o comportamento do shift+click
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();

            //Se clicou nos slots do bloco (0–9) → Move pro inventário do player
            if (index < 10) {
                if (!this.moveItemStackTo(stack, 10, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            //Se clicou no inventário do player → Move pro grid (slots 0–8)
            else {
                if (!this.moveItemStackTo(stack, 0, 9, false)) {
                    return ItemStack.EMPTY;
                }
            }

            //Mantém o inventário consistente
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