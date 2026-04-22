package com.unioncraftmod.item;

import com.unioncraftmod.UnionCraftMod;
import com.unioncraftmod.block.ModBlocks;
import com.unioncraftmod.item.custom.*;
import com.unioncraftmod.item.material.ModArmorMaterials;
import com.unioncraftmod.tier.ModToolTiers;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, UnionCraftMod.MOD_ID);

    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SAPPHIRE = ITEMS.register("sapphire",
            () -> new Item (new Item.Properties()));

    public static final RegistryObject<Item> COMPRESSED_COAL =
            ITEMS.register("compressed_coal",
                    () -> new FuelItem(new Item.Properties(), 1600 * 9, false));

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

    //RUBY SET
    public static final RegistryObject<Item> RUBY_SWORD = ITEMS.register("ruby_sword",
            () -> new SwordItem(ModToolTiers.RUBY_ITEMS, 4, -2.4F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> RUBY_PICKAXE = ITEMS.register("ruby_pickaxe",
            () -> new PickaxeItem(ModToolTiers.RUBY_ITEMS, 1, -2.8F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> RUBY_AXE = ITEMS.register("ruby_axe",
            () -> new AxeItem(ModToolTiers.RUBY_ITEMS, 5.0F, -3.0F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> RUBY_SHOVEL = ITEMS.register("ruby_shovel",
            () -> new ShovelItem(ModToolTiers.RUBY_ITEMS, 1.5F, -3.0F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> RUBY_HOE = ITEMS.register("ruby_hoe",
            () -> new HoeItem(ModToolTiers.RUBY_ITEMS, -2, 0.0F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> RUBY_HELMET = ITEMS.register("ruby_helmet",
            () -> new RubyArmorItem(ModArmorMaterials.RUBY, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> RUBY_CHESTPLATE = ITEMS.register("ruby_chestplate",
            () -> new RubyArmorItem(ModArmorMaterials.RUBY, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> RUBY_LEGGINGS = ITEMS.register("ruby_leggings",
            () -> new RubyArmorItem(ModArmorMaterials.RUBY, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> RUBY_BOOTS = ITEMS.register("ruby_boots",
            () -> new RubyArmorItem(ModArmorMaterials.RUBY, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    //SAPPHIRE SET
    public static final RegistryObject<Item> SAPPHIRE_SWORD = ITEMS.register("sapphire_sword",
            () -> new SwordItem(ModToolTiers.SAPPHIRE_ITEMS, 3, -2.4F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> SAPPHIRE_PICKAXE = ITEMS.register("sapphire_pickaxe",
            () -> new PickaxeItem(ModToolTiers.SAPPHIRE_ITEMS, 1, -2.8F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> SAPPHIRE_AXE = ITEMS.register("sapphire_axe",
            () -> new AxeItem(ModToolTiers.SAPPHIRE_ITEMS, 5.0F, -3.0F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> SAPPHIRE_SHOVEL = ITEMS.register("sapphire_shovel",
            () -> new ShovelItem(ModToolTiers.SAPPHIRE_ITEMS, 1.5F, -3.0F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> SAPPHIRE_HOE = ITEMS.register("sapphire_hoe",
            () -> new HoeItem(ModToolTiers.SAPPHIRE_ITEMS, -2, 0.0F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> SAPPHIRE_HELMET = ITEMS.register("sapphire_helmet",
            () -> new SapphireArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> SAPPHIRE_CHESTPLATE = ITEMS.register("sapphire_chestplate",
            () -> new SapphireArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> SAPPHIRE_LEGGINGS = ITEMS.register("sapphire_leggings",
            () -> new SapphireArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));;

    public static final RegistryObject<Item> SAPPHIRE_BOOTS = ITEMS.register("sapphire_boots",
            () -> new SapphireArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));


    public static final RegistryObject<Item> BIG_BANG = ITEMS.register("big_bang",
            () -> new FuelItem(new Item.Properties(), 1600, true));


    //BLOCKS
    public static final RegistryObject<Item> CRAFTER_COMPRESSOR =
            ITEMS.register("crafter_compressor",
                    () -> new BlockItem(ModBlocks.CRAFTER_COMPRESSOR.get(), new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
