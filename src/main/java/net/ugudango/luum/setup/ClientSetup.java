package net.ugudango.luum.setup;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.ugudango.luum.Luum;
import net.ugudango.luum.blocks.custom_carpet.CustomCarpetModelLoader;
import net.ugudango.luum.blocks.floor_loom.FloorLoomScreen;

@Mod.EventBusSubscriber(modid = Luum.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            RenderTypeLookup.setRenderLayer(ModBlocks.CUSTOM_CARPET.get(), (RenderType) -> true);
        });
        event.enqueueWork(() -> {
            ScreenManager.registerFactory(ModContainers.FLOOR_LOOM_CONTAINER.get(), FloorLoomScreen::new);
        });
    }

    @SubscribeEvent
    public static void onModelRegistryEvent(ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(new ResourceLocation(Luum.MOD_ID, "customcarpetloader"), new CustomCarpetModelLoader());
    }
}
