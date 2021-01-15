package net.ugudango.luum.blocks.custom_carpet;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.item.DyeColor;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;
import net.ugudango.luum.Luum;
import net.ugudango.luum.utils.LuumVector3D;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CustomCarpetBakedModel implements IDynamicBakedModel {
    public static final List<ResourceLocation> TEXTURES = new ArrayList<ResourceLocation>() {
        {
            add(new ResourceLocation(Luum.MOD_ID, "p0"));
            add(new ResourceLocation(Luum.MOD_ID, "p1"));
            add(new ResourceLocation(Luum.MOD_ID, "p2"));
            add(new ResourceLocation(Luum.MOD_ID, "p3"));
            add(new ResourceLocation(Luum.MOD_ID, "p4"));
            add(new ResourceLocation(Luum.MOD_ID, "p5"));
            add(new ResourceLocation(Luum.MOD_ID, "p6"));
            add(new ResourceLocation(Luum.MOD_ID, "p7"));
            add(new ResourceLocation(Luum.MOD_ID, "p8"));
            add(new ResourceLocation(Luum.MOD_ID, "p9"));
            add(new ResourceLocation(Luum.MOD_ID, "p10"));
            add(new ResourceLocation(Luum.MOD_ID, "p11"));
            add(new ResourceLocation(Luum.MOD_ID, "p12"));
            add(new ResourceLocation(Luum.MOD_ID, "p13"));
            add(new ResourceLocation(Luum.MOD_ID, "p14"));
            add(new ResourceLocation(Luum.MOD_ID, "p15"));
        }
    };
    private int[] ITEM_COLORS;

    public CustomCarpetBakedModel(int[] colors) {
        ITEM_COLORS = colors;
    }

    public CustomCarpetBakedModel() {
        super();
    }

    private static Vector3d v(double x, double y, double z) {
        return new Vector3d(x, y, z);
    }

    @Override
    public boolean isSideLit() {
        return false;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {

        RenderType layer = MinecraftForgeClient.getRenderLayer();

        if (side != null || (layer != null && !layer.equals(RenderType.getSolid()))) {
            return Collections.emptyList();
        }

        LuumVector3D.R rotation = LuumVector3D.R.DEG0;

        BlockState carpetBlockState = state;
        Direction dir;
        try {
            dir = carpetBlockState.get(HorizontalBlock.HORIZONTAL_FACING);
        } catch (Exception e) {
            dir = Direction.NORTH;
        }


        switch (dir) {
            case NORTH:
                rotation = LuumVector3D.R.DEG180;
                break;
            case EAST:
                rotation = LuumVector3D.R.DEG90;
                break;
            case WEST:
                rotation = LuumVector3D.R.DEG270;
                break;
        }

        int[] colors = extraData.getData(CustomCarpetTile.COLORS);
        List<TextureAtlasSprite> textures = new ArrayList<TextureAtlasSprite>();
        List<BakedQuad> quads = new ArrayList<>();

        final double carpetHeight = 1.0d / 16.0d;
        final int increment = 8;
        final int numberOfQuads = 256 / (increment * increment);
        Vector3d currentUpLeft = v (0, 1, 0);

        if (colors != null) {
            for (int colorId : colors) {
                textures.add(getTexture(colorId));
            }
        } else if (ITEM_COLORS != null){
            for (int colorId : ITEM_COLORS) {
                textures.add(getTexture(colorId));
            }
        } else {
            for (int i = 0; i < numberOfQuads; i++) {
                textures.add(getTexture(DyeColor.WHITE));
            }
        }

        for (int currentQuad = 0; currentQuad < numberOfQuads; currentQuad++ ) {
            int i_tlx = (currentQuad * increment) % 16;
            int i_tlz = ((currentQuad * increment) / 16) * increment;
            TextureAtlasSprite currentTexture = textures.get(currentQuad);

            /**
             * Top facets.
             */
            LuumVector3D tl = new LuumVector3D(
                    (double) i_tlx / 16.0d,
                    carpetHeight,
                    (double) i_tlz / 16.0d
            ).rotateAxisY(rotation);
            LuumVector3D tr = new LuumVector3D(
                    (double) (i_tlx + increment) / 16.0d,
                    carpetHeight,
                    (double) i_tlz / 16.0d
            ).rotateAxisY(rotation);;
            LuumVector3D bl = new LuumVector3D(
                    (double) i_tlx / 16.0d,
                    carpetHeight,
                    (double) (i_tlz + increment) / 16.0d
            ).rotateAxisY(rotation);;
            LuumVector3D br = new LuumVector3D(
                    (double) (i_tlx + increment) / 16.0d,
                    carpetHeight,
                    (double) (i_tlz + increment) / 16.0d
            ).rotateAxisY(rotation);
            /**
             * Bottom facets.
             */
            LuumVector3D tlb = tl.add(new LuumVector3D(0.0d, - (1.0d/16.0d), 0.0d));
            LuumVector3D trb = tr.add(new LuumVector3D(0.0d, - (1.0d/16.0d), 0.0d));
            LuumVector3D blb = bl.add(new LuumVector3D(0.0d, - (1.0d/16.0d), 0.0d));
            LuumVector3D brb = br.add(new LuumVector3D(0.0d, - (1.0d/16.0d), 0.0d));
            /**
             * Lateral facets.
             */
            // Top-render check
            if (i_tlz == 0) {
                quads.add(createQuad(tr, trb, tlb, tl, currentTexture));
            }
            // Down-render check
            if (i_tlz == 16 - increment) {
                quads.add(createQuad(bl, blb, brb, br, currentTexture));
            }
            // Left-render check
            if (i_tlx == 0) {
                quads.add(createQuad(tl, tlb, blb, bl, currentTexture));
            }
            // Right-render check
            if (i_tlx == 16 - increment) {
                quads.add(createQuad(br, brb, trb, tr, currentTexture));
            }
            quads.add(createQuad(tl, bl, br, tr, currentTexture));
            quads.add(createQuad(tlb, trb, brb, blb, currentTexture));
        }

        return quads;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return getTexture(1);
    }

    @Override
    public ItemOverrideList getOverrides() {
        return new CustomCarpetOverrideList();
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return getAllTransforms();
    }

    public ItemCameraTransforms getAllTransforms() {
        ItemTransformVec3f tpLeft = this.getTransform(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND);
        ItemTransformVec3f tpRight = this.getTransform(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
        ItemTransformVec3f fpLeft = this.getTransform(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND);
        ItemTransformVec3f fpRight = this.getTransform(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND);
        ItemTransformVec3f head = this.getTransform(ItemCameraTransforms.TransformType.HEAD);
        ItemTransformVec3f gui = this.getTransform(ItemCameraTransforms.TransformType.GUI);
        ItemTransformVec3f ground = this.getTransform(ItemCameraTransforms.TransformType.GROUND);
        ItemTransformVec3f fixed = this.getTransform(ItemCameraTransforms.TransformType.FIXED);
        return new ItemCameraTransforms(tpLeft, tpRight, fpLeft, fpRight, head, gui, ground, fixed);
    }

    /**
     * Use the DyeColor enum provided by net.minecraft
     * @param color
     * @return TextureAtlasSprite
     */
    private TextureAtlasSprite getTexture(DyeColor color) {
        int numericId = color.getId();
        numericId = 15 - Math.abs(numericId % 16);
        return Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(TEXTURES.get(numericId));
    }

    private TextureAtlasSprite getTexture(int numericId) {
        numericId = 15 - Math.abs(numericId % 16);
        return Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(TEXTURES.get(numericId));
    }

    private void putVertex(BakedQuadBuilder builder, Vector3d normal,
                           double x, double y, double z, float u, float v, TextureAtlasSprite sprite, float r, float g, float b) {

        ImmutableList<VertexFormatElement> elements = builder.getVertexFormat().getElements().asList();
        for (int j = 0 ; j < elements.size() ; j++) {
            VertexFormatElement e = elements.get(j);
            switch (e.getUsage()) {
                case POSITION:
                    builder.put(j, (float) x, (float) y, (float) z, 1.0f);
                    break;
                case COLOR:
                    builder.put(j, r, g, b, 1.0f);
                    break;
                case UV:
                    switch (e.getIndex()) {
                        case 0:
                            float iu = sprite.getInterpolatedU(u);
                            float iv = sprite.getInterpolatedV(v);
                            builder.put(j, iu, iv);
                            break;
                        case 2:
                            builder.put(j, (short) 0, (short) 0);
                            break;
                        default:
                            builder.put(j);
                            break;
                    }
                    break;
                case NORMAL:
                    builder.put(j, (float) normal.x, (float) normal.y, (float) normal.z);
                    break;
                default:
                    builder.put(j);
                    break;
            }
        }
    }

    private BakedQuad createQuad(Vector3d v1, Vector3d v2, Vector3d v3, Vector3d v4, TextureAtlasSprite sprite) {
        Vector3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();
        int tw = sprite.getWidth();
        int th = sprite.getHeight();

        BakedQuadBuilder builder = new BakedQuadBuilder(sprite);
        builder.setQuadOrientation(Direction.getFacingFromVector(normal.x, normal.y, normal.z));
        putVertex(builder, normal, v1.x, v1.y, v1.z, 0, 0, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v2.x, v2.y, v2.z, 0, th, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v3.x, v3.y, v3.z, tw, th, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v4.x, v4.y, v4.z, tw, 0, sprite, 1.0f, 1.0f, 1.0f);
        return builder.build();
    }

    private ItemTransformVec3f getTransform(ItemCameraTransforms.TransformType type) {
        if (type.equals(ItemCameraTransforms.TransformType.GUI)) {
            return new ItemTransformVec3f(new Vector3f(200, 50, 100), new Vector3f(), new Vector3f(1.0F, 1.0F, 1.0F));
        }
        return ItemTransformVec3f.DEFAULT;
    }
}
