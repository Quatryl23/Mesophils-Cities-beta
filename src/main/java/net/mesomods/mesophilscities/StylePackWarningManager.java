package net.mesomods.mesophilscities;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.Minecraft;

@OnlyIn(Dist.CLIENT)
public class StylePackWarningManager {
	public static void openWarningScreen() {
		Minecraft.getInstance().setScreen(new NoStylePackWarningScreen());
	}
}
