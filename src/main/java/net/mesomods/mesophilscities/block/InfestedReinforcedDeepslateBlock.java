
package net.mesomods.mesophilscities.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.PushReaction;




public class InfestedReinforcedDeepslateBlock extends Block {
	public InfestedReinforcedDeepslateBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(27.5f, 150f));
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	@Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity be, ItemStack tool) {
    super.playerDestroy(level, player, pos, state, be, tool);

    if (!level.isClientSide && !EnchantmentHelper.hasSilkTouch(tool)) {
        Silverfish silverfish = EntityType.SILVERFISH.create(level);
        if (silverfish != null) {
            silverfish.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0F, 0.0F);
            level.addFreshEntity(silverfish);
            silverfish.spawnAnim();
            }
        }
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
    return PushReaction.BLOCK;
    }



    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

    level.destroyBlock(pos, false);
    Block.dropResources(state, level, pos, null, null, ItemStack.EMPTY);
    


    if (level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
        Silverfish silverfish = EntityType.SILVERFISH.create(level);
        if (silverfish != null) {
            silverfish.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0F, 0.0F);
            level.addFreshEntity(silverfish);
        }
    }
}



	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 0;
	}
}

