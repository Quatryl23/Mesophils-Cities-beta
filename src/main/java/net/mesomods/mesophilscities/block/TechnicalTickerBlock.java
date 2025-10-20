
package net.mesomods.mesophilscities.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.MenuProvider;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mesomods.mesophilscities.block.entity.TechnicalTickerBlockEntity;
//import net.mesomods.mesophilscities.NodeFixManager;
import net.mesomods.mesophilscities.LoadedModsCache;
import org.apache.logging.log4j.LogManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;

public class TechnicalTickerBlock extends Block implements EntityBlock {
	public static final BooleanProperty LOADED = BooleanProperty.create("loaded");
	public static final DirectionProperty FACING = BlockStateProperties.FACING;

	public TechnicalTickerBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.EMPTY).strength(-1, 3600000).noCollission().noOcclusion().pushReaction(PushReaction.BLOCK).isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(LOADED, false).setValue(FACING, Direction.DOWN));
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LOADED);
		builder.add(FACING);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return state.getValue(LOADED) ? RenderShape.MODEL : RenderShape.INVISIBLE;
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		super.tick(state, level, pos, random);
		this.tickTargetBlock(state, level, pos);
		
	}

	public void tickTargetBlock(BlockState blockState, ServerLevel level, BlockPos worldPosition) {
		if (!blockState.hasProperty(BlockStateProperties.FACING))
			return;
		BlockPos tickingPos = worldPosition.relative(blockState.getValue(BlockStateProperties.FACING));
		BlockState tickingBlock = level.getBlockState(tickingPos);
		BlockEntity blockEntity = level.getBlockEntity(tickingPos);
		if (blockEntity instanceof ChiseledBookShelfBlockEntity bookShelf) {
			// Workaround because bookShelf.updateState(1) has private access
			bookShelf.setItem(0, bookShelf.getItem(0));
		} else if (LoadedModsCache.REFURBISHED_FURNITURE) {
			//not used:  if (!NodeFixManager.reconnectNodeIfPresent(blockEntity, worldPosition, level))
			level.scheduleTick(tickingPos, tickingBlock.getBlock(), 1);
		} else {
			level.scheduleTick(tickingPos, tickingBlock.getBlock(), 1);
		}
		level.setBlock(worldPosition, Blocks.AIR.defaultBlockState(), 3);
	}


	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getClickedFace().getOpposite()).setValue(LOADED, true);
	}

	


	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TechnicalTickerBlockEntity(pos, state);
	}

	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity == null ? false : blockEntity.triggerEvent(eventID, eventParam);
	}
}
