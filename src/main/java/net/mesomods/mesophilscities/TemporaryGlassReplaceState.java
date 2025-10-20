package net.mesomods.mesophilscities;

import net.minecraft.util.StringRepresentable;

public enum TemporaryGlassReplaceState implements StringRepresentable {
	UNSET("unset"),
	WALL("wall"),
    WINDOW("window");

    private final String name;

    TemporaryGlassReplaceState(String n) {
    	this.name = n;
    }

    public String getSerializedName() {
    	return name;
    }

    public String toString() {
    	return name;
    }
}