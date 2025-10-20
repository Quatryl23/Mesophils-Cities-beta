package net.mesomods.mesophilscities;

import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mesomods.mesophilscities.init.MesophilsCitiesModBlockEntities;
import net.mesomods.mesophilscities.block.CityPortalBlock;

import javax.annotation.Nullable;

import java.util.stream.IntStream;

public class CityPortalBlockEntity extends BlockEntity  {
	private BlockPos targetPos = BlockPos.ZERO;

@Override
public void saveAdditional(CompoundTag tag) {
    super.saveAdditional(tag);
    tag.putInt("TargetX", targetPos.getX());
    tag.putInt("TargetY", targetPos.getY());
    tag.putInt("TargetZ", targetPos.getZ());
}

@Override
public void load(CompoundTag tag) {
    super.load(tag);
    this.targetPos = new BlockPos(tag.getInt("TargetX"), tag.getInt("TargetY"), tag.getInt("TargetZ"));
}

public BlockPos getTargetPos() {
    return targetPos;
}

public void setTargetPos(BlockPos targetPos) {
    this.targetPos = targetPos;
    setChanged();
}

	

	public CityPortalBlockEntity(BlockPos position, BlockState state) {
		super(MesophilsCitiesModBlockEntities.CITY_PORTAL.get(), position, state);
	}


	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		return this.saveWithFullMetadata();
	}
}
