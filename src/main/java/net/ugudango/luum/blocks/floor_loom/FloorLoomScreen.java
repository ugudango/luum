package net.ugudango.luum.blocks.floor_loom;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.ugudango.luum.Luum;

import java.text.DecimalFormat;

public class FloorLoomScreen extends ContainerScreen<FloorLoomContainer> {
    private final ResourceLocation GUI = new ResourceLocation(Luum.MOD_ID, "textures/gui/floor_loom.png");

    public FloorLoomScreen(FloorLoomContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        assert this.minecraft != null;

        this.minecraft.getTextureManager().bindTexture(GUI);

        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) /2;

        this.blit(matrixStack, relX, relY, 0, 0, this.width, this.height);
    }
}
