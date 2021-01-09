package net.ugudango.luum.blocks;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.client.model.IModelLoader;

public class CustomCarpetModelLoader implements IModelLoader<CustomCarpetGeometry> {
    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    @Override
    public CustomCarpetGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
        return new CustomCarpetGeometry();
    }
}
