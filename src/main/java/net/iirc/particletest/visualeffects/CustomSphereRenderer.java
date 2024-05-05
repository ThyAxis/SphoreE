package net.iirc.particletest.visualeffects;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.iirc.particletest.ParticleTest;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.A;
import team.lodestar.lodestone.systems.rendering.StateShards;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import static com.mojang.blaze3d.vertex.VertexFormat.Mode.TRIANGLES;
import static team.lodestar.lodestone.handlers.RenderHandler.DELAYED_RENDER;
import static team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry.*;

@Mod.EventBusSubscriber(modid = ParticleTest.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CustomSphereRenderer {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final ResourceLocation UV_GRID = new ResourceLocation(ParticleTest.MODID, "textures/vfx/red.png");

    private static final Vec3 spherePos = new Vec3(0, -57, 0); // Initial render position
    private static final Vec3 targetPos = new Vec3(0, -57, 5); // Target End Position Of Animation
    private static final Vec3 triggerPos = new Vec3(-19, -60, 2); // Position to trigger animation
    private static final double TRIGGER_RADIUS = 10.0; // Radius around the trigger position to trigger the animation
    private static int tickCount = 0;
    private static final int ANIMATION_DURATION = 100; // Duration of the animation in ticks
    private static boolean insideRadius = false;
    private static boolean reverseAnimation = false;

    @SubscribeEvent
    public static void renderLevelStageEvent(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) return;
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null) {
            Camera camera = event.getCamera();
            Vec3 cameraPos = camera.getPosition();
            PoseStack poseStack = event.getPoseStack();

            poseStack.pushPose();

            Vec3 renderPos = new Vec3(0, 0, 0);
            // Calculate smooth interpolation for animation using partial ticks
            float partialTicks = event.getPartialTick();
            float t = (tickCount + partialTicks) / ANIMATION_DURATION;
            t = Math.min(t, 1.0f); // Ensure t does not exceed 1

            if (insideRadius) {
                if (!reverseAnimation) {
                    renderPos = interpolate(targetPos, spherePos, t); // Inside radius, move towards spherePos
                } else {
                    renderPos = interpolate(spherePos, targetPos, t); // Reverse animation, move towards targetPos
                }
            } else {
                if (!reverseAnimation) {
                    renderPos = interpolate(spherePos, targetPos, t); // Outside radius, move towards targetPos
                }


            }

            Vec3 position = renderPos.subtract(cameraPos);
            poseStack.translate(position.x, position.y, position.z);

            VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
            VertexConsumer vertexConsumer = DELAYED_RENDER.getBuffer(ADDITIVE_TEXTURE.applyWithModifier(UV_GRID, b -> b.replaceVertexFormat(TRIANGLES).setCullState(NO_CULL).setTransparencyState(StateShards.ADDITIVE_TRANSPARENCY)));
            builder.renderSphere(vertexConsumer, poseStack, 4.0f, 40, 40);
            poseStack.popPose();
        }
    }

    private static double interpolate(double start, double end, float t) {
        return start + (end - start) * t;
    }

    private static Vec3 interpolate(Vec3 a, Vec3 b, float t) {
        return a.add(b.subtract(a).multiply(new Vec3(t, t, t)));
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null) return;

        double distanceSq = player.position().distanceToSqr(triggerPos); // Radius Check
        if (distanceSq <= TRIGGER_RADIUS * TRIGGER_RADIUS && !insideRadius) {
            player.sendSystemMessage(Component.literal("Inside radius"));
            tickCount++;
            insideRadius = true;
        } else if (distanceSq > TRIGGER_RADIUS * TRIGGER_RADIUS && insideRadius) {
            player.sendSystemMessage(Component.literal("Outside radius"));
            tickCount++;
            insideRadius = false;
        }

        if (tickCount > ANIMATION_DURATION) {
            if (!reverseAnimation) {
                reverseAnimation = true;
                tickCount = 0;
            } else {
                player.sendSystemMessage(Component.literal("Animation Complete"));
                tickCount = 0;
                reverseAnimation = false;
            }
        }
    }
}
