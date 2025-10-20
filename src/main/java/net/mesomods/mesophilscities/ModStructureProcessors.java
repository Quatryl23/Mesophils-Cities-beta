package net.mesomods.mesophilscities;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public class ModStructureProcessors {
    public static final DeferredRegister<StructureProcessorType<?>> PROCESSOR_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, "mesophils_cities");

    public static final RegistryObject<StructureProcessorType<SpecialRotationProcessor>> SPECIAL_ROTATION_PROCESSOR =
            PROCESSOR_TYPES.register("special_rotation", () -> () -> SpecialRotationProcessor.CODEC);

    public static final RegistryObject<StructureProcessorType<UnprotectedBlockProcessor>> UNPROTECTED_BLOCK_PROCESSOR =
            PROCESSOR_TYPES.register("unprotected_blocks", () -> () -> UnprotectedBlockProcessor.CODEC);

    public static final RegistryObject<StructureProcessorType<BlockVariationProcessor>> BLOCK_VARIATION_PROCESSOR =
            PROCESSOR_TYPES.register("block_variation", () -> () -> BlockVariationProcessor.CODEC);

    public static final RegistryObject<StructureProcessorType<InstantLootTableProcessor>> INSTANT_LOOT_TABLE_PROCESSOR =
            PROCESSOR_TYPES.register("instant_loot", () -> () -> InstantLootTableProcessor.CODEC);

    public static final RegistryObject<StructureProcessorType<KeepExistingBlocksProcessor>> KEEP_EXISTING_BLOCKS_PROCESSOR =
            PROCESSOR_TYPES.register("keep_existing_blocks", () -> () -> KeepExistingBlocksProcessor.CODEC);

    public static final RegistryObject<StructureProcessorType<RoomConnectProcessor>> ROOM_CONNECT_PROCESSOR =
            PROCESSOR_TYPES.register("room_connect", () -> () -> RoomConnectProcessor.CODEC);

    public static final RegistryObject<StructureProcessorType<ElectricityMergeProcessor>> ELECTRICITY_MERGE_PROCESSOR =
            PROCESSOR_TYPES.register("electricity_merge", () -> () -> ElectricityMergeProcessor.CODEC);


    public static void register(IEventBus eventBus) {
        PROCESSOR_TYPES.register(eventBus);
    }
}
