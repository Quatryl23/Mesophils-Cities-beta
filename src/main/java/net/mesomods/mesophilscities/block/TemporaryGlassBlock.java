
package net.mesomods.mesophilscities.block;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mesomods.mesophilscities.init.MesophilsCitiesModBlocks;
import net.mesomods.mesophilscities.block.entity.TemporaryGlassBlockEntity;
import net.mesomods.mesophilscities.TemporaryGlassReplaceState;
import net.mesomods.mesophilscities.world.dimension.CityDimension;
import net.mesomods.mesophilscities.MesophilsCitiesModTags;

import java.util.List;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import java.util.ArrayList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.LevelAccessor;

public class TemporaryGlassBlock extends HorizontalDirectionalBlock implements EntityBlock {
	public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final EnumProperty<Direction> SECONDARY_FACING = DirectionProperty.create("secondary_facing", Direction.Plane.HORIZONTAL);
	public static final BooleanProperty IS_CORNER = BooleanProperty.create("is_corner");
	public static final BooleanProperty IS_FENCE = BooleanProperty.create("is_fence");
	public static final EnumProperty<TemporaryGlassReplaceState> REPLACE_STATE = EnumProperty.create("turns_into", TemporaryGlassReplaceState.class);
	public static final IntegerProperty FENCE_Y = IntegerProperty.create("fence_y_level", 0, 48);
	public static final TemporaryGlassReplaceState WALL_REPLACE = TemporaryGlassReplaceState.WALL;
	public static final ResourceKey<Level> CITY = CityDimension.KEY;


	public TemporaryGlassBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(SECONDARY_FACING, SECONDARY_FACING.getValue("north").get())
		.setValue(REPLACE_STATE, TemporaryGlassReplaceState.UNSET).setValue(IS_FENCE, true).setValue(IS_CORNER, false).setValue(FENCE_Y, 0));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, SECONDARY_FACING, REPLACE_STATE, IS_CORNER, IS_FENCE, FENCE_Y);
	}

	public enum BlockConnect {
		NONE,
		LEFT_RIGHT,
		LEFT_RIGHT_FRONT
	}

	@Override
	public void tick(BlockState blockstate, ServerLevel level, BlockPos pos, RandomSource random) {
		super.tick(blockstate, level, pos, random);
		final Direction facing = blockstate.getValue(FACING);
		final Direction facing2 = blockstate.getValue(SECONDARY_FACING);
		BlockState state;
		CompoundTag blockEntityData = level.getBlockEntity(pos).getPersistentData();
		final boolean isFence = blockstate.getValue(IS_FENCE);
		final boolean isCorner = blockstate.getValue(IS_CORNER);
		final int fenceY = blockstate.getValue(FENCE_Y);
		final TemporaryGlassReplaceState replaceState = blockstate.getValue(REPLACE_STATE);
		if (isCorner) {
			if (isFence) {
				if (fenceY > 0) {
					BlockPos checkPos = pos.below(fenceY);
					BlockState checkState = level.getBlockState(checkPos);
					if (checkState.is(MesophilsCitiesModBlocks.TEMPORARY_GLASS.get())) {
						int skippedBlocks1 = facing.getCounterClockWise() == facing2 ? 1 : facing.getClockWise() == facing2 ? 3 : 0;
						int skippedBlocks2 = facing.getCounterClockWise() == facing2 ? 3 : facing.getClockWise() == facing2 ? 1 : 0;
						if (fenceCheck(level, checkPos, facing, skippedBlocks1) || fenceCheck(level, checkPos, facing2, skippedBlocks2)) {
							transform(level, pos, facing, facing2, blockEntityData, true);
						} else {
							transform(level, pos, facing, null, blockEntityData, false);
						}
					} else if (checkState.is(Blocks.AIR)) {
						transform(level, pos, facing, null, blockEntityData, false);
					} else {
						transform(level, pos, facing, facing2, blockEntityData, true);
					}	
				} else if (replaceState == WALL_REPLACE) {
					transform(level, pos, facing, null, blockEntityData, false);
				} else {
					int skippedBlocks1 = facing.getCounterClockWise() == facing2 ? 1 : facing.getClockWise() == facing2 ? 3 : 0;
					int skippedBlocks2 = facing.getCounterClockWise() == facing2 ? 3 : facing.getClockWise() == facing2 ? 1 : 0;
					if (fenceCheck(level, pos, facing, skippedBlocks1) || fenceCheck(level, pos, facing2, skippedBlocks2)) {
						transform(level, pos, facing, facing2, blockEntityData, true);
					} else {
						transform(level, pos, facing, null, blockEntityData, false);
					}
				}
			} else {
				if (replaceState == WALL_REPLACE) {
					transform(level, pos, facing, null, blockEntityData, false);
				} else {
					if (windowCheck(level, pos, facing)) {
						transform(level, pos, facing, facing2, blockEntityData, true);
					} else {
						transform(level, pos, facing, null, blockEntityData, false);
					}
				}
				
			}
		} else {
			if (isFence) {
				if (fenceY > 0) {
					BlockPos checkPos = pos.below(fenceY);
					BlockState checkState = level.getBlockState(checkPos);
					if (checkState.is(MesophilsCitiesModBlocks.TEMPORARY_GLASS.get())) {
						if (fenceCheck(level, checkPos, facing)) {
							transform(level, pos, facing, null, blockEntityData, true);
						} else {
							transform(level, pos, facing, null, blockEntityData, false);
						}
					} else if (checkState.is(Blocks.AIR)) {
						transform(level, pos, facing, null, blockEntityData, false);
					} else {
						transform(level, pos, facing, null, blockEntityData, true);
					}
					
				} else if (replaceState == WALL_REPLACE) {
					transform(level, pos, facing, null, blockEntityData, false);
				} else if (fenceCheck(level, pos, facing)) {
					transform(level, pos, facing, null, blockEntityData, true);
				} else {
					transform(level, pos, facing, null, blockEntityData, false);
				}
			} else {
				if (replaceState == WALL_REPLACE) {
					transform(level, pos, facing, null, blockEntityData, false);
				} else if (windowCheck(level, pos, facing)) {
					transform(level, pos, facing, null, blockEntityData, true);
				} else {
					transform(level, pos, facing, null, blockEntityData, false);
				}
			}
		}
		

	}

	public boolean windowCheck(ServerLevel level, BlockPos pos, Direction facing) {
		// Checks adjacent block to know if the window would be obstructed by a solid block or a fence
		BlockPos checkPos = pos.relative(facing);
		BlockState state = level.getBlockState(checkPos);
		if (state.is(MesophilsCitiesModBlocks.TEMPORARY_GLASS.get())) {
			if (state.getValue(IS_FENCE)) {
				boolean fenceNeeded = fenceCheck(level, checkPos, state.getValue(FACING));
				if (state.getValue(IS_CORNER)) {
					fenceNeeded = fenceNeeded && fenceCheck(level, checkPos, state.getValue(SECONDARY_FACING));
				}
				if (!fenceNeeded) {
					return false;
				} else {
					level.setBlock(checkPos, state.setValue(REPLACE_STATE, WALL_REPLACE), 80);
					return true;
				}
				
			} else {
				return true;
			}
		}
		return !state.isFaceSturdy(level, pos, facing.getOpposite());
	}

	public boolean fenceCheck(ServerLevel level, BlockPos pos, Direction facing) {
		return fenceCheck(level, pos, facing, 0);
	}

	public boolean fenceCheck(ServerLevel level, BlockPos pos, Direction facing, int skippedBlock) {
		// Checks the three blocks next to a fence to check if there are blocks like air or temporary glass that will become air
		Direction right = facing.getClockWise();
		Direction left = facing.getCounterClockWise();
		BlockPos pos2 = pos.relative(facing);
		BlockPos[] checkPositions = {pos2.relative(left), pos2, pos2.relative(right)};
		int i = 0;
		for (BlockPos checkPos : checkPositions) {
			i++;
			if (skippedBlock == i) continue;
			BlockState checkState = level.getBlockState(checkPos);
			if (requiresFence(level, checkPos, checkState, facing.getOpposite()))
				return true;
		}
		return false;
	}

	public boolean requiresFence(ServerLevel level, BlockPos pos, BlockState state, Direction checkSide) {
		if (state.is(MesophilsCitiesModBlocks.TEMPORARY_GLASS.get())) {
			if (state.getValue(IS_FENCE)) {
				boolean hasNoPriority = (checkSide == Direction.NORTH || checkSide == Direction.WEST);
				if (!hasNoPriority && !fenceCheck(level, pos, checkSide)) { // checks if a fence is not needed (that´s the case if it has an adjacent fence that has priority and no other block needs it
					level.setBlock(pos, state.setValue(REPLACE_STATE, WALL_REPLACE), 80);
				}
				return hasNoPriority;
			} else {
				level.setBlock(pos, state.setValue(REPLACE_STATE, WALL_REPLACE), 80);
				return false;
			}
		} else {
			return !state.isFaceSturdy(level, pos, checkSide) && !state.is(MesophilsCitiesModTags.WINDOW);
		}
	}

	
	public void transform(ServerLevel level, BlockPos pos, Direction facing, Direction facing2, CompoundTag blockEntityData, boolean becomeWindow) {
		String rlString = blockEntityData.getString((becomeWindow ? "window" : "wall"));
		BlockState state = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(rlString)).getStateForPlacement(new BlockPlaceContext(level, null, InteractionHand.MAIN_HAND, ItemStack.EMPTY, new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos.relative(Direction.DOWN), false)));
		/*
		if (state.hasProperty(BlockStateProperties.NORTH))
			state = setDirectionsTrue(state, facing, facing2, 1);
		if (state.hasProperty(BlockStateProperties.NORTH_REDSTONE))
			state = setDirectionsTrue(state, facing, facing2, 2);
		if (state.hasProperty(BlockStateProperties.NORTH_WALL)) {
			if (state.getBlock() instanceof WallBlock || state.getBlock() instanceof FenceBlock) {
				state = state.getBlock().getStateForPlacement(new BlockPlaceContext(level, null, InteractionHand.MAIN_HAND, ItemStack.EMPTY, new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos.relative(Direction.DOWN), false)));
			} else {
			state = setDirectionsTrue(state, facing, facing2, 3);
			}
		}
			
		if (state.hasProperty(BlockStateProperties.FACING))
			state = state.setValue(BlockStateProperties.FACING, facing);
		if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING))
			state = state.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
			*/
		level.setBlock(pos, state, 7);
	}

	private BlockState setDirectionsTrue(BlockState state, Direction direction, int propertyType) {
		return setDirectionsTrue(state, List.of(direction.getCounterClockWise(), direction.getClockWise()), propertyType);
	}

	private BlockState setDirectionsTrue(BlockState state, Direction direction1, Direction direction2, int propertyType) {
		if (direction2 == null) return setDirectionsTrue(state, direction1, propertyType);
		List<Direction> list = new ArrayList(List.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST));
		list.remove(direction1);
		list.remove(direction2);
		return setDirectionsTrue(state, list, propertyType);
	}
	
	private BlockState setDirectionsTrue(BlockState state, List<Direction> list, int propertyType) {
		if (list.contains(Direction.NORTH))
			switch (propertyType) {
				case 1: 
				state = state.setValue(BlockStateProperties.NORTH, true);
				break;
				case 2: 
				state = state.setValue(BlockStateProperties.NORTH_REDSTONE, RedstoneSide.SIDE);
				break;
				case 3: 
				state = state.setValue(BlockStateProperties.NORTH_WALL, WallSide.LOW);
				break;
			}
		if (list.contains(Direction.SOUTH))
			switch (propertyType) {
				case 1: 
				state = state.setValue(BlockStateProperties.SOUTH, true);
				break;
				case 2: 
				state = state.setValue(BlockStateProperties.SOUTH_REDSTONE, RedstoneSide.SIDE);
				break;
				case 3: 
				state = state.setValue(BlockStateProperties.SOUTH_WALL, WallSide.LOW);
				break;
			}
		if (list.contains(Direction.WEST))
			switch (propertyType) {
				case 1: 
				state = state.setValue(BlockStateProperties.WEST, true);
				break;
				case 2: 
				state = state.setValue(BlockStateProperties.WEST_REDSTONE, RedstoneSide.SIDE);
				break;
				case 3: 
				state = state.setValue(BlockStateProperties.WEST_WALL, WallSide.LOW);
				break;
			}
		if (list.contains(Direction.EAST))
			switch (propertyType) {
				case 1: 
				state = state.setValue(BlockStateProperties.EAST, true);
				break;
				case 2: 
				state = state.setValue(BlockStateProperties.EAST_REDSTONE, RedstoneSide.SIDE);
				break;
				case 3: 
				state = state.setValue(BlockStateProperties.EAST_WALL, WallSide.LOW);
				break;
			}
		return state;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState blockstate = super.getStateForPlacement(context);
		int i;
		for (i=0; i<48; i++) {
			if (!world.getBlockState(pos.below(i+1)).is(MesophilsCitiesModBlocks.TEMPORARY_GLASS.get())) {
				break;
			}
		}
		for (int j=1; j<48; j++) {
			BlockState state = world.getBlockState(pos.above(j));
			if (state.is(MesophilsCitiesModBlocks.TEMPORARY_GLASS.get())) {
				world.setBlock(pos.above(j), state.setValue(FENCE_Y, Math.min(i+j, 48)), 3);
			} else {
				break;
			}
		}
		blockstate = blockstate.setValue(FENCE_Y, Math.min(i, 48));
		Direction direction = context.getHorizontalDirection().getOpposite();
		blockstate = blockstate.setValue(FACING, direction);
		blockstate = blockstate.setValue(SECONDARY_FACING, direction);
		return blockstate;
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity enitity, ItemStack itemstack) {
		CompoundTag tag = level.getBlockEntity(pos).getPersistentData();
		if (!level.isClientSide() && !level.dimension().equals(CITY) && !tag.getString("wall").equals("") && !tag.getString("wall").equals("minecraft:air")) {
			level.setBlock(pos, state.setValue(IS_FENCE, false), 3);
		}
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighbourState, LevelAccessor levelAccessor, BlockPos pos, BlockPos neighbourPos) {
		if (levelAccessor instanceof Level level) {
			if (facing == Direction.DOWN && !level.dimension().equals(CITY)) {
				int i;
				for (i=0; i<48; i++) {
					if (!level.getBlockState(pos.below(i+1)).is(MesophilsCitiesModBlocks.TEMPORARY_GLASS.get())) {
						break;
					}
				}
				for (int j=1; j<48; j++) {
					BlockState updateState = level.getBlockState(pos.above(j));
					if (updateState.is(MesophilsCitiesModBlocks.TEMPORARY_GLASS.get())) {
						level.setBlock(pos.above(j), updateState.setValue(FENCE_Y, Math.min(i+j, 48)), 3);
					} else {
						break;
					}
				}
				return state.setValue(FENCE_Y, Math.min(i, 48));
			}
		}
		
		return state;
	}

	@Override
	public InteractionResult use(BlockState blockstate, Level world, BlockPos pos, Player entity, InteractionHand hand, BlockHitResult hit) {
		super.use(blockstate, world, pos, entity, hand, hit);
		ItemStack itemstack = entity.getItemInHand(hand);
		Direction direction = hit.getDirection();
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (direction == null || blockEntity == null)
			return InteractionResult.FAIL;
		if (itemstack.getItem() == (new ItemStack(MesophilsCitiesModBlocks.TEMPORARY_GLASS.get())).getItem()) {
			return InteractionResult.FAIL;
		}
		if (itemstack.isEmpty()) {
			if (direction == Direction.UP || direction == Direction.DOWN)
				return InteractionResult.FAIL;
			Direction facing = blockstate.getValue(FACING);
			Direction facing2 = blockstate.getValue(SECONDARY_FACING);
			if (facing == facing2 && facing != direction) {
				blockstate = blockstate.setValue(SECONDARY_FACING, direction).setValue(IS_CORNER, true);
				world.setBlock(pos, blockstate, 3);
				return InteractionResult.SUCCESS;
			} else if (facing2 != facing && direction == facing2) {
				blockstate = blockstate.setValue(SECONDARY_FACING, facing).setValue(IS_CORNER, false);
				world.setBlock(pos, blockstate, 3);
				return InteractionResult.SUCCESS;
			} else if (facing2 != facing && direction == facing) {
				blockstate = blockstate.setValue(FACING, facing2).setValue(IS_CORNER, false);
				world.setBlock(pos, blockstate, 3);
				return InteractionResult.SUCCESS;
			}
		} else if (blockEntity.getPersistentData().getString("window").equals("")) {
			if (!world.isClientSide() && itemstack.getItem() instanceof BlockItem blockItem) {
				Block block = blockItem.getBlock();
				blockEntity.getPersistentData().putString("window", ForgeRegistries.BLOCKS.getKey(block).toString());
				world.sendBlockUpdated(pos, blockstate, blockstate, 3);
			}
			return InteractionResult.SUCCESS;
		} else if (blockEntity.getPersistentData().getString("wall").equals("")) {
			if (!world.isClientSide() && itemstack.getItem() instanceof BlockItem blockItem) {
				Block block = blockItem.getBlock();
				blockEntity.getPersistentData().putString("wall", ForgeRegistries.BLOCKS.getKey(block).toString());
				blockstate = blockstate.setValue(IS_FENCE, false);
				world.setBlock(pos, blockstate, 3);
				world.sendBlockUpdated(pos, blockstate, blockstate, 3);

			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.FAIL;
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		state = super.rotate(state, rotation);
		return state.setValue(SECONDARY_FACING, rotation.rotate(state.getValue(SECONDARY_FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(SECONDARY_FACING)));
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TemporaryGlassBlockEntity(pos, state);
	}

	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity == null ? false : blockEntity.triggerEvent(eventID, eventParam);
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}
}
