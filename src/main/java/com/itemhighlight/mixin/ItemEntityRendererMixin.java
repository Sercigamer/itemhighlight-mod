package com.itemhighlight.mixin;

import com.itemhighlight.ItemHighlightMod;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntityRenderer.class)
public class ItemEntityRendererMixin {

    @Inject(
        method = "render(Lnet/minecraft/world/entity/item/ItemEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
        at = @At("HEAD")
    )
    private void onRenderHead(ItemEntity entity, float yaw, float tickDelta,
                               PoseStack poseStack, MultiBufferSource bufferSource,
                               int light, CallbackInfo ci) {
        try {
            ItemStack stack = entity.getItem();
            if (stack.isEmpty()) return;

            ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
            if (id == null) return;

            float scale = ItemHighlightMod.CONFIG.getScale(id.toString());
            if (scale != 1.0f) {
                poseStack.pushPose();
                poseStack.scale(scale, scale, scale);
            }
        } catch (Exception e) {
            // Safety catch - never crash the game
        }
    }

    @Inject(
        method = "render(Lnet/minecraft/world/entity/item/ItemEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
        at = @At("TAIL")
    )
    private void onRenderTail(ItemEntity entity, float yaw, float tickDelta,
                               PoseStack poseStack, MultiBufferSource bufferSource,
                               int light, CallbackInfo ci) {
        try {
            ItemStack stack = entity.getItem();
            if (stack.isEmpty()) return;

            ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
            if (id == null) return;

            float scale = ItemHighlightMod.CONFIG.getScale(id.toString());
            if (scale != 1.0f) {
                poseStack.popPose();
            }
        } catch (Exception e) {
            // Safety catch - never crash the game
        }
    }
}
