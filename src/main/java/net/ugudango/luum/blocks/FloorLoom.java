package net.ugudango.luum.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class FloorLoom extends Block {

    public FloorLoom(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(HorizontalBlock.HORIZONTAL_FACING)) {
            case SOUTH:
                return VOXEL_SOUTH;
            case EAST:
                return VOXEL_EAST;
            case WEST:
                return VOXEL_WEST;
            default:
                return VOXEL_NORTH;
        }
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
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HorizontalBlock.HORIZONTAL_FACING);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(HorizontalBlock.HORIZONTAL_FACING)));
    }

    @Override
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 0.65f;
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        switch (state.get(HorizontalBlock.HORIZONTAL_FACING)) {
            case SOUTH:
                return VOXEL_SOUTH;
            case EAST:
                return VOXEL_EAST;
            case WEST:
                return VOXEL_WEST;
            default:
                return VOXEL_NORTH;
        }
    }

    private VoxelShape VOXEL_NORTH = Stream.of(
            Block.makeCuboidShape(30, 0, 4.56742, 31, 15, 7.56742),
            Block.makeCuboidShape(-14, 14, 4.56742, 30, 15, 5.56742),
            Block.makeCuboidShape(28, 0, 0.56742, 29, 12, 2.56742),
            Block.makeCuboidShape(-13, 10, -0.43258, 29, 11, 3.56742),
            Block.makeCuboidShape(-14, 5, 1.56742, 30, 7, 2.56742),
            Block.makeCuboidShape(-13, 0, 0.56742, -12, 12, 2.56742),
            Block.makeCuboidShape(29, 0, 0.56742, 30, 1, 7.56742),
            Block.makeCuboidShape(-14, 0, 0.56742, -13, 1, 7.56742),
            Block.makeCuboidShape(29, 3, 13.56742, 30, 9, 16.56742),
            Block.makeCuboidShape(-14, 3, 13.56742, -13, 9, 16.56742),
            Block.makeCuboidShape(-15, 0, 4.56742, -14, 15, 7.56742),
            Block.makeCuboidShape(-15, 0, 26.56742, -14, 28, 29.56742),
            Block.makeCuboidShape(-15, 10, 29.56742, -14, 16, 31.56742),
            Block.makeCuboidShape(-14, 10, 29.56742, 30, 12, 31.06742),
            Block.makeCuboidShape(30, 10, 29.56742, 31, 16, 31.56742),
            Block.makeCuboidShape(-16, 4, 27.56742, 32, 7, 28.56742),
            Block.makeCuboidShape(-15, 27, 21.56742, 31, 28, 24.56742),
            Block.makeCuboidShape(-13, 27, 15.56742, 29, 28, 18.56742),
            Block.makeCuboidShape(26, 19, 15.56742, 27, 27, 18.56742),
            Block.makeCuboidShape(-11, 19, 15.56742, -10, 27, 18.56742),
            Block.makeCuboidShape(-13, 18, 15.56742, 29, 19, 18.56742),
            Block.makeCuboidShape(-10, 13, 15.81742, 26, 18, 18.31742),
            Block.makeCuboidShape(-13, 12, 15.56742, 29, 13, 18.56742),
            Block.makeCuboidShape(29, 1, 4.56742, 30, 3, 29.56742),
            Block.makeCuboidShape(28, 1, 19.56742, 29, 28, 21.56742),
            Block.makeCuboidShape(-13, 1, 19.56742, -12, 28, 21.56742),
            Block.makeCuboidShape(29, 26, 14.56742, 30, 28, 29.56742),
            Block.makeCuboidShape(-14, 26, 14.56742, -13, 28, 29.56742),
            Block.makeCuboidShape(29, 27, 7.56742, 30, 28, 14.56742),
            Block.makeCuboidShape(30, 18, 12.56742, 31, 31, 13.56742),
            Block.makeCuboidShape(30, 15, 12.56742, 31, 16, 13.56742),
            Block.makeCuboidShape(-15, 15, 12.56742, -14, 16, 13.56742),
            Block.makeCuboidShape(-16, 13, 12.56742, 32, 15, 13.56742),
            Block.makeCuboidShape(-16, 16, 12.56742, 32, 18, 13.56742),
            Block.makeCuboidShape(-15, 18, 12.56742, -14, 31, 13.56742),
            Block.makeCuboidShape(29, 9, 4.56742, 30, 11, 21.56742),
            Block.makeCuboidShape(-15, 10, 14.56742, 31, 11, 15.56742),
            Block.makeCuboidShape(-14, 9, 4.56742, -13, 11, 21.56742),
            Block.makeCuboidShape(-14, 27, 7.56742, -13, 28, 14.56742),
            Block.makeCuboidShape(-14, 1, 4.56742, -13, 3, 29.56742),
            Block.makeCuboidShape(-11, 10, 31.06742, 27, 15, 31.56742),
            Block.makeCuboidShape(-11, 13.75, 4.06742, 27, 15.5, 4.56742),
            Block.makeCuboidShape(-11, 9.39268, 5.10111, 27, 9.89268, 16.60111),
            Block.makeCuboidShape(-11, 15, 4.56742, 27, 15.5, 31.56742),
            Block.makeCuboidShape(30, 0, 26.56742, 31, 28, 29.56742)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private VoxelShape VOXEL_EAST = Stream.of(
            Block.makeCuboidShape(-14.03371, 0, 30.03371, -11.03371, 28, 31.03371),
            Block.makeCuboidShape(7.96629, 0, 30.03371, 10.96629, 15, 31.03371),
            Block.makeCuboidShape(9.96629, 14, -13.96629, 10.96629, 15, 30.03371),
            Block.makeCuboidShape(12.96629, 0, 28.03371, 14.96629, 12, 29.03371),
            Block.makeCuboidShape(11.96629, 10, -12.96629, 15.96629, 11, 29.03371),
            Block.makeCuboidShape(12.96629, 5, -13.96629, 13.96629, 7, 30.03371),
            Block.makeCuboidShape(12.96629, 0, -12.96629, 14.96629, 12, -11.96629),
            Block.makeCuboidShape(7.96629, 0, 29.03371, 14.96629, 1, 30.03371),
            Block.makeCuboidShape(7.96629, 0, -13.96629, 14.96629, 1, -12.96629),
            Block.makeCuboidShape(-1.03371, 3, 29.03371, 1.96629, 9, 30.03371),
            Block.makeCuboidShape(-1.03371, 3, -13.96629, 1.96629, 9, -12.96629),
            Block.makeCuboidShape(7.96629, 0, -14.96629, 10.96629, 15, -13.96629),
            Block.makeCuboidShape(-14.03371, 0, -14.96629, -11.03371, 28, -13.96629),
            Block.makeCuboidShape(-16, 10, -15, -14, 16, -14),
            Block.makeCuboidShape(-15.53371, 10, -13.96629, -14.03371, 12, 30.03371),
            Block.makeCuboidShape(-16, 10, 30, -14, 16, 31),
            Block.makeCuboidShape(-13, 4, -16, -12, 7, 32),
            Block.makeCuboidShape(-9.03371, 27, -14.96629, -6.03371, 28, 31.03371),
            Block.makeCuboidShape(-3.03371, 27, -12.96629, -0.03371, 28, 29.03371),
            Block.makeCuboidShape(-3.03371, 19, 26.03371, -0.03371, 27, 27.03371),
            Block.makeCuboidShape(-3.03371, 19, -10.96629, -0.03371, 27, -9.96629),
            Block.makeCuboidShape(-3.03371, 18, -12.96629, -0.03371, 19, 29.03371),
            Block.makeCuboidShape(-2.78371, 13, -9.96629, -0.28371, 18, 26.03371),
            Block.makeCuboidShape(-3.03371, 12, -12.96629, -0.03371, 13, 29.03371),
            Block.makeCuboidShape(-14.03371, 1, 29.03371, 10.96629, 3, 30.03371),
            Block.makeCuboidShape(-6.03371, 1, 28.03371, -4.03371, 28, 29.03371),
            Block.makeCuboidShape(-6.03371, 1, -12.96629, -4.03371, 28, -11.96629),
            Block.makeCuboidShape(-14.03371, 26, 29.03371, 0.96629, 28, 30.03371),
            Block.makeCuboidShape(-14.03371, 26, -13.96629, 0.96629, 28, -12.96629),
            Block.makeCuboidShape(0.96629, 27, 29.03371, 7.96629, 28, 30.03371),
            Block.makeCuboidShape(1.96629, 18, 30.03371, 2.96629, 31, 31.03371),
            Block.makeCuboidShape(1.96629, 15, 30.03371, 2.96629, 16, 31.03371),
            Block.makeCuboidShape(1.96629, 15, -14.96629, 2.96629, 16, -13.96629),
            Block.makeCuboidShape(2, 13, -16, 3, 15, 32),
            Block.makeCuboidShape(2, 16, -16, 3, 18, 32),
            Block.makeCuboidShape(1.96629, 18, -14.96629, 2.96629, 31, -13.96629),
            Block.makeCuboidShape(-6.03371, 9, 29.03371, 10.96629, 11, 30.03371),
            Block.makeCuboidShape(-0.03371, 10, -14.96629, 0.96629, 11, 31.03371),
            Block.makeCuboidShape(-6.03371, 9, -13.96629, 10.96629, 11, -12.96629),
            Block.makeCuboidShape(0.96629, 27, -13.96629, 7.96629, 28, -12.96629),
            Block.makeCuboidShape(-14.03371, 1, -13.96629, 10.96629, 3, -12.96629),
            Block.makeCuboidShape(-16, 10, -11, -15.5, 15, 27),
            Block.makeCuboidShape(10.96629, 13.75, -10.96629, 11.46629, 15.5, 27.03371),
            Block.makeCuboidShape(-1.0674, 9.39268, -10.96629, 10.4326, 9.89268, 27.03371),
            Block.makeCuboidShape(-16, 15, -11, 11, 15.5, 27)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private VoxelShape VOXEL_SOUTH = Stream.of(
            Block.makeCuboidShape(-15, 0, -14, -14, 28, -11),
            Block.makeCuboidShape(-15, 0, 8, -14, 15, 11),
            Block.makeCuboidShape(-14, 14, 10, 30, 15, 11),
            Block.makeCuboidShape(-13, 0, 13, -12, 12, 15),
            Block.makeCuboidShape(-13, 10, 12, 29, 11, 16),
            Block.makeCuboidShape(-14, 5, 13, 30, 7, 14),
            Block.makeCuboidShape(28, 0, 13, 29, 12, 15),
            Block.makeCuboidShape(-14, 0, 8, -13, 1, 15),
            Block.makeCuboidShape(29, 0, 8, 30, 1, 15),
            Block.makeCuboidShape(-14, 3, -1, -13, 9, 2),
            Block.makeCuboidShape(29, 3, -1, 30, 9, 2),
            Block.makeCuboidShape(30, 0, 8, 31, 15, 11),
            Block.makeCuboidShape(30, 0, -14, 31, 28, -11),
            Block.makeCuboidShape(30.03371, 10, -15.96629, 31.03371, 16, -13.96629),
            Block.makeCuboidShape(-14, 10, -15.5, 30, 12, -14),
            Block.makeCuboidShape(-14.96629, 10, -15.96629, -13.96629, 16, -13.96629),
            Block.makeCuboidShape(-16, 4, -13, 32, 7, -12),
            Block.makeCuboidShape(-15, 27, -9, 31, 28, -6),
            Block.makeCuboidShape(-13, 27, -3, 29, 28, 0),
            Block.makeCuboidShape(-11, 19, -3, -10, 27, 0),
            Block.makeCuboidShape(26, 19, -3, 27, 27, 0),
            Block.makeCuboidShape(-13, 18, -3, 29, 19, 0),
            Block.makeCuboidShape(-10, 13, -2.75, 26, 18, -0.25),
            Block.makeCuboidShape(-13, 12, -3, 29, 13, 0),
            Block.makeCuboidShape(-14, 1, -14, -13, 3, 11),
            Block.makeCuboidShape(-13, 1, -6, -12, 28, -4),
            Block.makeCuboidShape(28, 1, -6, 29, 28, -4),
            Block.makeCuboidShape(-14, 26, -14, -13, 28, 1),
            Block.makeCuboidShape(29, 26, -14, 30, 28, 1),
            Block.makeCuboidShape(-14, 27, 1, -13, 28, 8),
            Block.makeCuboidShape(-15, 18, 2, -14, 31, 3),
            Block.makeCuboidShape(-15, 15, 2, -14, 16, 3),
            Block.makeCuboidShape(30, 15, 2, 31, 16, 3),
            Block.makeCuboidShape(-16, 13, 2, 32, 15, 3),
            Block.makeCuboidShape(-16, 16, 2, 32, 18, 3),
            Block.makeCuboidShape(30, 18, 2, 31, 31, 3),
            Block.makeCuboidShape(-14, 9, -6, -13, 11, 11),
            Block.makeCuboidShape(-15, 10, 0, 31, 11, 1),
            Block.makeCuboidShape(29, 9, -6, 30, 11, 11),
            Block.makeCuboidShape(29, 27, 1, 30, 28, 8),
            Block.makeCuboidShape(29, 1, -14, 30, 3, 11),
            Block.makeCuboidShape(-10.96629, 10, -15.96629, 27.03371, 15, -15.46629),
            Block.makeCuboidShape(-11, 13.75, 11, 27, 15.5, 11.5),
            Block.makeCuboidShape(-11, 9.39268, -1.03369, 27, 9.89268, 10.46631),
            Block.makeCuboidShape(-10.96629, 15, -15.96629, 27.03371, 15.5, 11.03371)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private VoxelShape VOXEL_WEST = Stream.of(
            Block.makeCuboidShape(31.5, 10, -11, 32, 15, 27),
            Block.makeCuboidShape(4.53371, 13.75, -10.96629, 5.03371, 15.5, 27.03371),
            Block.makeCuboidShape(5.5674, 9.39268, -10.96629, 17.0674, 9.89268, 27.03371),
            Block.makeCuboidShape(5, 15, -11, 32, 15.5, 27),
            Block.makeCuboidShape(27.03371, 0, 30.03371, 30.03371, 28, 31.03371),
            Block.makeCuboidShape(5.03371, 0, 30.03371, 8.03371, 15, 31.03371),
            Block.makeCuboidShape(5.03371, 14, -13.96629, 6.03371, 15, 30.03371),
            Block.makeCuboidShape(1.03371, 0, 28.03371, 3.03371, 12, 29.03371),
            Block.makeCuboidShape(0.03371, 10, -12.96629, 4.03371, 11, 29.03371),
            Block.makeCuboidShape(2.03371, 5, -13.96629, 3.03371, 7, 30.03371),
            Block.makeCuboidShape(1.03371, 0, -12.96629, 3.03371, 12, -11.96629),
            Block.makeCuboidShape(1.03371, 0, 29.03371, 8.03371, 1, 30.03371),
            Block.makeCuboidShape(1.03371, 0, -13.96629, 8.03371, 1, -12.96629),
            Block.makeCuboidShape(14.03371, 3, 29.03371, 17.03371, 9, 30.03371),
            Block.makeCuboidShape(14.03371, 3, -13.96629, 17.03371, 9, -12.96629),
            Block.makeCuboidShape(5.03371, 0, -14.96629, 8.03371, 15, -13.96629),
            Block.makeCuboidShape(27.03371, 0, -14.96629, 30.03371, 28, -13.96629),
            Block.makeCuboidShape(30, 10, -15, 32, 16, -14),
            Block.makeCuboidShape(30.03371, 10, -13.96629, 31.53371, 12, 30.03371),
            Block.makeCuboidShape(30, 10, 30, 32, 16, 31),
            Block.makeCuboidShape(28, 4, -16, 29, 7, 32),
            Block.makeCuboidShape(22.03371, 27, -14.96629, 25.03371, 28, 31.03371),
            Block.makeCuboidShape(16.03371, 27, -12.96629, 19.03371, 28, 29.03371),
            Block.makeCuboidShape(16.03371, 19, 26.03371, 19.03371, 27, 27.03371),
            Block.makeCuboidShape(16.03371, 19, -10.96629, 19.03371, 27, -9.96629),
            Block.makeCuboidShape(16.03371, 18, -12.96629, 19.03371, 19, 29.03371),
            Block.makeCuboidShape(16.28371, 13, -9.96629, 18.78371, 18, 26.03371),
            Block.makeCuboidShape(16.03371, 12, -12.96629, 19.03371, 13, 29.03371),
            Block.makeCuboidShape(5.03371, 1, 29.03371, 30.03371, 3, 30.03371),
            Block.makeCuboidShape(20.03371, 1, 28.03371, 22.03371, 28, 29.03371),
            Block.makeCuboidShape(20.03371, 1, -12.96629, 22.03371, 28, -11.96629),
            Block.makeCuboidShape(15.03371, 26, 29.03371, 30.03371, 28, 30.03371),
            Block.makeCuboidShape(15.03371, 26, -13.96629, 30.03371, 28, -12.96629),
            Block.makeCuboidShape(8.03371, 27, 29.03371, 15.03371, 28, 30.03371),
            Block.makeCuboidShape(13.03371, 18, 30.03371, 14.03371, 31, 31.03371),
            Block.makeCuboidShape(13.03371, 15, 30.03371, 14.03371, 16, 31.03371),
            Block.makeCuboidShape(13.03371, 15, -14.96629, 14.03371, 16, -13.96629),
            Block.makeCuboidShape(13, 13, -16, 14, 15, 32),
            Block.makeCuboidShape(13, 16, -16, 14, 18, 32),
            Block.makeCuboidShape(13.03371, 18, -14.96629, 14.03371, 31, -13.96629),
            Block.makeCuboidShape(5.03371, 9, 29.03371, 22.03371, 11, 30.03371),
            Block.makeCuboidShape(15.03371, 10, -14.96629, 16.03371, 11, 31.03371),
            Block.makeCuboidShape(5.03371, 9, -13.96629, 22.03371, 11, -12.96629),
            Block.makeCuboidShape(8.03371, 27, -13.96629, 15.03371, 28, -12.96629),
            Block.makeCuboidShape(5.03371, 1, -13.96629, 30.03371, 3, -12.96629)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
}
