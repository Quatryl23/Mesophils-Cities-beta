package net.mesomods.mesophilscities;

import net.minecraftforge.network.NetworkEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.CompoundTag;

import java.util.function.Supplier;
import java.util.List;

//currenty unused as item previews are disabled
public class UpdateLootTableWandItemPreviewPacket {
	private List<String> previewItems;
	private int slot;

	public UpdateLootTableWandItemPreviewPacket(List<String> previewItems, int slot) {
		this.previewItems = previewItems;
		this.slot = slot;
	}

	public static void encode(UpdateLootTableWandItemPreviewPacket pkt, FriendlyByteBuf buf) {
		buf.writeCollection(pkt.previewItems, FriendlyByteBuf::writeUtf);
		buf.writeInt(pkt.slot);
	}

	public static UpdateLootTableWandItemPreviewPacket decode(FriendlyByteBuf buf) {
		return new UpdateLootTableWandItemPreviewPacket(buf.readList(FriendlyByteBuf::readUtf), buf.readInt());
	}

	public static void handle(UpdateLootTableWandItemPreviewPacket packet, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Player player = ctx.get().getSender();
			CompoundTag itemData = player.getInventory().getItem(packet.slot).getOrCreateTag();
			ListTag list = new ListTag();
			for (String previewItem : packet.previewItems) {
				list.add(StringTag.valueOf(previewItem));
			}
			itemData.put("PreviewItems", list);
		});
		ctx.get().setPacketHandled(true);
	}
}
