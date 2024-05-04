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
import net.minecraftforge.event.TickEvent;
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
public class CustomSphereRenderer {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final ResourceLocation UV_GRID = new ResourceLocation(ParticleTest.MODID, "textures/vfx/red.png");

    private static Vec3 renderPos = new Vec3(0, -57, 0); // Initial render position
    private static Vec3 targetPos = new Vec3(0, -57, 0); // Target position
    private static Vec3 triggerPos = new Vec3(1, -60, -17); // Position to trigger animation
    private static final double TRIGGER_RADIUS = 10.0; // Radius around the trigger position to trigger the animation
    private static int tickCounter = 0;
    private static final int ANIMATION_DURATION = 100; // Duration of the animation in ticks
    private static boolean isAnimating = false;

    @SubscribeEvent
    public static void renderLevelStageEvent(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) return;
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null) {
            RenderLevelStageEvent event1 = event;
            Camera camera = event1.getCamera();
            Vec3 cameraPos = camera.getPosition();

            float radius = 4.0f;

            // Calculate the bounding box of the sphere
            AABB sphereBounds = new AABB(renderPos.x - radius, renderPos.y - radius, renderPos.z - radius,
                    renderPos.x + radius, renderPos.y + radius, renderPos.z + radius);

            // Check if the player's position is within the bounding box
         //   if (sphereBounds.contains(player.position())) {
                PoseStack poseStack = event1.getPoseStack();
                poseStack.pushPose();
                Vec3 position = renderPos.subtract(cameraPos);
                poseStack.translate(position.x, position.y, position.z);
                VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
                VertexConsumer vertexConsumer = DELAYED_RENDER.getBuffer(TEXTURE.applyWithModifier(UV_GRID, b -> b.replaceVertexFormat(TRIANGLES).setCullState(NO_CULL).setTransparencyState(StateShards.ADDITIVE_TRANSPARENCY)));
                builder.renderSphere(vertexConsumer, poseStack, radius, 40, 40); // Increased subdivisions
                poseStack.popPose();
            //}
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            tickCounter++;

            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                double distanceSq = player.position().distanceToSqr(triggerPos);
                if (distanceSq <= TRIGGER_RADIUS * TRIGGER_RADIUS) {
                    if (!isAnimating) {
                        // Start animation if not already animating
                        isAnimating = true;
                        // Change the target position to (0, -57, 0)
                        targetPos = new Vec3(0, -57, 0);
                    }
                } else {
                    isAnimating = false;
                }
            }

            if (isAnimating) {
                // Calculate interpolation factor (0 to 1) based on animation progress
                float t = Math.min((float) tickCounter / ANIMATION_DURATION, 1.0f);
                // Interpolate between current and target positions
                renderPos = new Vec3(
                        interpolate(renderPos.x, targetPos.x, t),
                        interpolate(renderPos.y, targetPos.y, t),
                        interpolate(renderPos.z, targetPos.z, t)
                );

                if (tickCounter >= ANIMATION_DURATION) {
                    // End animation when duration is reached
                    isAnimating = false;
                    tickCounter = 0;
                }
            }
        }
    }

    // Interpolate between two values
    private static double interpolate(double start, double end, float t) {
        return start * (1 - t) + end * t;
    }
}
