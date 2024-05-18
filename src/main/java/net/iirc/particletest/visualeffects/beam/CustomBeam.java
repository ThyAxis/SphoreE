package net.iirc.particletest.visualeffects.beam;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.iirc.particletest.ParticleTest;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.joml.Matrix4f;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import static com.mojang.blaze3d.vertex.VertexFormat.Mode.TRIANGLES;
import static team.lodestar.lodestone.handlers.RenderHandler.DELAYED_RENDER;
import static team.lodestar.lodestone.handlers.ScreenshakeHandler.intensity;
import static team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry.NO_CULL;
import static team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry.TEXTURE;

public class CustomBeam {
    public static final ResourceLocation UV_GRID = new ResourceLocation(ParticleTest.MODID, "textures/vfx/black.png");

    @SubscribeEvent
    public static void renderLevelStageEvent(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) return;
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null) {
            Camera camera = event.getCamera();
            Vec3 cameraPos = camera.getPosition();
            Vec3 renderPos = new Vec3(20, -59, 19);
            PoseStack poseStack = event.getPoseStack();

            Vec3 position = renderPos.subtract(cameraPos);
            poseStack.translate(position.x, position.y, position.z);
            float radius = 4.0f;


                VertexConsumer consumer = RenderHandler.DELAYED_RENDER.getBuffer(TEXTURE.applyWithModifier(UV_GRID, b -> b.replaceVertexFormat(TRIANGLES)));

                float distance = 0.35f + Easing.SINE_OUT.ease(intensity / 60f, 0, 0.35f, 1);
                float alpha = intensity / 60f;

                VFXBuilders.createWorld()
                        .renderBeam(consumer, poseStack.last().pose(), renderPos, renderPos, 10);


            poseStack.pushPose();

        }
    }
}
