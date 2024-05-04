package net.iirc.particletest.visualeffects;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.iirc.particletest.ParticleTest;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.lodestar.lodestone.systems.rendering.StateShards;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import static com.mojang.blaze3d.vertex.VertexFormat.Mode.TRIANGLES;
import static team.lodestar.lodestone.handlers.RenderHandler.DELAYED_RENDER;
import static team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry.*;

@Mod.EventBusSubscriber(modid = ParticleTest.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CustomSphereTwo {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final ResourceLocation UV_GRID = new ResourceLocation(ParticleTest.MODID, "textures/vfx/blue.png");

    @SubscribeEvent
    public static void renderLevelStageEvent(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) return;
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null) {
            Camera camera = event.getCamera();
            Vec3 cameraPos = camera.getPosition();
            Vec3 renderPos = new Vec3(0, -57, 5);

            float radius = 4.0f;

            // Calculate the bounding box of the sphere
            AABB sphereBounds = new AABB(renderPos.x - radius, renderPos.y - radius, renderPos.z - radius,
                    renderPos.x + radius, renderPos.y + radius, renderPos.z + radius);


            // Check if the player's position is within the bounding box
            if (sphereBounds.contains(player.position())) {

            }
            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();
            Vec3 position = renderPos.subtract(cameraPos);
            poseStack.translate(position.x, position.y, position.z);
            VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
            VertexConsumer vertexConsumer = DELAYED_RENDER.getBuffer(TEXTURE.applyWithModifier(UV_GRID, b -> b.replaceVertexFormat(TRIANGLES).setCullState(NO_CULL).setTransparencyState(StateShards.ADDITIVE_TRANSPARENCY)));
            builder.renderSphere(vertexConsumer, poseStack, radius, 40, 40); // Increased subdivisions
            poseStack.popPose();
        }
    }
}
