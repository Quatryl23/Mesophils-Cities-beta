package net.mesomods.mesophilscities;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mesomods.mesophilscities.CityTemplatePoolLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = "mesophils_cities", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CityTemplatePoolRegister {

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new CityTemplatePoolLoader());
    }
}

