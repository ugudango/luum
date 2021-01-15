package net.ugudango.luum.setup;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.ugudango.luum.blocks.custom_carpet.CustomCarpet;
import net.ugudango.luum.blocks.floor_loom.FloorLoom;

import java.util.function.Supplier;

public class ModBlocks {
    public static final RegistryObject<FloorLoom> FLOOR_LOOM = register("floor_loom", () ->
            new FloorLoom(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(2,10).harvestLevel(2).sound(SoundType.WOOD)));

    public static final RegistryObject<CustomCarpet> CUSTOM_CARPET = register("custom_carpet", () ->
            new CustomCarpet(AbstractBlock.Properties.create(Material.CARPET).hardnessAndResistance(0.8f).sound(SoundType.CLOTH)));

    public static void register() {}

    private  static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return Registration.BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> ret = registerNoItem(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().group(ItemGroup.MATERIALS)));
        return ret;
    }
}
