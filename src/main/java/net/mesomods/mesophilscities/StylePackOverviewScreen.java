package net.mesomods.mesophilscities;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;

@OnlyIn(Dist.CLIENT)
public class StylePackOverviewScreen extends Screen {
	private StylePackSelectionList selectionList;
	public ResourceLocation backgroundImage = new ResourceLocation("mesophils_cities", "textures/screens/default_preview.png");
	private Button curseforgeLink;
	private Button modrinthLink;
	private String curseforgeURL = "";
	private String modrinthURL = "";
	private int chosenStylePackIndex = -1;

	public StylePackOverviewScreen() {
		super(Component.literal("Style Pack Overview"));
	}

	@Override
	protected void init() {
		selectionList = new StylePackSelectionList(Minecraft.getInstance(), this.width, this.height / 8, this.height / 8, 7 * this.height / 8, this.height / 12, this);
		selectionList.setRenderBackground(false);
		selectionList.setRenderTopAndBottom(false);
		if (chosenStylePackIndex != -1) {
			StylePackSelectionList.Entry chosenEntry = selectionList.children().get(chosenStylePackIndex);
			selectionList.setSelected(chosenEntry);
			selectionList.setFocused(chosenEntry);
			
		}
		this.addRenderableWidget(selectionList);
		this.addRenderableWidget(new Button.Builder(Component.literal("\u274C"), btn -> {
			this.onClose();
			minecraft.setScreen(new NoStylePackWarningScreen());
		}).pos(29 * this.width / 30 - 10, this.height / 40).size(20, 20).build());
		this.addRenderableWidget(new Button.Builder(Component.translatable("menu.returnToMenu"), btn -> {
			this.returnToMenu();
		}).pos(this.width / 2 - 100, 9 * this.height / 10).size(200, 20).build());
		this.curseforgeLink = new Button.Builder(Component.literal("CurseForge"), btn -> {
				ConfirmLinkScreen.confirmLinkNow(curseforgeURL, this, true);
		}).pos(this.width / 6 - 40, 9 * this.height / 10).size(80, 20).build();
		this.modrinthLink = new Button.Builder(Component.literal("Modrinth"), btn -> {
			ConfirmLinkScreen.confirmLinkNow(modrinthURL, this, true);
		}).pos(5 * this.width / 6 - 40, 9 * this.height / 10).size(80, 20).build();
		if (curseforgeURL != "") {
			addRenderableWidget(curseforgeLink);
		}
		if (modrinthURL != "") {
			addRenderableWidget(modrinthLink);
		}
	}

	public void setBackground(ResourceLocation location, String curseforgeURL, String modrinthURL, int index) {
		this.backgroundImage = location;
		this.chosenStylePackIndex = index;
		this.removeWidget(curseforgeLink);
		if (curseforgeURL != null) {
			this.addRenderableWidget(curseforgeLink);
			this.curseforgeURL = curseforgeURL;
		}
		this.removeWidget(modrinthLink);
		if (modrinthURL != null) {
			this.addRenderableWidget(modrinthLink);
			this.modrinthURL = modrinthURL;
		}
	}

	@Override
	public void renderBackground(GuiGraphics graphics) {
		graphics.blit(backgroundImage, 0, 0, 0, 0, this.width, this.height, this.width, this.height);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(graphics);
		graphics.drawCenteredString(this.font, "Style Pack Overview", this.width / 2, this.height / 20, 0xFFFFFF);
		super.render(graphics, mouseX, mouseY, partialTicks);
	}

	public void returnToMenu() {
		this.onClose();
		Minecraft mc = Minecraft.getInstance();
		mc.setScreen(null);
		mc.level.disconnect();
		mc.clearLevel(new GenericDirtMessageScreen(Component.translatable("menu.savingLevel")));
		TitleScreen titleScreen = new TitleScreen();
		mc.setScreen(titleScreen);
	}

	public void onClose() {
	}
}
