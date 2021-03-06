package net.ugudango.luum.blocks.custom_carpet;

import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CustomCarpet extends Block {
    private final VoxelShape shape = VoxelShapes.create(0, 0, 0, 1, 1.0f / 16.0f, 1);

    public CustomCarpet(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return shape;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HorizontalBlock.HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(HorizontalBlock.HORIZONTAL_FACING, rot.rotate(state.get(HorizontalBlock.HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(HorizontalBlock.HORIZONTAL_FACING)));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        CustomCarpetTile cct = new CustomCarpetTile();
        return cct;
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        TileEntity tileEntity = builder.get(LootParameters.BLOCK_ENTITY);
        if(tileEntity instanceof CustomCarpetTile) {
            final List<ItemStack> drops = new ArrayList<>();
            ItemStack stack = new ItemStack(this);

            CompoundNBT tag = new CompoundNBT();
            ((CustomCarpetTile)tileEntity).write(tag);

            stack.setTagInfo("BlockEntityTag", tag);
            drops.add(stack);

            return drops;
        }
        else {
            return super.getDrops(state, builder);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HorizontalBlock.HORIZONTAL_FACING);
    }
}
