package net.ugudango.luum.blocks;

import com.google.common.collect.ImmutableList;
import com.sun.javafx.geom.Vec3d;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.item.DyeColor;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;
import net.ugudango.luum.Luum;

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


    @Override
    public boolean isSideLit() {
        return false;
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

    private static Vector3d v(double x, double y, double z) {
        return new Vector3d(x, y, z);
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {

        RenderType layer = MinecraftForgeClient.getRenderLayer();



//        if (side != null || (layer != null && !layer.equals(RenderType.getSolid()))) {
//            return Collections.emptyList();
//        }

        int[] colors = extraData.getData(CustomCarpetTile.COLORS);

        List<TextureAtlasSprite> textures = new ArrayList<TextureAtlasSprite>();

        List<BakedQuad> quads = new ArrayList<>();
        final float carpetHeight = 1.0f / 16.0f;
        final int increment = 8;
        final int numberOfQuads = 256 / (increment * increment);
        Vector3d currentUpLeft = v (0, 1, 0);

        if (colors != null) {
            for (int colorId : colors) {
                textures.add(getTexture(colorId));

            }
        } else {
            for (int i = 0; i < numberOfQuads; i++) {
                textures.add(getTexture(DyeColor.LIME));
            }
        }

        /**
         * TODO: Add orientation. Redo the calculations for 1/16 for a pixel.
         */
        for (int currentQuad = 0; currentQuad < numberOfQuads; currentQuad++ ) {
            float topLeftX = (float) ((float) increment * ((float) (currentQuad % (16.0 / increment + 1.0)) /(float) (16.0 / increment)) / 16.0f);
            float topLeftZ = (float) (increment * (currentQuad % (16.0 / increment)) / 16.0f);
            Vector3d tl = v(topLeftX, carpetHeight, topLeftZ);
            Vector3d tr = v(topLeftX + increment / 16.0f, carpetHeight, topLeftZ);
            Vector3d bl = v(topLeftX, carpetHeight, topLeftZ + increment / 16.0f);
            Vector3d br = v(topLeftX + increment / 16.0f, carpetHeight, topLeftZ + increment / 16.0f);
            quads.add(createQuad(tl, bl, br, tr, textures.get(currentQuad)));
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
        return ItemOverrideList.EMPTY;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return ItemCameraTransforms.DEFAULT;
    }
}
