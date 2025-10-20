package net.mesomods.mesophilscities;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.mesomods.mesophilscities.SilverfishReinforcedDeepslateInfestGoal;

@Mod.EventBusSubscriber(modid = "mesophils_cities") 
public class SilverfishAddInfestReinforcedDeepslateGoal {

    @SubscribeEvent
    public static void onSilverfishJoinWorld(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Silverfish silverfish) {
            silverfish.goalSelector.addGoal(5, new SilverfishReinforcedDeepslateInfestGoal(silverfish));

        }
}
    }