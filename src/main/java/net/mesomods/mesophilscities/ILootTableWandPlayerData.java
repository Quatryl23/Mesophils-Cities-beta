package net.mesomods.mesophilscities;
public interface ILootTableWandPlayerData {
	void setSynchronizeWands(boolean b);

	void setSavedLocation(String s);

	void setEmptyTargetContainer(boolean b);

	boolean getSynchronizeWands();

	String getSavedLocation();

	boolean getEmptyTargetContainer();
}
