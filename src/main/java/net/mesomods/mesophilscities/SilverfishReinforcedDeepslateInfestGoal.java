package net.mesomods.mesophilscities;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.EnumSet;

import net.mesomods.mesophilscities.init.MesophilsCitiesModBlocks;
import net.minecraft.client.renderer.entity.SilverfishRenderer;

public class SilverfishReinforcedDeepslateInfestGoal extends Goal {
    private final Silverfish silverfish;

    public SilverfishReinforcedDeepslateInfestGoal(Silverfish silverfish) {
        this.silverfish = silverfish;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!silverfish.getNavigation().isDone()) return false;

        Level level = silverfish.level();
        BlockPos below = silverfish.blockPosition().below();
        BlockState state = level.getBlockState(below);

        return state.is(Blocks.REINFORCED_DEEPSLATE); 
    }

    @Override
    public void start() {
        Level level = silverfish.level();
        BlockPos below = silverfish.blockPosition().below();
        BlockState state = level.getBlockState(below);

        if (state.is(Blocks.REINFORCED_DEEPSLATE)) {
            level.setBlock(below, MesophilsCitiesModBlocks.INFESTED_REINFORCED_DEEPSLATE.get().defaultBlockState(), 3);
            silverfish.spawnAnim();
            silverfish.discard();
        }
    }
}
