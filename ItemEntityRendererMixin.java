package com.itemhighlight.mixin;

import com.itemhighlight.ItemHighlightMod;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntityRenderer.class)
public class ItemEntityRendererMixin {

    /**
     * Inject right before the item is rendered on the ground.
     * We push a scaled matrix so the item appears larger for configured items.
     */
    @Inject(
        method = "render(Lnet/minecraft/entity/ItemEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
        at = @At("HEAD")
    )
    private void onRenderHead(ItemEntity entity, float yaw, float tickDelta,
                               MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                               int light, CallbackInfo ci) {

        ItemStack stack = entity.getStack();
        if (stack.isEmpty()) return;

        // Get the item's registry ID (e.g. "minecraft:enchanted_golden_apple")
        Identifier id = Registries.ITEM.getId(stack.getItem());
        if (id == null) return;

        float scale = ItemHighlightMod.CONFIG.getScale(id.toString());
        if (scale != 1.0f) {
            // Push matrix and scale uniformly around the item's center
            matrices.push();
            matrices.scale(scale, scale, scale);
        }
    }

    /**
     * After the render call finishes, pop the matrix if we pushed one.
     */
    @Inject(
        method = "render(Lnet/minecraft/entity/ItemEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
        at = @At("TAIL")
    )
    private void onRenderTail(ItemEntity entity, float yaw, float tickDelta,
                               MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                               int light, CallbackInfo ci) {

        ItemStack stack = entity.getStack();
        if (stack.isEmpty()) return;

        Identifier id = Registries.ITEM.getId(stack.getItem());
        if (id == null) return;

        float scale = ItemHighlightMod.CONFIG.getScale(id.toString());
        if (scale != 1.0f) {
            matrices.pop();
        }
    }
}
