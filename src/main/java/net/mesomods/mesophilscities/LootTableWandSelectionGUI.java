package net.mesomods.mesophilscities;

import org.checkerframework.checker.units.qual.s;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import net.mesomods.mesophilscities.item.LootTableWandItem;

import java.util.function.Consumer;
import java.util.List;
import java.util.ArrayList;

@OnlyIn(Dist.CLIENT)
public class LootTableWandSelectionGUI extends Screen {
	private final LootTableWandGUI backgroundGui;
	private final Player player;
	private List<ResourceLocation> allLootTables = new ArrayList();
	private EditBox searchBox;
	private RespondingCheckbox searchToggle;
	private String viewedResourceLocation;
	private String lootTableCountString;
	private boolean emptyLocation;
	private LootTableSelectionList selectionList;
	private List<Button> buttons = new ArrayList();
	private List<Integer> betweenButtonSpaces = new ArrayList();
	private Button changedParentButton = null;
	private long buttonHideTime = 0;

	public LootTableWandSelectionGUI(LootTableWandGUI gui, Player p) {
		super(Component.literal("Loot Table Selection"));
		gui.setIsBackground(true);
		this.backgroundGui = gui;
		this.player = p;
		this.lootTableCountString = "Loading Loot Tables";
	}

	@Override
	protected void init() {
		searchBox = new EditBox(this.font, this.width / 2 - 80, this.height / 32, 160, 20, Component.empty());
		searchBox.setResponder(s -> this.reloadResourceLocation());
		searchToggle = new RespondingCheckbox(this.width / 2 + 90, this.height / 32, 20, 20, Component.literal("Show all files"), false);
		searchToggle.setResponder(s -> this.reloadResourceLocation());
		selectionList = new LootTableSelectionList(Minecraft.getInstance(), this.width, this.height / 8, this.height / 8, 7 * this.height / 8, this.height / 12);
		selectionList.setRenderBackground(false);
		selectionList.setRenderTopAndBottom(false);
		viewedResourceLocation = LootTableWandItem.getSavedLocation(player);
		LootTableRequestNetwork.CHANNEL.sendToServer(new RequestLootTablesPacket());
		this.addRenderableWidget(searchBox);
		this.addRenderableWidget(searchToggle);
		this.addRenderableWidget(selectionList);
	}

	protected void initWithLootTableData(List<ResourceLocation> recievedList) {
		recievedList.sort((rl1, rl2) -> {
			boolean firstVanilla = rl1.getNamespace().equals("minecraft");
			boolean secondVanilla = rl2.getNamespace().equals("minecraft");
			if (firstVanilla && !secondVanilla) return -1;
			if (!firstVanilla && secondVanilla) return 1;
			return rl1.getNamespace().compareTo(rl2.getNamespace());
		});
		allLootTables = recievedList;
		this.reloadResourceLocation();
	}

	public void reloadResourceLocation() {
		for (Button button : buttons) {
			this.removeWidget(button);
		}
		buttons = new ArrayList();
		betweenButtonSpaces = new ArrayList();
		emptyLocation = true;
		List<String> availablePaths = new ArrayList();
		List<ResourceLocation> lootTables;
		String namespace = null;
		String path = null;
		if (viewedResourceLocation.equals("")) {
			lootTables = allLootTables;
			for (ResourceLocation lootTable : getFilteredLootTables(null, null)) {
				String lootTableNamespace = lootTable.getNamespace();
				if (searchToggle.selected()) {
					availablePaths.add(lootTable.getNamespace() + ":" + lootTable.getPath().substring(12));
				} else {
					if (!availablePaths.contains(lootTableNamespace)) {
					availablePaths.add(lootTableNamespace);
					}
				}			
			}
		} else if (!viewedResourceLocation.contains(":")) {
			namespace = viewedResourceLocation;
			String finalNamespace = namespace;
			lootTables = getFilteredLootTables(finalNamespace, "loot_tables");
			for (ResourceLocation lootTable : lootTables) {
				String subPath = lootTable.getPath();
				subPath = subPath.substring(12);
				String availablePath = subPath;
				if (subPath.contains("/")) {
					availablePath = subPath.substring(0, subPath.indexOf('/'));
				}
				if (!subPath.equals(availablePath) && searchToggle.selected()) {
					availablePaths.add(subPath);
				} else {
					if (!availablePaths.contains(availablePath)) {
						availablePaths.add(availablePath);
					}
				}
			}
		} else {
			String[] splitLocation = viewedResourceLocation.split(":");
			namespace = splitLocation[0];
			String finalNamespace = namespace;
			path = splitLocation[1];
			lootTables = getFilteredLootTables(finalNamespace, "loot_tables/" + path);
			for (ResourceLocation lootTable : lootTables) {
				String subPath = lootTable.getPath();
				subPath = subPath.substring(path.length() + 13);
				String availablePath = subPath;
				if (subPath.contains("/")) {
					availablePath = subPath.substring(0, subPath.indexOf('/'));

				}
				if (!subPath.equals(availablePath) && searchToggle.selected()) {
					availablePaths.add(subPath);
				} else {
					if (!availablePaths.contains(availablePath)) {
						availablePaths.add(availablePath);
					}
				}
			}
		}
		selectionList.removeAllEntries();
		for (String availablePath : availablePaths) {
			selectionList.addEntry(availablePath, this.font, string -> this.clickedOnString(string));
			this.emptyLocation = false;
		}
		selectionList.setScrollAmount(0.0);
		List<String> locationComponents = new ArrayList();
		locationComponents.add("data");
		if (namespace != null) {
			locationComponents.add(namespace);
			if (path != null) {
				locationComponents.addAll(List.of(path.split("/")));
			}
		}
		for (String locationComponent : locationComponents) {
			int buttonStartPos = betweenButtonSpaces.isEmpty() ? 10 : betweenButtonSpaces.get(betweenButtonSpaces.size() - 1) + 5;
			int buttonSize = this.font.width(locationComponent) + 12;
			buttons.add(Button.builder(Component.literal(locationComponent), btn -> this.clickedOnParentFolder(btn.getMessage().getString())).pos(buttonStartPos, 9 * this.height / 10).size(buttonSize, this.height / 14).build());
			betweenButtonSpaces.add(buttonStartPos + buttonSize + 5);
		}
		for (Button button : buttons) {
			this.addRenderableWidget(button);
		}
	}

	public void clickedOnParentFolder(String string) {
		if (string.equals("data")) {
			this.viewedResourceLocation = "";
		} else {
			this.viewedResourceLocation = this.viewedResourceLocation.substring(0, viewedResourceLocation.indexOf(string) + string.length());
		}
		this.reloadResourceLocation();
	}

	public void clickedOnString(String string) {
		if (string.endsWith(".json")) {
			backgroundGui.setLootTable(buildNewLocation(string));
			this.onClose();
			return;
		} else if (viewedResourceLocation.equals("")) {
			this.viewedResourceLocation = string;
		} else if (!viewedResourceLocation.contains(":")) {
			this.viewedResourceLocation = this.viewedResourceLocation + ":" + string;
		} else {
			this.viewedResourceLocation = this.viewedResourceLocation + "/" + string;
		}
		this.reloadResourceLocation();
	}

	public List<ResourceLocation> getFilteredLootTables(String namespace, String path) {
		List<ResourceLocation> filteredLootTables = new ArrayList();
		for (ResourceLocation lootTable : allLootTables) {
			if (namespace != null && !lootTable.getNamespace().equals(namespace))
				continue;
			if (path != null && !lootTable.getPath().startsWith(path))
				continue;
			if (!lootTable.toString().replace("loot_tables/", "").contains(searchBox.getValue())) {
				continue;
			}
			filteredLootTables.add(lootTable);
		}
		lootTableCountString = filteredLootTables.size() + " loot tables found";
		return filteredLootTables;
	}

	@Override
	public boolean keyPressed(int key, int x, int y) {
		if (!super.keyPressed(key, x, y) && key > 47 && key < 58) {
			LootTableSelectionList.Entry selectedEntry = selectionList.getSelected();
			if (selectedEntry == null || selectedEntry.isFolder())
				return false;
			String selectedLootTable = buildNewLocation(selectedEntry.getString());
			int slot = key == 48 ? 10 : key - 48;
			int i = (slot - 1) % 5;
			int j = (slot - 1) == i ? 0 : 1;
			String shortLocation = backgroundGui.getShortenedLocation(selectedLootTable);
			if (changedParentButton != null) {
				this.removeWidget(changedParentButton);
			}
			this.changedParentButton = Button.builder(Component.literal(shortLocation), (btn) -> {
			}).pos(this.width / 2 + 5 + (210 * (j - 1)), (i + 3) * this.height / 8).size(200, 20).build();
			backgroundGui.setLootTable(slot - 1, selectedLootTable);
			this.addRenderableWidget(changedParentButton);
			buttonHideTime = System.nanoTime() + 300_000_000L;
			return true;
		}
		return super.keyPressed(key, x, y);
	}

	public String buildNewLocation(String string) {
		String stringWithoutFileExtension = string.endsWith(".json") ? string.substring(0, string.length() - 5) : string;
		if (viewedResourceLocation.equals("")) return stringWithoutFileExtension;
		char locationSeperator = viewedResourceLocation.contains(":") ? '/' : ':';
		return viewedResourceLocation + locationSeperator + stringWithoutFileExtension;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		if (this.emptyLocation) {
			guiGraphics.drawCenteredString(this.font, "No loot tables found", this.width / 2, 3 * this.height / 16, 0xFFFFFF);
		}
		guiGraphics.drawCenteredString(this.font, lootTableCountString, this.width / 2 - 160, this.height / 19, 0xFFFFFF);
		String seperatorChar = ":";
		for (int betweenButtonSpace : betweenButtonSpaces) {
			guiGraphics.drawCenteredString(this.font, seperatorChar, betweenButtonSpace, 12 * this.height / 13, 0xFFFFFF);
			seperatorChar = "/";
		}
		if (changedParentButton != null && System.nanoTime() > this.buttonHideTime) {
			this.removeWidget(changedParentButton);
			changedParentButton = null;
		}
	}

	@Override
	public void onClose() {
		super.onClose();
		backgroundGui.setIsBackground(false);
		LootTableWandItem.saveLocation(player, viewedResourceLocation);
		Minecraft.getInstance().setScreen(backgroundGui);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	public class RespondingCheckbox extends Checkbox {
		private Consumer<Boolean> responder = null;

		public RespondingCheckbox(int i1, int i2, int i3, int i4, Component comp, boolean b) {
			super(i1, i2, i3, i4, comp, b);
		}

		@Override
		public void onPress() {
			super.onPress();
			if (responder != null) {
				responder.accept(this.selected());
			}
		}

		public void setResponder(Consumer<Boolean> consumer) {
			this.responder = consumer;
		}
	}
}
