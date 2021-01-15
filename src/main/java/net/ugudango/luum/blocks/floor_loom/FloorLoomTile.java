package net.ugudango.luum.blocks.floor_loom;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.Block;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.GrassBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.client.model.pipeline.ForgeBlockModelRenderer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import net.ugudango.luum.blocks.custom_carpet.CustomCarpet;
import net.ugudango.luum.blocks.custom_carpet.CustomCarpetTile;
import net.ugudango.luum.setup.ModBlocks;
import net.ugudango.luum.setup.ModTiles;
import net.ugudango.luum.setup.Registration;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class FloorLoomTile extends TileEntity implements ITickableTileEntity{
    private ItemStackHandler itemHandler = createHandler();

    @Override
    public void tick() {
        if (world.isRemote) return;

        if (itemHandler.getStackInSlot(5) != ItemStack.EMPTY
            || itemHandler.getStackInSlot(5).getCount() > 0) return;

        for (int i = 1; i < 5; i++) {
            if (itemHandler.getStackInSlot(i) == ItemStack.EMPTY) {

            }
        }

        Block possiblyCarpet = ForgeRegistries.BLOCKS.getValue(itemHandler.getStackInSlot(0).getItem().getRegistryName());
        if (!(possiblyCarpet instanceof CarpetBlock)) return;

        ArrayList<Item> possiblyDyes = new ArrayList();
        int[] colors = {0, 0, 0, 0};
        for (int i = 0; i < 4; i++) {
            possiblyDyes.add(ForgeRegistries.ITEMS.getValue(itemHandler.getStackInSlot(i + 1).getItem().getRegistryName()));
            if (!(possiblyDyes.get(i) instanceof DyeItem)) return;
            colors[i] = ((DyeItem) possiblyDyes.get(i)).getDyeColor().getId();
        }

        ItemStack customCarpet = new ItemStack(ModBlocks.CUSTOM_CARPET.get());
        customCarpet.setCount(1);

        CompoundNBT tag = new CompoundNBT();
        tag.putIntArray("colors", colors);

        customCarpet.setTagInfo("BlockEntityTag", tag);

        itemHandler.insertItem(5, customCarpet, false);

        for (int i = 0; i < 5; i++) {
            itemHandler.extractItem(0, 1, false);
        }
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(6) {
            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                // Any Recyclable item can be thrown in here
                //TODO look at crafting recipes as well
                if (slot == 5) return false;
                return true;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if(isItemValid(slot, stack))
                    return super.insertItem(slot, stack, simulate);
                else
                    return super.insertItem(slot, stack, true);
            }
        };
    }


    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);


    public FloorLoomTile() {
        super(ModTiles.FLOOR_LOOM.get());
    }

    @Override
    public void remove() {
        super.remove();
        handler.invalidate();
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        itemHandler.deserializeNBT(tag.getCompound("inv"));
        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", itemHandler.serializeNBT());
        return super.write(tag);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, /* @Nullable */ Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }
}
