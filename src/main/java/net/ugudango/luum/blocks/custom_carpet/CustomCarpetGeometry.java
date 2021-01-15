package net.ugudango.luum.blocks.custom_carpet;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import java.util.*;
import java.util.function.Function;

public class CustomCarpetGeometry implements IModelGeometry<CustomCarpetGeometry> {
    @Override
    public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
        return new CustomCarpetBakedModel();
    }

    @Override
    public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
        List<RenderMaterial> textureCollection = new ArrayList<>();
        for (ResourceLocation rl : CustomCarpetBakedModel.TEXTURES) {
            System.out.println(rl);
            textureCollection.add(new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, rl));
        }
        return (Collection<RenderMaterial>) textureCollection;
    }
}
