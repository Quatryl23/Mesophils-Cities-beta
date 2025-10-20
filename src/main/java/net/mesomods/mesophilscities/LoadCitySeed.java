package net.mesomods.mesophilscities;
import net.mesomods.mesophilscities.SeedCache;

import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.server.level.ServerLevel;
import net.mesomods.mesophilscities.network.MesophilsCitiesModVariables;

@Mod.EventBusSubscriber
public class LoadCitySeed {
@SubscribeEvent
public static void onWorldLoad(LevelEvent.Load event) {
    if (event.getLevel() instanceof ServerLevel serverLevel) {
        var mapVars = MesophilsCitiesModVariables.MapVariables.get(serverLevel);
        if (mapVars.city_seed == 0) {
            mapVars.city_seed = serverLevel.getSeed();
            mapVars.syncData(serverLevel);
        }
        SeedCache.CITY_SEED = (long) mapVars.city_seed;
    }
}
}