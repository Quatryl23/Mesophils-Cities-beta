package net.mesomods.mesophilscities;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;
import net.minecraft.ChatFormatting;

import net.mesomods.mesophilscities.item.LootTableWandItem;
import net.mesomods.mesophilscities.LootTableWandSelectionGUI;

import java.util.List;
import java.util.ArrayList;

@OnlyIn(Dist.CLIENT)
public class LootTableWandGUI extends Screen {
	private EditBox textField;
	public Checkbox emptyContainerCheckbox;
	public Checkbox synchronizeWandsCheckbox;
	private List<Button> lootTableButtons;
	private LootTableSelectionList selectionList;
	private Button lastClickedButton;
	private long lastClickedButtonTime;
	public final ItemStack boundItem;
	private final Player player;
	private int activeIndex;
	private int centerX;
	private int centerY;
	private boolean isBackground;

	public LootTableWandGUI(Player player, ItemStack item) {
		super(Component.literal("Loot Table Selection"));
		this.boundItem = item;
		this.player = player;
	}

	@Override
	protected void init() {
		centerX = this.width / 2;
		centerY = this.height / 2;
		activeIndex = LootTableWandItem.getActiveLootTableIndex(boundItem);
		lootTableButtons = new ArrayList();
		isBackground = false;
		this.emptyContainerCheckbox = new Checkbox(this.width / 2 - 68, 5 * (this.height / 32), 20, 20, Component.literal("Clear target container"), LootTableWandItem.shouldEmptyTargetContainer(player));
		this.synchronizeWandsCheckbox = new Checkbox(this.width / 2 - 68, 9 * (this.height / 32), 20, 20, Component.literal("Synchronize Wands"), LootTableWandItem.shouldSynchronizeWands(player));
		this.addRenderableWidget(emptyContainerCheckbox);
		this.addRenderableWidget(synchronizeWandsCheckbox);
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 5; i++) {
				// TODO: make translatable
				int lootTableIndex = (5 * j) + i;
				ResourceLocation lootTableLocation = LootTableWandItem.getLootTable(boundItem, lootTableIndex);
				String lootTableString = lootTableLocation == null ? "No Loot Table set" : lootTableLocation.toString();
				Button lootTableButton = Button.builder(Component.literal(getShortenedLocation(lootTableString)), button -> {
					// double click functionality
					if (lastClickedButton == button) {
						long timeSinceLastClick = System.nanoTime() - lastClickedButtonTime;
						if (timeSinceLastClick > 500_000_000L) {
							lastClickedButtonTime = System.nanoTime();
							return;
						}
						lastClickedButton = null;
						Minecraft.getInstance().setScreen(new LootTableWandSelectionGUI(this, player));
					} else {
						lastClickedButton = button;
						lastClickedButtonTime = System.nanoTime();
						int newIndex = lootTableButtons.indexOf(button);
						this.updateActiveLootTable(newIndex);
						// shift clicking closes the screen after selection
						if (this.hasShiftDown()) {
							this.onClose();
						}
					}
				}).pos(this.width / 2 + 5 + (210 * (j - 1)), (i + 3) * this.height / 8).size(200, 20).build();
				lootTableButton.setTooltip(Tooltip.create(Component.literal(lootTableString)));
				this.addRenderableWidget(lootTableButton);
				lootTableButtons.add(lootTableButton);
			}
		}
		activateLootTableButton(this.activeIndex);
	}

	public void updateActiveLootTable(int newIndex) {
		deactivateLootTableButton(this.activeIndex);
		this.activeIndex = newIndex;
		LootTableWandItem.setActiveLootTableIndex(boundItem, this.activeIndex, this.player, this);
		activateLootTableButton(this.activeIndex);
	}

	public void deactivateLootTableButton(int index) {
		if (index == -1)
			return;
		Button button = lootTableButtons.get(index);
		button.setFGColor(0xE0E0E0);
		button.setFocused(false);
	}

	public void activateLootTableButton(int index) {
		if (index == -1)
			return;
		Button button = lootTableButtons.get(index);
		button.setFGColor(ChatFormatting.GREEN.getColor());
		button.setFocused(true);
	}

	public void setLootTable(String location) {
		this.setLootTable(activeIndex, location);
	}

	public void setLootTable(int index, String location) {
		LootTableWandItem.setLootTable(player, boundItem, index, location);
		Button button = lootTableButtons.get(index);
		button.setMessage(Component.literal(getShortenedLocation(location)));
	}

	public void setIsBackground(boolean b) {
		this.isBackground = b;
	}

	public static String getShortenedLocation(String string) {
		return LootTableWandItem.getShortenedLocation(string);
	}

	@Override
	public boolean keyPressed(int key, int x, int y) {
		if (!super.keyPressed(key, x, y) && key == 127) {
			LootTableWandItem.removeActiveLootTable(boundItem);
			return true;
		}
		return super.keyPressed(key, x, y);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		this.renderBackground(guiGraphics);
		guiGraphics.drawCenteredString(this.font, "Loot Table Selection", this.width / 2, this.height / 16, 0xFFFFFF);
		super.render(guiGraphics, mouseX, mouseY, partialTick);
	}

	@Override
	public void onClose() {
		LootTableWandItem.updatePlayerPreferences(player, this, true);
		super.onClose();

	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
