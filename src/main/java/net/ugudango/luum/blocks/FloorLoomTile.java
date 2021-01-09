package net.ugudango.luum.blocks;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.GrassBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.client.model.pipeline.ForgeBlockModelRenderer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.ugudango.luum.setup.Registration;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.common.util.Constants;

//public class FloorLoomTile extends TileEntity implements ITickableTileEntity {
//
//    private ItemStackHandler itemHandler = createHandler();
//
//    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
//
//    public FloorLoomTile(TileEntityType<?> tileEntityTypeIn) {
//        super(tileEntityTypeIn);
//    }
//
//    @Override
//    public void tick() {
//
//    }
//}
