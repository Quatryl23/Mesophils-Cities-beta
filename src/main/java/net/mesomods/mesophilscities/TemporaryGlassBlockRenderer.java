package net.mesomods.mesophilscities;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.Minecraft;
import net.minecraft.ChatFormatting;

import net.mesomods.mesophilscities.block.entity.TemporaryGlassBlockEntity;
import net.mesomods.mesophilscities.block.TemporaryGlassBlock;
import net.mesomods.mesophilscities.world.dimension.CityDimension;


import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class TemporaryGlassBlockRenderer implements BlockEntityRenderer<TemporaryGlassBlockEntity> {
	public TemporaryGlassBlockRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(TemporaryGlassBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
		BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
		// Sync persistent NBT to fields
		be.updateTextureIdsFromPersistentData();
		// Convert block ID strings into blocks
		Block windowBlock = BuiltInRegistries.BLOCK.get(new ResourceLocation(be.windowBlockId));
		Block wallBlock = BuiltInRegistries.BLOCK.get(new ResourceLocation(be.wallBlockId));
		Direction facing = be.getBlockState().getValue(TemporaryGlassBlock.FACING);
		Direction facing2 = be.getBlockState().getValue(TemporaryGlassBlock.SECONDARY_FACING);

		boolean north = false;
		boolean south = false;
		boolean east = false;
		boolean west = false;
		if (windowBlock == Blocks.AIR) {
			windowBlock = BuiltInRegistries.BLOCK.get(new ResourceLocation("minecraft", "glass"));
		} ;

		BlockState windowState = windowBlock.defaultBlockState();
		BlockState wallState = wallBlock.defaultBlockState();

		if (windowState.hasProperty(BlockStateProperties.NORTH) && windowState.hasProperty(BlockStateProperties.SOUTH) && windowState.hasProperty(BlockStateProperties.WEST) && windowState.hasProperty(BlockStateProperties.EAST)) {
			if (facing == Direction.NORTH || facing == Direction.SOUTH) {
				windowState = windowState.setValue(BlockStateProperties.WEST, true);
				windowState = windowState.setValue(BlockStateProperties.EAST, true);
			} else {
				windowState = windowState.setValue(BlockStateProperties.NORTH, true);
				windowState = windowState.setValue(BlockStateProperties.SOUTH, true);
			}
		}
		if (wallState.hasProperty(BlockStateProperties.NORTH) && wallState.hasProperty(BlockStateProperties.SOUTH) && wallState.hasProperty(BlockStateProperties.WEST) && wallState.hasProperty(BlockStateProperties.EAST)) {
			if (facing == Direction.NORTH || facing == Direction.SOUTH) {
				wallState = wallState.setValue(BlockStateProperties.WEST, true);
				wallState = wallState.setValue(BlockStateProperties.EAST, true);
			} else {
				wallState = wallState.setValue(BlockStateProperties.NORTH, true);
				wallState = wallState.setValue(BlockStateProperties.SOUTH, true);
			}
		}
		
		if (be.getLevel() == null || be.getLevel().dimension().equals(CityDimension.KEY)) {
			blockRenderer.renderSingleBlock(wallState, poseStack, buffer, packedLight, packedOverlay);
			return;
		}
			
		
		poseStack.pushPose();
		try {
			// Render full-size window block centered
			poseStack.pushPose();
			poseStack.translate(0.0, 0.0, 0.0); // Already centered
			blockRenderer.renderSingleBlock(windowState, poseStack, buffer, packedLight, packedOverlay);
			poseStack.popPose();
			// Render small wall block centered
			poseStack.pushPose();
			poseStack.translate(0.5, 0.5, 0.5); // Move to center of block
			poseStack.scale(0.25f, 0.25f, 0.25f); // Shrink it down
			poseStack.translate(-0.5, 0.0, -0.5); // Re-center the model's origin
			blockRenderer.renderSingleBlock(wallState, poseStack, buffer, packedLight, packedOverlay);
			poseStack.popPose();
		} finally {
			poseStack.popPose();
		};
		
			poseStack.translate(0.5, 0.5, 0.5);
			List<Direction> outsideDirections = new ArrayList();
			outsideDirections.add(facing);
			if (facing != facing2) 
				outsideDirections.add(facing2);
				ListTag messages = new ListTag();
				messages.add(StringTag.valueOf(Component.Serializer.toJson(Component.empty())));
				messages.add(StringTag.valueOf(Component.Serializer.toJson(Component.literal("OUTSIDE").withStyle(style -> style.withColor(ChatFormatting.RED)))));
				messages.add(StringTag.valueOf(Component.Serializer.toJson(Component.empty())));
				messages.add(StringTag.valueOf(Component.Serializer.toJson(Component.empty())));
				CompoundTag frontTextTag = new CompoundTag();
				frontTextTag.put("messages", messages);
				frontTextTag.putString("color", "red");
				frontTextTag.putBoolean("has_glowing_text", true);
				CompoundTag tag = new CompoundTag();
				tag.put("front_text", frontTextTag);
				tag.putString("id", "minecraft:sign");
				tag.putInt("x", 0);
				tag.putInt("y", 0);
				tag.putInt("z", 0);
				boolean secondaryRender = false;
			for (Direction dir: outsideDirections) {
				poseStack.pushPose();
				try {
				BlockState signState = Blocks.OAK_WALL_SIGN.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, dir);
				SignBlockEntity fakeSign = new SignBlockEntity(BlockPos.ZERO, signState);
				fakeSign.setLevel(Minecraft.getInstance().level);
				fakeSign.load(tag);
				BlockEntityRenderer<SignBlockEntity> signRenderer = Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(fakeSign);
				float offset = 0.23f;
				if (secondaryRender) {
					poseStack.translate(-0.15, -0.15, -0.15);
					
				} else {
					poseStack.translate(-0.25, -0.25, -0.25);
				}
				
				switch (dir) {
					case NORTH -> poseStack.translate(0.0, 0.0, -offset - 0.5);
					case SOUTH -> poseStack.translate(0.0, 0.0, offset + 0.5);
					case WEST -> poseStack.translate(-offset - 0.5, 0.0, 0.0);
					case EAST -> poseStack.translate(offset + 0.5, 0.0, 0.0);
				}
				if (secondaryRender) {
					poseStack.scale(0.3f, 0.3f, 0.3f);
				} else {
					poseStack.scale(0.5f, 0.5f, 0.5f);
				}
				
				if (signRenderer != null) {
					signRenderer.render(fakeSign, partialTick, poseStack, buffer, packedLight, packedOverlay);
				}
			} finally {
				poseStack.popPose();
			}
			secondaryRender = true;
		}	
	}
}
