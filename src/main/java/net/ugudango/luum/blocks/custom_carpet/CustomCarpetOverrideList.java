package net.ugudango.luum.blocks.custom_carpet;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nullable;

public class CustomCarpetOverrideList extends ItemOverrideList {
    public CustomCarpetOverrideList() {
        super();
    }

    @Nullable
    @Override
    public IBakedModel getOverrideModel(IBakedModel model, ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity livingEntity) {
        try {
            CompoundNBT tag = stack.getTag().getCompound("BlockEntityTag");
            int[] colors = tag.getIntArray("colors");
            return new CustomCarpetBakedModel(colors);
        } catch (Exception e) {
            return new CustomCarpetBakedModel(null);
        }
    }
}
