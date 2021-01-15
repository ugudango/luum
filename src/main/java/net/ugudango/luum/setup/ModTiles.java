package net.ugudango.luum.setup;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.ugudango.luum.blocks.custom_carpet.CustomCarpetTile;
import net.ugudango.luum.blocks.floor_loom.FloorLoomTile;

public class ModTiles {
    public static final RegistryObject<TileEntityType<CustomCarpetTile>> CUSTOM_CARPET = Registration.TILES.register("custom_carpet", () ->
            TileEntityType.Builder.create(CustomCarpetTile::new, ModBlocks.CUSTOM_CARPET.get()).build(null));

    public static final RegistryObject<TileEntityType<FloorLoomTile>> FLOOR_LOOM = Registration.TILES.register("floor_loom", () ->
            TileEntityType.Builder.create(FloorLoomTile::new, ModBlocks.FLOOR_LOOM.get()).build(null));

    public static void register() {}
}
