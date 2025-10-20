package net.mesomods.mesophilscities.block.entity;

import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.mesomods.mesophilscities.init.MesophilsCitiesModBlockEntities;
import net.mesomods.mesophilscities.block.TechnicalTickerBlock;

public class TechnicalTickerBlockEntity extends BlockEntity {
	public static final ResourceKey<Level> CITY = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("mesophils_cities", "city"));

	public TechnicalTickerBlockEntity(BlockPos position, BlockState state) {
		super(MesophilsCitiesModBlockEntities.TECHNICAL_TICKER.get(), position, state);
	}

	@Override
	public void onLoad() {
		if (!level.isClientSide() && level.dimension().equals(CITY)) {
			level.scheduleTick(worldPosition, getBlockState().getBlock(), 1);
		}
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
}
