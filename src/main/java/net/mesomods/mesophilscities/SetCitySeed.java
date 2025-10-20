package net.mesomods.mesophilscities; 

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraft.server.level.ServerLevel;


import net.mesomods.mesophilscities.network.MesophilsCitiesModVariables;

@Mod.EventBusSubscriber
public class SetCitySeed {

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            var mapVars = MesophilsCitiesModVariables.MapVariables.get(serverLevel);
            if (mapVars.city_seed == 0) {
                mapVars.city_seed = serverLevel.getSeed();
                mapVars.syncData(serverLevel);
                System.out.println("[MesophilsCities] Seed initialized : " + serverLevel.getSeed());
            }
        }
    }
}
