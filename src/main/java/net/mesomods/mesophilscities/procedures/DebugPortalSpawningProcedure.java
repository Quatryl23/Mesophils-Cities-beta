package net.mesomods.mesophilscities.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.IntTag;

public class DebugPortalSpawningProcedure {
	public static void execute(LevelAccessor world, double x, double z, Entity entity) {
		if (entity == null)
			return;
		ListTag xd;
		xd = CheckForPortalHouse240Procedure.execute(world, x, z);
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(
					Component.literal(("x1: " + ((xd.get(0)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) + "\n" + "z1: " + ((xd.get(1)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) + "\n" + "x2: "
							+ (((xd.get(2)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) < 2 ? ((xd.get(0)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) + 1 : ((xd.get(0)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0)) + "\n"
							+ "z2: " + (((xd.get(2)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) < 2 ? ((xd.get(1)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) : ((xd.get(1)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) + 1))),
					false);
		if ((entity instanceof LivingEntity _entity) ? _entity.isHolding(Blocks.STONE.asItem()) : false) {
			{
				Entity _ent = entity;
				_ent.teleportTo((((xd.get(2)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) < 2 ? ((xd.get(0)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) + 0.5 : ((xd.get(0)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0)), 100,
						(((xd.get(2)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) < 2 ? ((xd.get(1)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) : ((xd.get(1)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) + 0.5));
				if (_ent instanceof ServerPlayer _serverPlayer)
					_serverPlayer.connection.teleport(
							(((xd.get(2)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) < 2 ? ((xd.get(0)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) + 0.5 : ((xd.get(0)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0)), 100,
							(((xd.get(2)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) < 2 ? ((xd.get(1)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) : ((xd.get(1)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) + 0.5),
							_ent.getYRot(), _ent.getXRot());
			}
		}
	}
}
