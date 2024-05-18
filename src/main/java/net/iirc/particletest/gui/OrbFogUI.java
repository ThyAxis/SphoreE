package net.iirc.particletest.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import static team.lodestar.lodestone.systems.client.ClientTickCounter.partialTicks;

public class OrbFogUI {
    public static final IGuiOverlay OVERLAY = OrbFogUI::renderOverlay;
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final LocalPlayer player = minecraft.player;

    private static final ResourceLocation ORB_TEXTURE = new ResourceLocation("textures/gui/ionizer.png");

    public static void renderOverlay(ForgeGui gui, GuiGraphics guiGraphics, float partialTicks, int screenWidth, int screenHeight) {
        Player player = minecraft.player;
        if (player == null) return;

        guiGraphics.fill(0, 0, screenWidth, screenHeight, 0xFF555555);
        guiGraphics.blit(ORB_TEXTURE, 20, 20, 0, 0, screenWidth, screenHeight);
    }
}
