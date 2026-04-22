package com.unioncraftmod.block.entity;

import com.unioncraftmod.menu.CompressorMenu;
import com.unioncraftmod.recipe.CompressorRecipe;
import com.unioncraftmod.recipe.CountedIngredient;
import com.unioncraftmod.recipe.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CrafterCompressorBlockEntity extends BlockEntity implements MenuProvider {

    private CompressorRecipe cachedRecipe = null;
    private boolean inventoryChanged = true;

    // 🔴 INVENTÁRIO
    // Slots 0–8 = INPUT
    // Slot 9 = OUTPUT
    private final ItemStackHandler itemHandler = new ItemStackHandler(10) {

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            inventoryChanged = true;
        }


        // ❌ NÃO permite inserir no slot de output
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return slot != 9;
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return super.extractItem(slot, amount, simulate);
        }
    };

    // 🔴 CAPABILITIES
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> inputHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> outputHandler = LazyOptional.empty();

    // 🔴 PROGRESSO
    private int progress = 0;
    private int maxProgress = 72;

    public CrafterCompressorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CRAFTER_COMPRESSOR_BE.get(), pos, state);
    }

    // 🔴 INICIALIZAÇÃO DOS HANDLERS
    @Override
    public void onLoad() {
        super.onLoad();

        // Handler completo (GUI, etc)
        lazyItemHandler = LazyOptional.of(() -> itemHandler);

        // INPUT → slots 0 até 8
        inputHandler = LazyOptional.of(() -> new RangedWrapper(itemHandler, 0, 9));

        // OUTPUT → slot 9
        outputHandler = LazyOptional.of(() -> new RangedWrapper(itemHandler, 9, 10));
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        inputHandler.invalidate();
        outputHandler.invalidate();
    }

    // 🔴 LÓGICA DE AUTOMAÇÃO
    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if (cap == ForgeCapabilities.ITEM_HANDLER) {

            if (side == Direction.DOWN) {
                return outputHandler.cast(); // ↓ saída
            }

            if (side == Direction.UP) {
                return inputHandler.cast(); // ↑ entrada
            }

            // lados também são entrada
            return inputHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    // 🔴 MENU
    @Override
    public Component getDisplayName() {
        return Component.literal("Compressor");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new CompressorMenu(id, inventory, this);
    }


    //🔴 CACHE DE RECEITA
    private static Optional<CompressorRecipe> getCachedRecipe(CrafterCompressorBlockEntity entity) {

        if (entity.level == null) return Optional.empty();

        // USA CACHE SE AINDA FOR VÁLIDO
        if (!entity.inventoryChanged && entity.cachedRecipe != null) {
            if (canStillCraft(entity, entity.cachedRecipe)) {
                return Optional.of(entity.cachedRecipe);
            }
        }

        // RECRIA CONTAINER
        SimpleContainer container = new SimpleContainer(entity.itemHandler.getSlots());

        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            container.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        // BUSCA RECEITA NOVA
        Optional<CompressorRecipe> recipe = entity.level.getRecipeManager().getRecipeFor(
                ModRecipes.COMPRESSOR_TYPE.get(),
                container,
                entity.level
        );

        // ATUALIZA CACHE
        entity.cachedRecipe = recipe.orElse(null);
        entity.inventoryChanged = false;

        return recipe;
    }
    // 🔴 TICK
    public static void tick(Level level, BlockPos pos, BlockState state, CrafterCompressorBlockEntity entity) {
        if (level.isClientSide) return;

        Optional<CompressorRecipe> recipeOpt = getCachedRecipe(entity);

        if (recipeOpt.isPresent()) {
            CompressorRecipe recipe = recipeOpt.get();

            if (canInsertOutput(entity, recipe)) {
                entity.progress++;

                if (entity.progress >= entity.maxProgress) {
                    craftItem(entity, recipe);
                    entity.setChanged();
                }
            } else {
                entity.resetProgress();
                entity.setChanged();
            }
        } else {
            entity.resetProgress();
            entity.setChanged();
        }
    }

    // 🔴 VERIFICA OUTPUT
    private static boolean canInsertOutput(CrafterCompressorBlockEntity entity, CompressorRecipe recipe) {
        int outputSlot = 9;

        ItemStack outputStack = entity.itemHandler.getStackInSlot(outputSlot);

        if (entity.level == null) return false;

        ItemStack result = recipe.getResultItem(entity.level.registryAccess());

        // evita edge case raro (resultado vazio)
        if (result.isEmpty()) return false;

        if (outputStack.isEmpty()) return true;

        return ItemStack.isSameItemSameTags(outputStack, result)
                && outputStack.getCount() + result.getCount() <= outputStack.getMaxStackSize();
    }

    // 🔴 CRAFT
    private static void craftItem(CrafterCompressorBlockEntity entity, CompressorRecipe recipe) {

        NonNullList<CountedIngredient> ingredients = recipe.getCountedIngredients();

        // REMOVE INGREDIENTES
        for (CountedIngredient counted : ingredients) {

            int remaining = counted.getCount();

            for (int i = 0; i < 9; i++) {
                ItemStack stack = entity.itemHandler.getStackInSlot(i);

                if (counted.getIngredient().test(stack)) {

                    int remove = Math.min(remaining, stack.getCount());

                    entity.itemHandler.extractItem(i, remove, false);
                    remaining -= remove;

                    if (remaining <= 0) break;
                }
            }
        }

        // OUTPUT
        int outputSlot = 9;
        ItemStack outputStack = entity.itemHandler.getStackInSlot(outputSlot);
        if (entity.level == null) return;

        ItemStack result = recipe.getResultItem(entity.level.registryAccess());

        if (outputStack.isEmpty()) {
            entity.itemHandler.setStackInSlot(outputSlot, result.copy());
        } else {
            outputStack.grow(result.getCount());
        }

        //Verificação
        entity.inventoryChanged = true;

        entity.resetProgress();
    }

    // 🔴 RESET
    private void resetProgress() {
        this.progress = 0;
    }

    // 🔴 NBT
    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("progress", progress);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("progress");
    }

    // 🔴 GUI DATA
    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> progress;
                case 1 -> maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> progress = value;
                case 1 -> maxProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public ContainerData getData() {
        return data;
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    private static boolean canStillCraft(CrafterCompressorBlockEntity entity, CompressorRecipe recipe) {

        for (CountedIngredient counted : recipe.getCountedIngredients()) {
            int needed = counted.getCount();

            for (int i = 0; i < 9; i++) {
                ItemStack stack = entity.itemHandler.getStackInSlot(i);

                if (counted.getIngredient().test(stack)) {
                    needed -= stack.getCount();
                    if (needed <= 0) break;
                }
            }

            if (needed > 0) {
                return false;
            }
        }

        return true;
    }
}