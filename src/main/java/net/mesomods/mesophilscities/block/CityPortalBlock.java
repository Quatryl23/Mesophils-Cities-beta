
package net.mesomods.mesophilscities.block;

import org.checkerframework.checker.units.qual.s;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.mesomods.mesophilscities.init.MesophilsCitiesModBlockEntities;
import net.mesomods.mesophilscities.init.MesophilsCitiesModParticleTypes;


import net.mesomods.mesophilscities.CityPortalBlockEntity;
import net.mesomods.mesophilscities.world.dimension.CityDimension;
import net.mesomods.mesophilscities.world.teleporter.CityTeleporter;
import net.mesomods.mesophilscities.world.teleporter.CityPortalShape;

import java.util.Optional;
import net.minecraft.world.level.block.EntityBlock;

public class CityPortalBlock extends NetherPortalBlock implements EntityBlock {
	public CityPortalBlock() {
		super(BlockBehaviour.Properties.of().noCollission().randomTicks().pushReaction(PushReaction.BLOCK).strength(-1.0F).sound(SoundType.GLASS).lightLevel(s -> 10).noLootTable());
	}

	@Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return MesophilsCitiesModBlockEntities.CITY_PORTAL.get().create(pos, state);
    }


	@Override
	public void randomTick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
	}

	public static void portalSpawn(Level world, BlockPos pos) {
		Optional<CityPortalShape> optional = CityPortalShape.findEmptyPortalShape(world, pos, Direction.Axis.X);
		if (optional.isPresent()) {
			optional.get().createPortalBlocks();
		}
	}

	public static void portalSpawn(Level world, BlockPos pos, int rotation) {
        Direction.Axis axis = Direction.Axis.X;
		if (rotation < 2) {axis = Direction.Axis.X;} else {axis = Direction.Axis.Z;}
		Optional<CityPortalShape> optional = CityPortalShape.findEmptyPortalShape(world, pos, axis);
		if (optional.isPresent()) {
			optional.get().createPortalBlocks();
		}
	}



	@Override
	public BlockState updateShape(BlockState p_54928_, Direction p_54929_, BlockState p_54930_, LevelAccessor p_54931_, BlockPos p_54932_, BlockPos p_54933_) {
		Direction.Axis direction$axis = p_54929_.getAxis();
		Direction.Axis direction$axis1 = p_54928_.getValue(AXIS);
		boolean flag = direction$axis1 != direction$axis && direction$axis.isHorizontal();
		return !flag && !p_54930_.is(this) && !(new CityPortalShape(p_54931_, p_54932_, direction$axis1)).isComplete() ? Blocks.AIR.defaultBlockState() : p_54928_;//super.updateShape(p_54928_, p_54929_, p_54930_, p_54931_, p_54932_, p_54933_);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
		for (int i = 0; i < 4; i++) {
			double px = pos.getX() + random.nextFloat();
			double py = pos.getY() + random.nextFloat();
			double pz = pos.getZ() + random.nextFloat();
			double vx = (random.nextFloat() - 0.5) / 2.;
			double vy = (random.nextFloat() - 0.5) / 2.;
			double vz = (random.nextFloat() - 0.5) / 2.;
			int j = random.nextInt(4) - 1;
			if (world.getBlockState(pos.west()).getBlock() != this && world.getBlockState(pos.east()).getBlock() != this) {
				px = pos.getX() + 0.5 + 0.25 * j;
				vx = random.nextFloat() * 2 * j;
			} else {
				pz = pos.getZ() + 0.5 + 0.25 * j;
				vz = random.nextFloat() * 2 * j;
			}
			world.addParticle((MesophilsCitiesModParticleTypes.CITY_PORTAL.get()), px, py, pz, vx, vy, vz);

		}
		if (random.nextInt(110) == 0)
			world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(("block.portal.ambient"))), SoundSource.BLOCKS, 0.5f, random.nextFloat() * 0.4f + 0.8f);
	}

	@Override
	public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
	if (entity.canChangeDimensions() && !entity.level().isClientSide()) {
		if (entity.isOnPortalCooldown()) {
			entity.setPortalCooldown();
		} else if (entity.level().dimension() == Level.OVERWORLD) {
				teleportToDimension(entity, pos, ResourceKey.create(Registries.DIMENSION, new ResourceLocation("mesophils_cities:city")));
				entity.setPortalCooldown();
			} else if (entity.level().dimension() == CityDimension.KEY) {
				teleportToDimension(entity, pos, Level.OVERWORLD);
				entity.setPortalCooldown();
			}

		/*if (entity.canChangeDimensions() && !entity.level().isClientSide()) {
			if (!entity.isOnPortalCooldown()) {
				entity.setPortalCooldown(entity.getDimensionChangingDelay() + 5);
			} else if ((entity.getPortalCooldown() < 5) && entity.level().dimension() == Level.OVERWORLD) {
				teleportToDimension(entity, pos, ResourceKey.create(Registries.DIMENSION, new ResourceLocation("mesophils_cities:city")));
				entity.setPortalCooldown(0);
			} else if ((entity.getPortalCooldown() < 5) && entity.level().dimension() == CityDimension.KEY) {
				teleportToDimension(entity, pos, Level.OVERWORLD);
				entity.setPortalCooldown(0);
			}
		}*/
		}
	}

	private void teleportToDimension(Entity entity, BlockPos pos, ResourceKey<Level> destinationType) {
		entity.changeDimension(entity.getServer().getLevel(destinationType), new CityTeleporter(entity.getServer().getLevel(destinationType), pos));
	}
}
