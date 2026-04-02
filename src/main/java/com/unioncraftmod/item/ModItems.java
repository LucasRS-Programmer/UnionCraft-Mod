package com.unioncraftmod.item;

import com.unioncraftmod.UnionCraftMod;
import com.unioncraftmod.item.custom.FuelItem;
import com.unioncraftmod.item.custom.FlowerSwordItem;
import com.unioncraftmod.tier.ModToolTiers;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.unioncraftmod.item.custom.FastFoodItem;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, UnionCraftMod.MOD_ID);

    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SAPPHIRE = ITEMS.register("sapphire",
            () -> new Item (new Item.Properties()));

    public static final RegistryObject<Item> GUAVA = ITEMS.register("guava",
            () -> new FastFoodItem (new Item.Properties().food(ModFoods.GUAVA)));

    public static final RegistryObject<Item> ROSE_SWORD = ITEMS.register("rose_sword",
            () -> new FlowerSwordItem(ModToolTiers.ROSE, 3, -2.4F,
                    new Item.Properties().rarity(Rarity.UNCOMMON),
                    MobEffects.REGENERATION, 100, 0,
                    null, 0,
                    false, false));

    public static final RegistryObject<Item> TORCHFLOWER_SWORD = ITEMS.register("torchflower_sword",
            () -> new FlowerSwordItem(ModToolTiers.TORCHFLOWER, 3, -2.4F,
                    new Item.Properties().rarity(Rarity.RARE),
                    MobEffects.FIRE_RESISTANCE, 100, 0,
                    Enchantments.FIRE_ASPECT, 2,
                    false, true));

    public static final RegistryObject<Item> SUNFLOWER_SWORD = ITEMS.register("sunflower_sword",
            () -> new FlowerSwordItem(ModToolTiers.YELLOW_FLOWERS, 3, -2.4F,
                    new Item.Properties().rarity(Rarity.UNCOMMON),
                    null, 0, 0,
                    null, 0,
                    true, false));

    public static final RegistryObject<Item> BIG_BANG = ITEMS.register("big_bang",
            () -> new FuelItem(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC), 4000));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
