package net.ugudango.luum.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.util.Constants;
import net.ugudango.luum.setup.ModTiles;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class CustomCarpetTile extends TileEntity {

    public static final ModelProperty<int[]> COLORS = new ModelProperty<>();
    public static final ModelProperty<BlockState> CARPET_BLOCK_STATE = new ModelProperty<>();

    private int[] colors = {1, 3, 4, 11};
    private BlockState carpetBlockState;

    public void setCarpetBlockState(BlockState carpetBlockState) {
        this.carpetBlockState = carpetBlockState;
    }

    public CustomCarpetTile() {
        super(ModTiles.CUSTOM_CARPET.get());
    }

    @Nonnull
    @Override
    public IModelData getModelData() {
        return new ModelDataMap.Builder()
                .withInitial(COLORS, colors)
                .withInitial(CARPET_BLOCK_STATE, carpetBlockState)
                .build();
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT tag = super.getUpdateTag();
        if (colors != null) {
            tag.putIntArray("colors", colors);
        }
        if (carpetBlockState != null) {
            tag.put("carpet_block_state", NBTUtil.writeBlockState(carpetBlockState));
        }
        return tag;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        read(state, tag);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {

        int[] oldColors = colors;
        BlockState oldBlockState = carpetBlockState;
        CompoundNBT tag = pkt.getNbtCompound();
        handleUpdateTag(getBlockState(), tag);
        if (!Objects.equals(oldColors, colors)
           || !Objects.equals(oldBlockState, carpetBlockState)) {
            ModelDataManager.requestModelDataRefresh(this);
            world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
        }
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        if (nbt.contains("colors")) {
            /**
             * TODO: Try it with byteArray in the future. It's less data to store.
             */
            colors = nbt.getIntArray("colors");
        }
        if (nbt.contains("carpet_block_state")) {
            carpetBlockState = NBTUtil.readBlockState(nbt.getCompound("carpet_block_state"));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        if (colors != null) {
            tag.putIntArray("colors", colors);
        }
        if (carpetBlockState != null) {
            tag.put("carpet_block_state", NBTUtil.writeBlockState(carpetBlockState));
        }
        return super.write(tag);
    }
}
