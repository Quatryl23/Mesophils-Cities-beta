package net.mesomods.mesophilscities;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.mesomods.mesophilscities.block.entity.TemporaryGlassBlockEntity;
import net.mesomods.mesophilscities.init.MesophilsCitiesModBlockEntities;

@Mod.EventBusSubscriber(modid = "mesophils_cities", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        BlockEntityType<TemporaryGlassBlockEntity> type =
            (BlockEntityType<TemporaryGlassBlockEntity>) MesophilsCitiesModBlockEntities.TEMPORARY_GLASS.get();

        event.registerBlockEntityRenderer(
            type,
            context -> new TemporaryGlassBlockRenderer(context)
        );
    }
}