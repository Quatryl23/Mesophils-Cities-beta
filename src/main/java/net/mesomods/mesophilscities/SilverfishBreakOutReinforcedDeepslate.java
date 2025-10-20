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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.mesomods.mesophilscities.init.MesophilsCitiesModBlocks;



@Mod.EventBusSubscriber(modid = "mesophils_cities")
public class SilverfishBreakOutReinforcedDeepslate {

    @SubscribeEvent
    public static void onSilverfishHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Silverfish silverfish)) return;

        Level level = silverfish.level();
        if (level.isClientSide) return;

        if (!level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) return;       

        DamageSource source = event.getSource();
        Entity attacker = source.getEntity();
        String damageTypeId = source.getMsgId();


        if (attacker instanceof Player player) {
            if (player.isCreative() || player.isSpectator()) return;
        } else if (!damageTypeId.equals("magic") &&
                   !damageTypeId.equals("indirectMagic") &&
                   !damageTypeId.equals("poison")) {
        return;
        }

        

        
        CompoundTag tag = silverfish.getPersistentData();
        int cooldown = tag.getInt("BreakoutCooldown");
        if (cooldown > 0) return;
        tag.putInt("BreakoutCooldown", 20);

        BlockPos origin = silverfish.blockPosition();
        int breakoutRadius = 10;
        int verticalBreakoutRadius = 5;

        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-breakoutRadius,-verticalBreakoutRadius, -breakoutRadius),
                                                    origin.offset(breakoutRadius,verticalBreakoutRadius, breakoutRadius))) {
            BlockState state = level.getBlockState(pos);
            if (state.is(MesophilsCitiesModBlocks.INFESTED_REINFORCED_DEEPSLATE.get())) {
               if (level.random.nextBoolean()) continue;
               level.scheduleTick(pos, state.getBlock(), level.random.nextInt(40));

            }
        }
    }
}
