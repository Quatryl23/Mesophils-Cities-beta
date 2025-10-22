package net.mesomods.mesophilscities;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

@OnlyIn(Dist.CLIENT)
public class NoStylePackWarningScreen extends ConfirmScreen {
	public static final String MESSAGE = "You´re trying to load Mesophil´s Cities without any Style Pack enabled. If you continue, you will play with the default debug structure pack consisting of just colored concrete cubes. If you want to play with actual buildings, you should download a Style Pack and make sure it is correctly loaded";
	public static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation("mesophils_cities", "textures/screens/default_preview.png");

	public NoStylePackWarningScreen() {
		super((b) -> {
			Minecraft mc = Minecraft.getInstance();	
			if (!b) {
				mc.setScreen(new StylePackOverviewScreen());
			} else {
				mc.setScreen(null);
				MesophilsCitiesModNetwork.CHANNEL.sendToServer(new SuppressStylePackWarningPacket());
			}
		}, Component.literal("No Style Pack found"), Component.literal(MESSAGE), Component.literal("Ignore and continue"), Component.literal("Show Style Packs"));
	}

	@Override
	public void renderBackground(GuiGraphics graphics) {
		graphics.blit(BACKGROUND_LOCATION, 0, 0, 0, 0, this.width, this.height, this.width, this.height);
		super.renderBackground(graphics);
	}
}
