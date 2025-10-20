package net.mesomods.mesophilscities;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.mesomods.mesophilscities.block.CityPortalBlock;
import net.mesomods.mesophilscities.procedures.TrySpawningPortalProcedure;

@Mod.EventBusSubscriber
public class CityPortalIgnite {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        ItemStack itemstack = event.getItemStack();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState clickedState = level.getBlockState(pos);
        Block clickedBlock = clickedState.getBlock();
        event.setCanceled(false);

        if (itemstack.getItem() == Items.CLOCK) {
            if (clickedState.is(BlockTags.create(new ResourceLocation("mesophils_cities", "city_portal_allowed_frame_blocks")))) {

                BlockPos adjacentPos = pos.relative(event.getFace());

                if (level.isEmptyBlock(adjacentPos)) {
                    if (!player.mayUseItemAt(adjacentPos, event.getFace(), itemstack)) {
                        return;
                    }
                    if (TrySpawningPortalProcedure.execute(level, adjacentPos.getX(),adjacentPos.getY(),adjacentPos.getZ() )) {
                    if (!player.isCreative()) itemstack.shrink(1);
                    event.setCanceled(true);
                    event.setCancellationResult(InteractionResult.SUCCESS);
                }         
                
            }
                }
             
            }
        }
    }

