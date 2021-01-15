package net.ugudango.luum.blocks.floor_loom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class FloorLoom extends Block {

    private VoxelShape VOXEL_NORTH = Stream.of(
            Block.makeCuboidShape(30, 0, 4.9, 31, 15, 7.9),
            Block.makeCuboidShape(-14, 14, 4.9, 30, 15, 5.9),
            Block.makeCuboidShape(28, 0, 0.9, 29, 12, 2.9),
            Block.makeCuboidShape(-13, 10, -0.1, 29, 11, 3.9),
            Block.makeCuboidShape(-14, 5, 1.9, 30, 7, 2.9),
            Block.makeCuboidShape(-13, 0, 0.9, -12, 12, 2.9),
            Block.makeCuboidShape(29, 0, 0.9, 30, 1, 7.9),
            Block.makeCuboidShape(-14, 0, 0.9, -13, 1, 7.9),
            Block.makeCuboidShape(29, 3, 13.9, 30, 9, 16.9),
            Block.makeCuboidShape(-14, 3, 13.9, -13, 9, 16.9),
            Block.makeCuboidShape(-15, 0, 4.9, -14, 15, 7.9),
            Block.makeCuboidShape(-15, 0, 26.9, -14, 28, 29.9),
            Block.makeCuboidShape(-15, 10, 29.9, -14, 16, 31.9),
            Block.makeCuboidShape(-14, 10, 29.9, 30, 12, 31.4),
            Block.makeCuboidShape(30, 10, 29.9, 31, 16, 31.9),
            Block.makeCuboidShape(-16, 4, 27.9, 32, 7, 28.9),
            Block.makeCuboidShape(-15, 27, 21.9, 31, 27.75, 24.9),
            Block.makeCuboidShape(-13, 27, 15.96742, 29, 28, 18.96742),
            Block.makeCuboidShape(26, 19, 15.96742, 27, 27, 18.96742),
            Block.makeCuboidShape(-11, 19, 15.96742, -10, 27, 18.96742),
            Block.makeCuboidShape(-13, 18, 15.9, 29, 19, 18.9),
            Block.makeCuboidShape(-10, 13, 16.275, 26, 18, 18.775),
            Block.makeCuboidShape(-13, 12, 15.9, 29, 13, 18.9),
            Block.makeCuboidShape(29, 1, 4.9, 30, 3, 29.9),
            Block.makeCuboidShape(28, 1, 19.9, 29, 28, 21.9),
            Block.makeCuboidShape(-13, 1, 19.9, -12, 28, 21.9),
            Block.makeCuboidShape(29, 26, 14.9, 30, 28, 29.9),
            Block.makeCuboidShape(-14, 26, 14.9, -13, 28, 29.9),
            Block.makeCuboidShape(29, 27, 7.9, 30, 28, 14.9),
            Block.makeCuboidShape(30, 18, 12.9, 31, 31, 13.9),
            Block.makeCuboidShape(30, 15, 12.9, 31, 16, 13.9),
            Block.makeCuboidShape(-15, 15, 12.9, -14, 16, 13.9),
            Block.makeCuboidShape(-16, 13, 12.96742, 32, 15, 13.96742),
            Block.makeCuboidShape(-16, 16, 12.9, 32, 18, 13.9),
            Block.makeCuboidShape(-15, 18, 12.9, -14, 31, 13.9),
            Block.makeCuboidShape(29, 9, 4.9, 30, 11, 21.9),
            Block.makeCuboidShape(-15, 10, 14.9, 31, 10.9, 15.9),
            Block.makeCuboidShape(-14, 9, 4.9, -13, 11, 21.9),
            Block.makeCuboidShape(-14, 27, 7.9, -13, 28, 14.9),
            Block.makeCuboidShape(-14, 1, 4.9, -13, 3, 29.9),
            Block.makeCuboidShape(-11, 10, 31.4, 27, 15, 31.9),
            Block.makeCuboidShape(-11, 13.75, 4.4, 27, 15.5, 4.9),
            Block.makeCuboidShape(-11, 15, 4.9, 27, 15.5, 31.9),
            Block.makeCuboidShape(30, 0, 26.9, 31, 28, 29.9)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    private VoxelShape VOXEL_EAST = Stream.of(
            Block.makeCuboidShape(8.1, 0, 30, 11.1, 15, 31),
            Block.makeCuboidShape(10.1, 14, -14, 11.1, 15, 30),
            Block.makeCuboidShape(13.1, 0, 28, 15.1, 12, 29),
            Block.makeCuboidShape(12.1, 10, -13, 16.1, 11, 29),
            Block.makeCuboidShape(13.1, 5, -14, 14.1, 7, 30),
            Block.makeCuboidShape(13.1, 0, -13, 15.1, 12, -12),
            Block.makeCuboidShape(8.1, 0, 29, 15.1, 1, 30),
            Block.makeCuboidShape(8.1, 0, -14, 15.1, 1, -13),
            Block.makeCuboidShape(-0.9, 3, 29, 2.1, 9, 30),
            Block.makeCuboidShape(-0.9, 3, -14, 2.1, 9, -13),
            Block.makeCuboidShape(8.1, 0, -15, 11.1, 15, -14),
            Block.makeCuboidShape(-13.9, 0, -15, -10.9, 28, -14),
            Block.makeCuboidShape(-15.9, 10, -15, -13.9, 16, -14),
            Block.makeCuboidShape(-15.4, 10, -14, -13.9, 12, 30),
            Block.makeCuboidShape(-15.9, 10, 30, -13.9, 16, 31),
            Block.makeCuboidShape(-12.9, 4, -16, -11.9, 7, 32),
            Block.makeCuboidShape(-8.9, 27, -15, -5.9, 27.75, 31),
            Block.makeCuboidShape(-2.96742, 27, -13, 0.03258, 28, 29),
            Block.makeCuboidShape(-2.96742, 19, 26, 0.03258, 27, 27),
            Block.makeCuboidShape(-2.96742, 19, -11, 0.03258, 27, -10),
            Block.makeCuboidShape(-2.9, 18, -13, 0.1, 19, 29),
            Block.makeCuboidShape(-2.775, 13, -10, -0.275, 18, 26),
            Block.makeCuboidShape(-2.9, 12, -13, 0.1, 13, 29),
            Block.makeCuboidShape(-13.9, 1, 29, 11.1, 3, 30),
            Block.makeCuboidShape(-5.9, 1, 28, -3.9, 28, 29),
            Block.makeCuboidShape(-5.9, 1, -13, -3.9, 28, -12),
            Block.makeCuboidShape(-13.9, 26, 29, 1.1, 28, 30),
            Block.makeCuboidShape(-13.9, 26, -14, 1.1, 28, -13),
            Block.makeCuboidShape(1.1, 27, 29, 8.1, 28, 30),
            Block.makeCuboidShape(2.1, 18, 30, 3.1, 31, 31),
            Block.makeCuboidShape(2.1, 15, 30, 3.1, 16, 31),
            Block.makeCuboidShape(2.1, 15, -15, 3.1, 16, -14),
            Block.makeCuboidShape(2.03258, 13, -16, 3.03258, 15, 32),
            Block.makeCuboidShape(2.1, 16, -16, 3.1, 18, 32),
            Block.makeCuboidShape(2.1, 18, -15, 3.1, 31, -14),
            Block.makeCuboidShape(-5.9, 9, 29, 11.1, 11, 30),
            Block.makeCuboidShape(0.1, 10, -15, 1.1, 10.9, 31),
            Block.makeCuboidShape(-5.9, 9, -14, 11.1, 11, -13),
            Block.makeCuboidShape(1.1, 27, -14, 8.1, 28, -13),
            Block.makeCuboidShape(-13.9, 1, -14, 11.1, 3, -13),
            Block.makeCuboidShape(-15.9, 10, -11, -15.4, 15, 27),
            Block.makeCuboidShape(11.1, 13.75, -11, 11.6, 15.5, 27),
            Block.makeCuboidShape(-15.9, 15, -11, 11.1, 15.5, 27),
            Block.makeCuboidShape(-13.9, 0, 30, -10.9, 28, 31)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    private VoxelShape VOXEL_SOUTH = Stream.of(
            Block.makeCuboidShape(-15, 0, 8.1, -14, 15, 11.1),
            Block.makeCuboidShape(-14, 14, 10.1, 30, 15, 11.1),
            Block.makeCuboidShape(-13, 0, 13.1, -12, 12, 15.1),
            Block.makeCuboidShape(-13, 10, 12.1, 29, 11, 16.1),
            Block.makeCuboidShape(-14, 5, 13.1, 30, 7, 14.1),
            Block.makeCuboidShape(28, 0, 13.1, 29, 12, 15.1),
            Block.makeCuboidShape(-14, 0, 8.1, -13, 1, 15.1),
            Block.makeCuboidShape(29, 0, 8.1, 30, 1, 15.1),
            Block.makeCuboidShape(-14, 3, -0.9, -13, 9, 2.1),
            Block.makeCuboidShape(29, 3, -0.9, 30, 9, 2.1),
            Block.makeCuboidShape(30, 0, 8.1, 31, 15, 11.1),
            Block.makeCuboidShape(30, 0, -13.9, 31, 28, -10.9),
            Block.makeCuboidShape(30, 10, -15.9, 31, 16, -13.9),
            Block.makeCuboidShape(-14, 10, -15.4, 30, 12, -13.9),
            Block.makeCuboidShape(-15, 10, -15.9, -14, 16, -13.9),
            Block.makeCuboidShape(-16, 4, -12.9, 32, 7, -11.9),
            Block.makeCuboidShape(-15, 27, -8.9, 31, 27.75, -5.9),
            Block.makeCuboidShape(-13, 27, -2.96742, 29, 28, 0.03258),
            Block.makeCuboidShape(-11, 19, -2.96742, -10, 27, 0.03258),
            Block.makeCuboidShape(26, 19, -2.96742, 27, 27, 0.03258),
            Block.makeCuboidShape(-13, 18, -2.9, 29, 19, 0.1),
            Block.makeCuboidShape(-10, 13, -2.775, 26, 18, -0.275),
            Block.makeCuboidShape(-13, 12, -2.9, 29, 13, 0.1),
            Block.makeCuboidShape(-14, 1, -13.9, -13, 3, 11.1),
            Block.makeCuboidShape(-13, 1, -5.9, -12, 28, -3.9),
            Block.makeCuboidShape(28, 1, -5.9, 29, 28, -3.9),
            Block.makeCuboidShape(-14, 26, -13.9, -13, 28, 1.1),
            Block.makeCuboidShape(29, 26, -13.9, 30, 28, 1.1),
            Block.makeCuboidShape(-14, 27, 1.1, -13, 28, 8.1),
            Block.makeCuboidShape(-15, 18, 2.1, -14, 31, 3.1),
            Block.makeCuboidShape(-15, 15, 2.1, -14, 16, 3.1),
            Block.makeCuboidShape(30, 15, 2.1, 31, 16, 3.1),
            Block.makeCuboidShape(-16, 13, 2.03258, 32, 15, 3.03258),
            Block.makeCuboidShape(-16, 16, 2.1, 32, 18, 3.1),
            Block.makeCuboidShape(30, 18, 2.1, 31, 31, 3.1),
            Block.makeCuboidShape(-14, 9, -5.9, -13, 11, 11.1),
            Block.makeCuboidShape(-15, 10, 0.1, 31, 10.9, 1.1),
            Block.makeCuboidShape(29, 9, -5.9, 30, 11, 11.1),
            Block.makeCuboidShape(29, 27, 1.1, 30, 28, 8.1),
            Block.makeCuboidShape(29, 1, -13.9, 30, 3, 11.1),
            Block.makeCuboidShape(-11, 10, -15.9, 27, 15, -15.4),
            Block.makeCuboidShape(-11, 13.75, 11.1, 27, 15.5, 11.6),
            Block.makeCuboidShape(-11, 15, -15.9, 27, 15.5, 11.1),
            Block.makeCuboidShape(-15, 0, -13.9, -14, 28, -10.9)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    private VoxelShape VOXEL_WEST = Stream.of(
            Block.makeCuboidShape(4.9, 0, -15, 7.9, 15, -14),
            Block.makeCuboidShape(4.9, 14, -14, 5.9, 15, 30),
            Block.makeCuboidShape(0.9, 0, -13, 2.9, 12, -12),
            Block.makeCuboidShape(-0.1, 10, -13, 3.9, 11, 29),
            Block.makeCuboidShape(1.9, 5, -14, 2.9, 7, 30),
            Block.makeCuboidShape(0.9, 0, 28, 2.9, 12, 29),
            Block.makeCuboidShape(0.9, 0, -14, 7.9, 1, -13),
            Block.makeCuboidShape(0.9, 0, 29, 7.9, 1, 30),
            Block.makeCuboidShape(13.9, 3, -14, 16.9, 9, -13),
            Block.makeCuboidShape(13.9, 3, 29, 16.9, 9, 30),
            Block.makeCuboidShape(4.9, 0, 30, 7.9, 15, 31),
            Block.makeCuboidShape(26.9, 0, 30, 29.9, 28, 31),
            Block.makeCuboidShape(29.9, 10, 30, 31.9, 16, 31),
            Block.makeCuboidShape(29.9, 10, -14, 31.4, 12, 30),
            Block.makeCuboidShape(29.9, 10, -15, 31.9, 16, -14),
            Block.makeCuboidShape(27.9, 4, -16, 28.9, 7, 32),
            Block.makeCuboidShape(21.9, 27, -15, 24.9, 27.75, 31),
            Block.makeCuboidShape(15.96742, 27, -13, 18.96742, 28, 29),
            Block.makeCuboidShape(15.96742, 19, -11, 18.96742, 27, -10),
            Block.makeCuboidShape(15.96742, 19, 26, 18.96742, 27, 27),
            Block.makeCuboidShape(15.9, 18, -13, 18.9, 19, 29),
            Block.makeCuboidShape(16.275, 13, -10, 18.775, 18, 26),
            Block.makeCuboidShape(15.9, 12, -13, 18.9, 13, 29),
            Block.makeCuboidShape(4.9, 1, -14, 29.9, 3, -13),
            Block.makeCuboidShape(19.9, 1, -13, 21.9, 28, -12),
            Block.makeCuboidShape(19.9, 1, 28, 21.9, 28, 29),
            Block.makeCuboidShape(14.9, 26, -14, 29.9, 28, -13),
            Block.makeCuboidShape(14.9, 26, 29, 29.9, 28, 30),
            Block.makeCuboidShape(7.9, 27, -14, 14.9, 28, -13),
            Block.makeCuboidShape(12.9, 18, -15, 13.9, 31, -14),
            Block.makeCuboidShape(12.9, 15, -15, 13.9, 16, -14),
            Block.makeCuboidShape(12.9, 15, 30, 13.9, 16, 31),
            Block.makeCuboidShape(12.96742, 13, -16, 13.96742, 15, 32),
            Block.makeCuboidShape(12.9, 16, -16, 13.9, 18, 32),
            Block.makeCuboidShape(12.9, 18, 30, 13.9, 31, 31),
            Block.makeCuboidShape(4.9, 9, -14, 21.9, 11, -13),
            Block.makeCuboidShape(14.9, 10, -15, 15.9, 10.9, 31),
            Block.makeCuboidShape(4.9, 9, 29, 21.9, 11, 30),
            Block.makeCuboidShape(7.9, 27, 29, 14.9, 28, 30),
            Block.makeCuboidShape(4.9, 1, 29, 29.9, 3, 30),
            Block.makeCuboidShape(31.4, 10, -11, 31.9, 15, 27),
            Block.makeCuboidShape(4.4, 13.75, -11, 4.9, 15.5, 27),
            Block.makeCuboidShape(4.9, 15, -11, 31.9, 15.5, 27),
            Block.makeCuboidShape(26.9, 0, -15, 29.9, 28, -14)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

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

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HorizontalBlock.HORIZONTAL_FACING);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof FloorLoomTile) {
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("screen.luum.floor_loom");
                    }

                    @Nullable
                    @Override
                    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
                        return new FloorLoomContainer(p_createMenu_1_, worldIn, pos, p_createMenu_2_, p_createMenu_3_);
                    }
                };
                NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing");
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new FloorLoomTile();
    }
}
