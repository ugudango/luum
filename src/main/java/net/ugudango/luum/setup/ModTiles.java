package net.ugudango.luum.setup;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.ugudango.luum.blocks.CustomCarpetTile;
import net.ugudango.luum.blocks.FloorLoom;

import java.util.function.Supplier;

public class ModTiles {
    public static final RegistryObject<TileEntityType<CustomCarpetTile>> CUSTOM_CARPET = Registration.TILES.register("custom_carpet", () ->
            TileEntityType.Builder.create(CustomCarpetTile::new, ModBlocks.CUSTOM_CARPET.get()).build(null));

    public static void register() {}
}
