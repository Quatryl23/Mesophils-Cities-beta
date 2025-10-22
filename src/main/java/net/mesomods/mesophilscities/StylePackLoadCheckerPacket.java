package net.mesomods.mesophilscities;

import net.minecraftforge.network.NetworkEvent;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;
import net.minecraftforge.network.NetworkDirection;

public class StylePackLoadCheckerPacket {
	private boolean showWarning;

	public StylePackLoadCheckerPacket(boolean b) {
		this.showWarning = b;
	}

	public static void encode(StylePackLoadCheckerPacket pkt, FriendlyByteBuf buf) {
		buf.writeBoolean(pkt.showWarning);
	}

	public static StylePackLoadCheckerPacket decode(FriendlyByteBuf buf) {
		return new StylePackLoadCheckerPacket(buf.readBoolean());
	}

	public static void handle(StylePackLoadCheckerPacket packet, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			if (packet.showWarning) {
				if (ctx.get().getDirection().getReceptionSide().isServer()) return;
				StylePackWarningManager.openWarningScreen();
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
