package net.mesomods.mesophilscities;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.Minecraft;

import com.mojang.blaze3d.vertex.PoseStack;

//currenty unused as item previews are disabled
public class LootTableWandRenderer extends BlockEntityWithoutLevelRenderer {
	public LootTableWandRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
		super(dispatcher, modelSet);
	}

	@Override
	public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource bufferSource, int i1, int i2) {
		super.renderByItem(stack, context, poseStack, bufferSource, i1, i2);
		if (stack.getOrCreateTag().contains("PreviewItems")) {
			CompoundTag itemNbt = stack.getOrCreateTag();
			String previewItemString = itemNbt.getList("PreviewItems", 8).get(0).getAsString();
			ItemStack previewItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(previewItemString)).getDefaultInstance();
			if (previewItem == null)
				return;
			poseStack.pushPose();
			float offset = 0.65f;
			poseStack.translate(offset, -offset, -0.1f);
			float scale = 0.3f;
			poseStack.scale(scale, scale, scale);
			Minecraft.getInstance().getItemRenderer().renderStatic(previewItem, context, i1, i2, poseStack, bufferSource, null, i1);
			poseStack.popPose();
		}
	}
}
