package net.mesomods.mesophilscities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.ObjectSelectionList.Entry;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Font;
import net.minecraft.client.Minecraft;

import java.util.function.Consumer;

public class StylePackSelectionList extends ObjectSelectionList<StylePackSelectionList.Entry> {
	public StylePackSelectionList(Minecraft minecraft, int width, int height, int y0, int y1, int itemHeight, StylePackOverviewScreen screen) {
		super(minecraft, width, height, y0, y1, itemHeight);
		this.addEntry("Mesophil's Cities Classic", minecraft.font, (entry) -> screen.setBackground(new ResourceLocation("mesophils_cities", "textures/screens/classic_preview.png"),
				"https://www.curseforge.com/minecraft/data-packs/mesophils-cities-classic", "https://modrinth.com/datapack/mesophils-cities-classic", this.children().indexOf(entry)));
	}

	protected int addEntry(String string, Font font, Consumer<StylePackSelectionList.Entry> action) {
		return super.addEntry(new Entry(0, 0, string, font, action));
	}

	@Override
	public void render(GuiGraphics graphics, int x, int y, float partialTick) {
		graphics.fill(this.getLeft(), this.getTop(), this.getRight(), this.getBottom(), 0xB2000000);
		super.render(graphics, x, y, partialTick);
	}

	public void removeAllEntries() {
		this.clearEntries();
	}

	class Entry extends ObjectSelectionList.Entry<StylePackSelectionList.Entry> {
		private final Consumer<StylePackSelectionList.Entry> onClick;
		private final StringWidget widget;
		private static final int BUTTON_WIDTH = 200;
		private static final int BUTTON_HEIGHT = 20;
		private long lastClickTime = 0L;

		Entry(int x, int y, String string, Font font, Consumer<StylePackSelectionList.Entry> onClick) {
			this.widget = new StringWidget(Component.literal(string), font);
			this.onClick = onClick;
		}

		public Component getNarration() {
			return widget.getMessage();
		}

		@Override
		public void render(GuiGraphics graphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isHovered, float partialTicks) {
			// Position the button relative to the list
			widget.setX(left);
			widget.setY(top);
			widget.setWidth(width);
			widget.setHeight(height);
			// Render the button
			widget.render(graphics, mouseX, mouseY, partialTicks);
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int buttonCode) {
			onClick.accept(this);
			return true;
		}
	}
}
