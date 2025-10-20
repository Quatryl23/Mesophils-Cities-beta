package net.mesomods.mesophilscities;


import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;

public class LootTableWandPlayerData implements ILootTableWandPlayerData {
	private boolean emptyTargetContainer = false;
	private boolean synchronizeWands = false;
	private String savedLocation = "";

	@Override
	public boolean getEmptyTargetContainer() {
		return this.emptyTargetContainer;
	}

	@Override
	public void setEmptyTargetContainer(boolean b) {
		this.emptyTargetContainer = b;
	}

	@Override
	public boolean getSynchronizeWands() {
		return this.synchronizeWands;
	}

	@Override
	public String getSavedLocation() {
		return this.savedLocation;
	}

	@Override
	public void setSavedLocation(String s) {
		this.savedLocation = s;
	}

	@Override
	public void setSynchronizeWands(boolean b) {
		this.synchronizeWands = b;
	}

	public String toString() {
		return ("LootTableWandPlayerData" + "\nShould empty target container: " + emptyTargetContainer + "\nSynchronized wands: " + synchronizeWands + "\nSaved location: " + savedLocation);
	}
}
