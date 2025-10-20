package net.mesomods.mesophilscities.item;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.mesomods.mesophilscities.init.MesophilsCitiesModItems;
import net.mesomods.mesophilscities.UpdateLootTableWandPlayerDataPacket;
import net.mesomods.mesophilscities.UpdateLootTableWandPacket;
import net.mesomods.mesophilscities.ModCapabilities;
import net.mesomods.mesophilscities.LootTableWandGUI;
import net.mesomods.mesophilscities.LootTableRequestNetwork;

import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.ChatFormatting;



import javax.annotation.Nullable;

import java.util.List;

public class LootTableWandItem extends Item {
	public LootTableWandItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
	}

	@Override
	public Component getName(ItemStack stack) {
		CompoundTag itemStackData = stack.getOrCreateTag();
		if (itemStackData.contains("ActiveLootTable")) {
			ResourceLocation lootTable = getActiveLootTable(stack);
			String lootTableString = lootTable == null ? "No loot table set" : lootTable.toString();
			return Component.literal(lootTableString.substring(lootTableString.lastIndexOf("/") + 1)).withStyle(ChatFormatting.WHITE);
		}
		return super.getName(stack);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(stack, level, list, flag);
		CompoundTag itemStackData = stack.getOrCreateTag();
		if (itemStackData.contains("ActiveLootTable")) {
			MutableComponent itemName = (MutableComponent) super.getName(stack);
			Component purpleItemName = itemName.withStyle(ChatFormatting.LIGHT_PURPLE);
			list.add(purpleItemName);
			ResourceLocation lootTable = getActiveLootTable(stack);
			if (lootTable != null) {
				String lootTableString = lootTable.toString();
				list.add(Component.literal(lootTableString));
			} else {
				list.add(Component.translatable("item.mesophils_cities.loot_table_wand.description_0"));
			}
		} else {
			list.add(Component.translatable("item.mesophils_cities.loot_table_wand.description_0"));
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (level.isClientSide) {
			Minecraft.getInstance().setScreen(new LootTableWandGUI(player, stack));
		}
		return InteractionResultHolder.success(stack);
	}

	// Loot table items preview : disabled
	/*@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean b) {
		if (level instanceof ServerLevel serverLevel && entity instanceof ServerPlayer serverplayer) {
			CompoundTag itemNbt = stack.getOrCreateTag();
			if (itemNbt.contains("PreviewTime")) {
				if (itemNbt.getInt("PreviewTime") == 0) {
					if (itemNbt.getList("PreviewItems", 8).size() == 0) {
						ListTag previewItems = itemNbt.getList("PreviewItems", 8);
						ResourceLocation lootTableLocation = new ResourceLocation(itemNbt.getString("LootTable" + itemNbt.getInt("ActiveLootTable")));
						LootTable lootTable = serverLevel.getServer().getLootData().getLootTable(lootTableLocation);
						LootParams lootParams = new LootParams.Builder(serverLevel).withParameter(LootContextParams.ORIGIN, entity.getEyePosition()).withParameter(LootContextParams.THIS_ENTITY, entity).create(LootContextParamSets.CHEST);
						LootContext lootContext = new LootContext.Builder(lootParams).create(new ResourceLocation("mesophils_cities", "loot_table_wand_preview"));
						List<ItemStack> lootList = new ArrayList();
						Consumer<ItemStack> lootGetter = lootList::add;
						lootTable.getRandomItems(lootContext, lootGetter);
						System.out.println(lootList.toString());
						for (ItemStack lootStack : lootList) {
							Item lootItem = lootStack.getItem();
							ResourceLocation lootItemLocation = ForgeRegistries.ITEMS.getKey(lootItem);
							previewItems.add(StringTag.valueOf(lootItemLocation.toString()));
						}
						System.out.println(previewItems.toString());
					} else {
						itemNbt.getList("PreviewItems", 8).remove(0);
					}
					itemNbt.putInt("PreviewTime", 500);
					ListTag previewItemsTag = itemNbt.getList("PreviewItems", 8);
					List<String> previewItemList = new ArrayList();
					for (int i = 0; i < previewItemsTag.size(); i++) {
						previewItemList.add(previewItemsTag.getString(i));
					}
					//LootTableRequestNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverplayer), new UpdateLootTableWandItemPreviewPacket(previewItemList, slot));
				} else {
					itemNbt.putInt("PreviewTime", itemNbt.getInt("PreviewTime") - 1);
				}
			}
		}
	}
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				Minecraft instance = Minecraft.getInstance();
				return new LootTableWandRenderer(instance.getBlockEntityRenderDispatcher(), instance.getEntityModels());
			}
		});
	}
	*/
	@Override
	public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
		return this.useOn(context);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		if (level.isClientSide()) {
			Player player = context.getPlayer();
			BlockPos targetPos = context.getClickedPos();
			BlockEntity targetBE = level.getBlockEntity(targetPos);
			if (!(targetBE instanceof RandomizableContainerBlockEntity container) && context.isSecondaryUseActive()) {
				use(level, player, context.getHand());			
			}
			return InteractionResult.SUCCESS;
		}
			
		BlockPos targetPos = context.getClickedPos();
		Player player = context.getPlayer();
		ItemStack stack = context.getItemInHand();
		BlockEntity targetBE = level.getBlockEntity(targetPos);
		ResourceLocation lootTable = getActiveLootTable(stack);
		MutableComponent message;
		if (targetBE instanceof RandomizableContainerBlockEntity container) {
			if (context.isSecondaryUseActive()) {
				CompoundTag data = container.saveWithoutMetadata();
				MutableComponent containerName = (MutableComponent) container.getName();
				message = Component.empty();
				message.append(containerName.withStyle(ChatFormatting.DARK_GREEN));
				if (data.contains("LootTable")) {
					message.append(Component.literal(" has loot table "));
					message.append(Component.literal(data.getString("LootTable")).withStyle(ChatFormatting.GREEN));
				} else {
					message.append(Component.literal(" has no loot table"));
				}
			} else {
				if (lootTable == null) {
					message = Component.literal("No Loot Table selected").withStyle(ChatFormatting.RED);
				} else {
					if (shouldEmptyTargetContainer(player)) {
						container.clearContent();
					}
					container.setLootTable(lootTable, 0);
					message = Component.literal("Appended ");
					message.append(Component.literal(LootTableWandItem.getShortenedLocation(lootTable.getPath())).withStyle(ChatFormatting.GREEN));
					message.append(Component.literal(" to "));
					MutableComponent containerName = (MutableComponent) container.getName();
					message.append(containerName.withStyle(ChatFormatting.DARK_GREEN));
					level.playSound(player, targetPos, SoundEvents.NOTE_BLOCK_PLING.value(), SoundSource.PLAYERS);
				}
				
			}	
		} else {
			message = Component.literal("Cannot append loot tables to ").append(level.getBlockState(targetPos).getBlock().getName().withStyle(ChatFormatting.RED));
		}
		player.displayClientMessage(message, true);
		return InteractionResult.SUCCESS;
	}

	public static String getShortenedLocation(String string) {
		if (string.contains("/")) {
			return string.substring(string.lastIndexOf("/") + 1);
		}
		return string.substring(string.lastIndexOf(":") + 1);
	}

	public static boolean shouldEmptyTargetContainer(Player player) {
		final boolean[] result = {false};
		player.getCapability(ModCapabilities.LOOT_TABLE_WAND_PLAYER_DATA).ifPresent(data -> {
			result[0] = data.getEmptyTargetContainer();
		});
		return result[0];
	}

	public static boolean shouldSynchronizeWands(Player player) {
		final boolean[] result = {false};
		player.getCapability(ModCapabilities.LOOT_TABLE_WAND_PLAYER_DATA).ifPresent(data -> {
			result[0] = data.getSynchronizeWands();
		});
		return result[0];
	}

	public static String getSavedLocation(Player player) {
		final String[] result = {""};
		player.getCapability(ModCapabilities.LOOT_TABLE_WAND_PLAYER_DATA).ifPresent(data -> {
			result[0] = data.getSavedLocation();
		});
		return result[0];
	}

	public static void saveLocation(Player player, String location) {
		player.getCapability(ModCapabilities.LOOT_TABLE_WAND_PLAYER_DATA).ifPresent(data -> {
			data.setSavedLocation(location);
		});
	}

	public static void updatePlayerPreferences(Player player, LootTableWandGUI gui, boolean synchWithServer) {
		player.getCapability(ModCapabilities.LOOT_TABLE_WAND_PLAYER_DATA).ifPresent(data -> {
			data.setEmptyTargetContainer(gui.emptyContainerCheckbox.selected());
			data.setSynchronizeWands(gui.synchronizeWandsCheckbox.selected());
		});
		LootTableRequestNetwork.CHANNEL.sendToServer(new UpdateLootTableWandPlayerDataPacket(gui.emptyContainerCheckbox.selected(), gui.synchronizeWandsCheckbox.selected()));
		if (synchWithServer)
			syncWandNbtWithServer(player, gui.boundItem);
	}

	public static void syncWandNbtWithServer(Player player, ItemStack stack) {
		int slot = player.getInventory().items.indexOf(stack);
		LootTableRequestNetwork.CHANNEL.sendToServer(new UpdateLootTableWandPacket(slot, stack.getOrCreateTag()));
	}

	public static boolean setLootTable(Player player, ItemStack stack, int lootTableSlot, String lootTableString) {
		if (lootTableSlot > 9 || lootTableSlot < 0) {
			return false;
		}
		CompoundTag itemStackData = stack.getOrCreateTag();
		String lootTableKey = "LootTable" + lootTableSlot;
		itemStackData.putString(lootTableKey, lootTableString);
		return true;
	}

	public static int getActiveLootTableIndex(ItemStack stack) {
		CompoundTag itemStackData = stack.getOrCreateTag();
		if (itemStackData.contains("ActiveLootTable")) {
			return itemStackData.getInt("ActiveLootTable");
		} else {
			return -1;
		}
	}

	public static void setActiveLootTableIndex(Player player, ItemStack stack, int index) {
		CompoundTag itemStackData = stack.getOrCreateTag();
		itemStackData.putInt("ActiveLootTable", index);
		syncWandNbtWithServer(player, stack);
		if (!itemStackData.contains("PreviewTime") && getLootTable(stack, index) != null) {
			itemStackData.putInt("PreviewTime", 0);
			itemStackData.put("PreviewItems", new ListTag());
		} else if (itemStackData.contains("PreviewTime") && getLootTable(stack, index) == null) {
			itemStackData.remove("PreviewTime");
			itemStackData.remove("PreviewItems");
		}
	}

	public static void setActiveLootTableIndex(ItemStack stack, int index, Player player, @Nullable LootTableWandGUI gui) {
		if (gui != null) {
			updatePlayerPreferences(player, gui, false);
		}
		if (shouldSynchronizeWands(player)) {
			for (ItemStack itemstack : player.getInventory().items) {
				if (itemstack.is(MesophilsCitiesModItems.LOOT_TABLE_WAND.get())) {
					setActiveLootTableIndex(player, itemstack, index);
				}
			}
		} else {
			setActiveLootTableIndex(player, stack, index);
		}
	}

	public static void removeLootTable(ItemStack stack, int index) {
		if (index == -1) {
			return;
		}
		CompoundTag itemStackData = stack.getOrCreateTag();
		String lootTableKey = "LootTable" + index;
		itemStackData.remove(lootTableKey);
	}

	public static void removeActiveLootTable(ItemStack stack) {
		removeLootTable(stack, getActiveLootTableIndex(stack));
	}

	@Nullable
	public static ResourceLocation getActiveLootTable(ItemStack stack) {
		return getLootTable(stack, -1);
	}

	@Nullable
	public static ResourceLocation getLootTable(ItemStack stack, int lootTableSlot) {
		if (lootTableSlot > 9 || lootTableSlot < -1) {
			return null;
		}
		CompoundTag itemStackData = stack.getOrCreateTag();
		if (lootTableSlot == -1) {
			lootTableSlot = itemStackData.contains("ActiveLootTable") ? itemStackData.getInt("ActiveLootTable") : 0;
		}
		String lootTableKey = "LootTable" + lootTableSlot;
		if (!itemStackData.contains(lootTableKey)) {
			return null;
		}
		String lootTableString = itemStackData.getString(lootTableKey);
		ResourceLocation lootTableLocation = new ResourceLocation(lootTableString);
		return lootTableLocation;
	}
}
