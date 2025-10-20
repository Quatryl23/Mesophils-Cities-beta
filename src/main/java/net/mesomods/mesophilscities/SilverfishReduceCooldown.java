package net.mesomods.mesophilscities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "mesophils_cities")
public class SilverfishReduceCooldown{
@SubscribeEvent
public static void onSilverfishTick(net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent event) {
    if (event.getEntity() instanceof Silverfish silverfish) {
        CompoundTag tag = silverfish.getPersistentData();
        int cooldown = tag.getInt("BreakoutCooldown");
        if (cooldown > 0) {
            tag.putInt("BreakoutCooldown", cooldown - 1);
        }
    }
}
}
