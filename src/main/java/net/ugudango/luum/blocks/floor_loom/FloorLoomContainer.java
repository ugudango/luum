package net.ugudango.luum.blocks.floor_loom;

import net.minecraft.block.Block;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import net.ugudango.luum.setup.ModBlocks;
import net.ugudango.luum.setup.ModContainers;

import javax.annotation.Nullable;

public class FloorLoomContainer extends Container {
    private TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;

    public FloorLoomContainer(int windowID, World world, BlockPos blockPos, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        super(ModContainers.FLOOR_LOOM_CONTAINER.get(), windowID);

        this.tileEntity = world.getTileEntity(blockPos);
        this.playerEntity = playerEntity;
        this.playerInventory = new InvWrapper(playerInventory);

        if(tileEntity != null) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 28, 30));

                addSlot(new SlotItemHandler(h, 1, 64, 21));
                addSlot(new SlotItemHandler(h, 2, 82, 21));
                addSlot(new SlotItemHandler(h, 3, 64, 39));
                addSlot(new SlotItemHandler(h, 4, 82, 39));

                addSlot(new SlotItemHandler(h, 5, 136, 30));
            });
        }

        layoutPlayerInvetory(10, 70);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, ModBlocks.FLOOR_LOOM.get());
    }

    private void layoutPlayerInvetory(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for(int i=0; i<amount; ++i) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            ++index;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for(int j=0; j<verAmount; ++j) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot == null || !slot.getHasStack()) return itemstack;

        ItemStack stack = slot.getStack();
        itemstack = stack.copy();
        if (index - 5 <= 0) {
            if (!this.mergeItemStack(stack, 6, 42, true)) {
                return ItemStack.EMPTY;
            }
            slot.onSlotChange(stack, itemstack);
        } else {

            Block blockFromItem = null;
            try {
                blockFromItem = ForgeRegistries.BLOCKS.getValue(stack.getItem().getRegistryName());
            } catch (Exception e) {
                System.out.println(e);
            }
            if (blockFromItem != null)
                if (blockFromItem instanceof CarpetBlock) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }

            Item item = null;
            try {
                item = ForgeRegistries.ITEMS.getValue(stack.getItem().getRegistryName());
            } catch (Exception e) {
                System.out.println(e);
            }
            if (item != null) {
                if (item instanceof DyeItem) {
                    if (!this.mergeItemStack(stack, 1, 5, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
        }

        return ItemStack.EMPTY;
    }
}
