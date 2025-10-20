package net.mesomods.mesophilscities.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import net.mesomods.mesophilscities.init.MesophilsCitiesModBlocks;

public class SilverfishBottleRightclickedOnBlockProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Direction direction, Entity entity, ItemStack itemstack) {
		if (direction == null || entity == null)
			return InteractionResult.PASS;
		BlockState infest = Blocks.AIR.defaultBlockState();
		double spawnx = 0;
		double spawny = 0;
		double spawnz = 0;
		if (blockstate.is(BlockTags.create(new ResourceLocation("mesophils_cities:silverfish_bottle_infestable")))) {
			if (blockstate.getBlock() == Blocks.STONE) {
				infest = Blocks.INFESTED_STONE.defaultBlockState();
			} else if (blockstate.getBlock() == Blocks.COBBLESTONE) {
				infest = Blocks.INFESTED_COBBLESTONE.defaultBlockState();
			} else if (blockstate.getBlock() == Blocks.STONE_BRICKS) {
				infest = Blocks.INFESTED_STONE_BRICKS.defaultBlockState();
			} else if (blockstate.getBlock() == Blocks.CHISELED_STONE_BRICKS) {
				infest = Blocks.INFESTED_CHISELED_STONE_BRICKS.defaultBlockState();
			} else if (blockstate.getBlock() == Blocks.MOSSY_STONE_BRICKS) {
				infest = Blocks.INFESTED_MOSSY_STONE_BRICKS.defaultBlockState();
			} else if (blockstate.getBlock() == Blocks.DEEPSLATE) {
				infest = Blocks.INFESTED_DEEPSLATE.defaultBlockState();
			} else if (blockstate.getBlock() == Blocks.CRACKED_STONE_BRICKS) {
				infest = Blocks.INFESTED_CRACKED_STONE_BRICKS.defaultBlockState();
			} else if (blockstate.getBlock() == Blocks.REINFORCED_DEEPSLATE) {
				infest = MesophilsCitiesModBlocks.INFESTED_REINFORCED_DEEPSLATE.get().defaultBlockState();
			}
			if (!(infest.getBlock() == Blocks.AIR)) {
				BlockPos pos = new BlockPos((int) x, (int) y, (int) z);
				world.levelEvent(2001, pos, Block.getId(blockstate));
				world.setBlock(BlockPos.containing(x, y, z), infest, 3);
				if (entity instanceof ServerPlayer _player) {
					Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("mesophils_cities:destruction_for_beginners"));
					AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
					if (!_ap.isDone()) {
						for (String criteria : _ap.getRemainingCriteria())
							_player.getAdvancements().award(_adv, criteria);
					}
				}
			}
		} else {
			spawnx = x + ((direction == Direction.EAST) ? 1.5 : (direction == Direction.WEST) ? (-0.5) : 0.5);
			spawny = y + ((direction == Direction.UP) ? 1.5 : (direction == Direction.DOWN) ? (-0.5) : 0.5);
			spawnz = z + ((direction == Direction.SOUTH) ? 1.5 : (direction == Direction.NORTH) ? (-0.5) : 0.5);
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = EntityType.SILVERFISH.spawn(_level, BlockPos.containing(spawnx, spawny, spawnz), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot((float) (new Object() {
						public double get(Vec3 vec3) {
							return Math.toDegrees(Math.acos(vec3.multiply(1.0D, 0.0D, 1.0D).normalize().z())) * (vec3.x() >= 0.0D ? -1.0D : 1.0D);
						}
					}).get((new Vec3(direction.step()))));
					entityToSpawn.setYBodyRot((float) (new Object() {
						public double get(Vec3 vec3) {
							return Math.toDegrees(Math.acos(vec3.multiply(1.0D, 0.0D, 1.0D).normalize().z())) * (vec3.x() >= 0.0D ? -1.0D : 1.0D);
						}
					}).get((new Vec3(direction.step()))));
					entityToSpawn.setYHeadRot((float) (new Object() {
						public double get(Vec3 vec3) {
							return Math.toDegrees(Math.acos(vec3.multiply(1.0D, 0.0D, 1.0D).normalize().z())) * (vec3.x() >= 0.0D ? -1.0D : 1.0D);
						}
					}).get((new Vec3(direction.step()))));
					entityToSpawn.setXRot((float) (-Math.toDegrees(Math.asin((new Vec3(direction.step())).normalize().y()))));
				}
			}
		}
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.silverfish.ambient")), SoundSource.NEUTRAL, 1, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.silverfish.ambient")), SoundSource.NEUTRAL, 1, 1, false);
			}
		}
		if (entity instanceof Player _player) {
			ItemStack _setstack = new ItemStack(Items.GLASS_BOTTLE).copy();
			_setstack.setCount(1);
			ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
		}
		itemstack.shrink(1);
		return InteractionResult.CONSUME;
	}
}
