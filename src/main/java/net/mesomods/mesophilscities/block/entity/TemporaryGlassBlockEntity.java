package net.mesomods.mesophilscities.block.entity;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.mesomods.mesophilscities.init.MesophilsCitiesModBlockEntities;
import net.mesomods.mesophilscities.init.MesophilsCitiesModBlocks;
import net.mesomods.mesophilscities.block.TemporaryGlassBlock;
import net.mesomods.mesophilscities.world.dimension.CityDimension;
import org.apache.logging.log4j.LogManager;

public class TemporaryGlassBlockEntity extends BlockEntity {
	public String windowBlockId = ""; //minecraft:glass_pane";
	public String wallBlockId = ""; //minecraft:black_terracotta";
	public static final ResourceKey<Level> CITY = CityDimension.KEY;

	public TemporaryGlassBlockEntity(BlockPos position, BlockState state) {
		super(MesophilsCitiesModBlockEntities.TEMPORARY_GLASS.get(), position, state);
	}

	@Override
	public void onLoad() {
		if (!level.isClientSide() && level.dimension().equals(CITY)) {
			level.scheduleTick(worldPosition, getBlockState().getBlock(), 1);
		}
		//update fenceY value
		Level world = level;
		BlockPos pos = worldPosition;
		BlockState blockstate = world.getBlockState(pos);
		int i;
		for (i=0; i<48; i++) {
			if (!world.getBlockState(pos.below(i+1)).is(MesophilsCitiesModBlocks.TEMPORARY_GLASS.get())) {
				break;
			}
		}
		for (int j=0; j<48; j++) {
			BlockState state = world.getBlockState(pos.above(j));
			if (state.is(MesophilsCitiesModBlocks.TEMPORARY_GLASS.get())) {
				if (!state.hasProperty(TemporaryGlassBlock.FENCE_Y)) continue;
				world.setBlock(pos.above(j), state.setValue(TemporaryGlassBlock.FENCE_Y, Math.min(i+j, 48)), 3);
			} else {
				break;
			}
		}
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		updateTextureIdsFromPersistentData();

	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		return this.saveWithFullMetadata();
	}

	public void updateTextureIdsFromPersistentData() {
		CompoundTag tag = this.getPersistentData();
		this.windowBlockId = tag.getString("window");
		this.wallBlockId = tag.getString("wall");
	}
}
