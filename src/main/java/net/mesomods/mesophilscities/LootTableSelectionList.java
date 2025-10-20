package net.mesomods.mesophilscities;

import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.ObjectSelectionList.Entry;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Font;
import net.minecraft.client.Minecraft;

import java.util.function.Consumer;

public class LootTableSelectionList extends ObjectSelectionList<LootTableSelectionList.Entry> {
	public LootTableSelectionList(Minecraft minecraft, int width, int height, int y0, int y1, int itemHeight) {
		super(minecraft, width, height, y0, y1, itemHeight);
	}

	protected int addEntry(String string, Font font, Consumer<String> action) {
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

	class Entry extends ObjectSelectionList.Entry<LootTableSelectionList.Entry> {
		private final Consumer<String> onClick;
		private final StringWidget widget;
		private final boolean isFolder;
		private static final int BUTTON_WIDTH = 200;
		private static final int BUTTON_HEIGHT = 20;
		private long lastClickTime = 0L;

		Entry(int x, int y, String string, Font font, Consumer<String> action) {
			this.widget = new StringWidget(Component.literal(string), font);
			this.onClick = action;
			this.isFolder = !string.endsWith(".json");
		}

		public Component getNarration() {
			return widget.getMessage();
		}

		public boolean isFolder() {
			return isFolder;
		}

		public String getString() {
			return widget.getMessage().getString();
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
			if (isFolder)
				renderFrame(graphics, top, left, top + height - 1, left + width - 5);
		}

		public void renderFrame(GuiGraphics graphics, int top, int left, int bottom, int right) {
			final int COLOR = 0xFF888888;
			graphics.fill(left, top, right + 1, top + 1, COLOR);
			graphics.fill(left, top, left + 1, bottom + 1, COLOR);
			graphics.fill(left, bottom, right + 1, bottom + 1, COLOR);
			graphics.fill(right, top, right + 1, bottom + 1, COLOR);
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int buttonCode) {
			if (System.nanoTime() - lastClickTime > 500_000_000L) {
				lastClickTime = System.nanoTime();
				return true;
			}
			onClick.accept(widget.getMessage().getString());
			return true;
		}
	}
}
