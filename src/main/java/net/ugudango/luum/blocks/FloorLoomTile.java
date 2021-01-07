package net.ugudango.luum.blocks;

import net.minecraft.tileentity.TileEntityType;
import net.ugudango.luum.setup.Registration;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.common.util.Constants;

public class FloorLoomTile extends TileEntity {
    public FloorLoomTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }
}
