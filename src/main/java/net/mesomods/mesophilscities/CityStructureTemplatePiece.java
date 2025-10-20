package net.mesomods.mesophilscities;

import net.minecraft.resources.ResourceLocation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.world.level.block.Rotation;

public class CityStructureTemplatePiece {
	public ResourceLocation location = null;
	public ResourceLocation[] processors = null;
	public int height = 63;
	public Rotation rotation = Rotation.NONE;

	public CityStructureTemplatePiece() {
	}

	public CityStructureTemplatePiece(ResourceLocation loc, ResourceLocation[] proc, int h, Rotation rot) {
		location = loc;
		processors = proc;
		height = h;
		rotation = rot;
	}

	public CityStructureTemplatePiece rotated(Rotation rot) {
		return new CityStructureTemplatePiece(location, processors, height, rotation.getRotated(rot));
	}

	public CityStructureTemplatePiece fallbackPiece(String loc, int h) {
		height = h;
		location = new ResourceLocation("mesophils_cities", loc);
		return this;
	}

}

