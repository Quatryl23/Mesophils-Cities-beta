package net.mesomods.mesophilscities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import java.util.HashMap;
import java.util.Comparator;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.JsonArray;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.mesomods.mesophilscities.CityStructureTemplatePiece;

import net.minecraft.world.level.block.Rotation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import net.mesomods.mesophilscities.WeightedTemplatePieceList;
import net.mesomods.mesophilscities.CityTemplateType;
import net.mesomods.mesophilscities.CityMultiPieceTemplateType;
import net.mesomods.mesophilscities.procedures.CityRandomSourceProcedure;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.core.BlockPos;

import org.checkerframework.checker.nullness.qual.Nullable;
import java.util.Arrays;

public class CityTemplatePoolLoader extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().create();
    
    public static Map<String,CityTemplateType> HouseTemplatePool = new HashMap<>();
    public static Map<String,CityTemplateType> TrainTemplatePool = new HashMap<>();
    public static Map<String,CityTemplateType> StreetTemplatePool = new HashMap<>();
    public static Map<String,CityTemplateType> PathTemplatePool = new HashMap<>();
    public static Map<String,CityTemplateType> CustomTemplatePool = new HashMap<>();
    public static Map<String,CityMultiPieceTemplateList> SpecialTemplatePool = new HashMap<>();
    public static int templateCount = 0;
    public static boolean showNoStylePackWarning = true;

    public CityTemplatePoolLoader() {
    	super(GSON, "worldgen/city_template_pool");
    	//construct HouseTemplatePool (empty)    	
    	for (int i=12; i<36; i+=4) {
    		for (int j=12; j<36; j+=4) {
    		    String size = "0" + i + "0" + j;
    		    HouseTemplatePool.put(size, new CityTemplateType());

    	    }
    	}
    	HouseTemplatePool.put("portal_house_x", new CityTemplateType());
    	HouseTemplatePool.put("portal_house_z", new CityTemplateType());
    	//construct StreetTemplatePool (empty)
        StreetTemplatePool.put("crossing", new CityTemplateType());
        StreetTemplatePool.put("straight", new CityTemplateType());
        StreetTemplatePool.put("branch", new CityTemplateType());
        StreetTemplatePool.put("curve", new CityTemplateType());;
        StreetTemplatePool.put("level_crossing", new CityTemplateType());
        //construct TrainTemplatePool (empty)
        TrainTemplatePool.put("crossing", new CityTemplateType());
        TrainTemplatePool.put("straight", new CityTemplateType());
        TrainTemplatePool.put("branch", new CityTemplateType());
        TrainTemplatePool.put("curve", new CityTemplateType());
        //construct PathTemplatePool (empty)
        PathTemplatePool.put("004004", new CityTemplateType());
        //construct SpecialTemplatePool
        SpecialTemplatePool.put("station", new CityMultiPieceTemplateList(5, 3));
        SpecialTemplatePool.put("bridge", new CityMultiPieceTemplateList(1, 3));
    }

    public static CityStructureTemplatePiece getRandomPiece(String type, String name, String direction, RandomSource random, BlockPos pos) {
    	int pieceX;
    	int pieceZ;
    	RandomSource cityRandom;
    	CityMultiPieceTemplateType multiType;
    	CityTemplateType template;
    	switch (type) {
    		case "house":
    			return HouseTemplatePool.get(name).randomOfDirection(direction, random);
    		case "path":
    		    return PathTemplatePool.get(name).randomOfRandom(random);	
    		case "street":
    		    template = StreetTemplatePool.get(name);
    		    return name == "crossing" ? template.randomOfRandom(random) : name == "curve" || name == "branch" ? template.randomOfDirection(direction, random) : template.randomOfAxis(direction, random);
    		case "train":
    		    template = TrainTemplatePool.get(name);
    		    return name == "crossing" ? template.randomOfRandom(random) : name == "curve" || name == "branch" ? template.randomOfDirection(direction, random) : template.randomOfAxis(direction, random);
    		case "station":
    			pieceX = Integer.parseInt(name.substring(0, name.indexOf("-")));
                pieceZ = Integer.parseInt(name.substring(name.indexOf("-") + 1));
    			cityRandom = CityRandomSourceProcedure.execute(pos.getX() - 48 * pieceX, pos.getZ() - 48 * pieceZ, 1834290525);
    			if (cityRandom.nextBoolean()) {
    				if (direction.equals("n")) {
    					direction = "s";
    				} else {
    					direction = "e";
    				}
    			}
    			multiType = SpecialTemplatePool.get("station").random(cityRandom);
    			return multiType.randomOfDirection(pieceX, pieceZ, direction, random);
    		case "bridge":
    			pieceX = Integer.parseInt(name.substring(0, name.indexOf("-")));
                pieceZ = Integer.parseInt(name.substring(name.indexOf("-") + 1));
    			cityRandom = CityRandomSourceProcedure.execute(pos.getX() - 48 * pieceX, pos.getZ() - 48 * pieceZ, 1945290525);
    			if (cityRandom.nextBoolean()) {
    				if (direction.equals("z")) {
    					direction = "n";
    				} else {
    					direction = "w";
    				}
    			} else {
    				if (direction.equals("z")) {
    					direction = "s";
    				} else {
    					direction = "e";
    				}
    			}
    			multiType = SpecialTemplatePool.get("bridge").random(cityRandom);
    		    CityStructureTemplatePiece piece = multiType.randomOfDirection(pieceX, pieceZ, direction, random);
    			LogManager.getLogger().info("Trying to place bridge piece from location " + piece.location.toString());
    			return piece;
    		default:
    		}
    	return new CityStructureTemplatePiece();	
    	}


    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonMap, ResourceManager resourceManager, ProfilerFiller profiler) {
    	LogManager.getLogger().info("Loading city templates from datapacks");
    	templateCount = 0;
    	showNoStylePackWarning = true;
        jsonMap.entrySet().stream()
        .sorted((entry1, entry2) -> {
        	boolean e1special = entry1.getKey().getPath().substring(0,7).equals("special");
        	boolean e2special = entry2.getKey().getPath().substring(0,7).equals("special");
        	if (e1special && !e2special) return 1;
        	if (!e1special && e2special) return -1;
        	return 0;
        }).forEach((entry) -> {
        	showNoStylePackWarning = false;
        	ResourceLocation id = entry.getKey();
        	JsonElement jsonElement = entry.getValue();
            String resourcePath = id.getPath();
            //LogManager.getLogger().info("Applying to City Template Pool at '" + resourcePath + "'");
            String poolType;
            String poolName;
            if (resourcePath.contains("/")) {
            	poolType = resourcePath.substring(0, resourcePath.indexOf('/'));
                poolName = resourcePath.substring(resourcePath.indexOf('/') + 1);
            } else {
            	poolType = "default";
            	poolName = resourcePath;
            }
            JsonObject json = jsonElement.getAsJsonObject();
            
            CityTemplateType type;

            switch (poolType) {
            	case "house":
            	boolean portalHouse = false;
            	if (poolName.length() > 6) {
            		if (poolName.substring(0, 6).equals("house_")) {
            			poolName = poolName.substring(6);
            		} else if (poolName.substring(0, 6).equals("portal")) {
            			portalHouse = true;
            		}
            	}
            	if (!HouseTemplatePool.containsKey(poolName)) {
            		LogManager.getLogger().warn("Found json file with invalid name '" + poolName + "' at city_template_pool/house");
            		return;
            	}
            	type = HouseTemplatePool.get(poolName);
            	String swappedPoolName = portalHouse ? (poolName.equals("portal_house_x") ? "portal_house_z" : "portal_house_x") : poolName.substring(3) + poolName.substring(0,3);
            	CityTemplateType secondaryType = HouseTemplatePool.get(swappedPoolName);
            	jsonToTemplateType(json, type, resourcePath, secondaryType);
                return;
                
            	case "street":
            	if (poolName.length() > 7 && poolName.substring(0, 7).equals("street_")) {
            		poolName = poolName.substring(7);
            	}
            	if (!StreetTemplatePool.containsKey(poolName)) {
            		LogManager.getLogger().warn("Found json file with invalid name '" + poolName + "' at city_template_pool/street");
            		return;
            	}
            	type = StreetTemplatePool.get(poolName);
            	jsonToTemplateType(json, type, resourcePath, null);
            	return;
            	
            	case "train":
            	if (poolName.length() > 6 && poolName.substring(0, 6).equals("train_")) {
            		poolName = poolName.substring(6);
            	}
            	if (!TrainTemplatePool.containsKey(poolName)) {
            		LogManager.getLogger().warn("Found json file with invalid name '" + poolName + "' at city_template_pool/train");
            		return;
            	}
            	type = TrainTemplatePool.get(poolName);
            	jsonToTemplateType(json, type, resourcePath, null);
            	return;
            	
            	case "path":
            	if (poolName.length() > 5 && poolName.substring(0, 5).equals("path_")) {
            		poolName = poolName.substring(5);
            	}
            	if (!PathTemplatePool.containsKey(poolName)) {
            		LogManager.getLogger().warn("Found json file with invalid name '" + poolName + "' at city_template_pool/train");
            		return;
            	}
            	type = PathTemplatePool.get(poolName);
            	jsonToTemplateType(json, type, resourcePath, null);
            	return;
            	
            	case "special":
            	if (poolName.length() > 6 && poolName.substring(0, 6).equals("train_")) {
            		poolName = poolName.substring(6);
            	}
            	if (poolName.length() > 7 && poolName.substring(0, 7).equals("street_")) {
            		poolName = poolName.substring(7);
            	}
            	if (!SpecialTemplatePool.containsKey(poolName)) {
            		LogManager.getLogger().warn("Found json file with invalid name '" + poolName + "' at city_template_pool/" + resourcePath.substring(0, resourcePath.lastIndexOf('/')));
            	}
            	CityMultiPieceTemplateList multiTypeList = SpecialTemplatePool.get(poolName);
            	jsonToMultiTemplateList(json, multiTypeList, resourcePath);
            	return;

            	case "custom":
            	if (!CustomTemplatePool.containsKey(poolName)) {
            		CustomTemplatePool.put(poolName, new CityTemplateType());
            	}
            	type = CustomTemplatePool.get(poolName);
            	jsonToTemplateType(json, type, resourcePath, null);
            	return;

				case "portal":
				// TODO: Find custom Structure Pool Injectors and apply them to PortalTemplatePoolInjector::setInjectPool (incomplete)
				return;

            	case "jigsaw":
            	// belongs to Room Template Pools
            	return;
            	
            	default:
            	LogManager.getLogger().warn("Found City Template Pool folder with invalid name '" + poolType + "'");
            	return;
            }
            	
                
            
            
        }
        );
        LogManager.getLogger().info("Successfully loaded " + templateCount + " city templates from datapacks");
        templateCount = 0;
        //fallback house structures
        for (Map.Entry<String, CityTemplateType> type : HouseTemplatePool.entrySet()) {
        	String name = type.getKey();
        	templateCount += type.getValue().addFallback(name, 63, new String[] {"_n","_s","_w","_e"});
        }

        //fallback street structures
        for (Map.Entry<String, CityTemplateType> type : StreetTemplatePool.entrySet()) {
        	String name = type.getKey();
        	//System.out.println("Street fallback try " + type.getKey());
        	switch (name) {
        		case "crossing" -> {
        			templateCount += type.getValue().addFallback("street_" + name, 62, new String[] {"","","",""});
        		}
        		case "straight", "level_crossing" -> {
        			templateCount += type.getValue().addFallback("street_" + name, 62, new String[] {"_z","_z","_x","_x"});
        		}
        		default -> {
        			templateCount += type.getValue().addFallback("street_" + name, 62, new String[] {"_n","_s","_w","_e"});
        		}
        	}        	
        }

        //fallback train structures
        for (Map.Entry<String, CityTemplateType> type : TrainTemplatePool.entrySet()) {
        	String name = type.getKey();
        	//System.out.println("Train fallback try " + type.getKey());
        	switch (name) {
        		case "crossing" -> {
        			templateCount += type.getValue().addFallback("train_" + name, 62, new String[] {"","","",""});
        		}
        		case "straight" -> {
        			templateCount += type.getValue().addFallback("train_" + name, 62, new String[] {"_z","_z","_x","_x"});
        		}
        		default -> {
        			templateCount += type.getValue().addFallback("train_" + name, 62, new String[] {"_n","_s","_w","_e"});
        		}
        	}        	
        }

        //fallback path structures
        for (Map.Entry<String, CityTemplateType> type : PathTemplatePool.entrySet()) {
        	String name = type.getKey();
        	templateCount += type.getValue().addFallback("path_" + name, 61, new String[] {"","","",""});
        }

        //fallback station structures
        CityMultiPieceTemplateList stationList = SpecialTemplatePool.get("station");
        if (stationList.isEmpty()) {
        	stationList.add(new CityMultiPieceTemplateType(stationList.sizeX, stationList.sizeZ), 1);
        }
        CityMultiPieceTemplateType[] stations = stationList.getAll();
        for (CityMultiPieceTemplateType station : stations) {
        	templateCount += station.addFallback("station", 61, new String[] {"_z","_z","_x","_x"});
        }

        //fallback bridge structures
        CityMultiPieceTemplateList bridgeList = SpecialTemplatePool.get("bridge");
        if (bridgeList.isEmpty()) {
        	bridgeList.add(new CityMultiPieceTemplateType(bridgeList.sizeX, bridgeList.sizeZ), 1);
        }
        CityMultiPieceTemplateType[] bridges = bridgeList.getAll();
        for (CityMultiPieceTemplateType bridge : bridges) {
        	templateCount += bridge.addFallback("street_bridge", 62, new String[] {"_z","_z","_x","_x"});
        }
        /* for (Map.Entry<String,Map<String,WeightedTemplatePieceList>> size : HouseTemplatePool.entrySet()) {
        	String fallbackSize = size.getKey();
        	for (Map.Entry<String,WeightedTemplatePieceList> direction : size.getValue().entrySet()) {
        		if (direction.getValue().isEmpty()) {
        			CityStructureTemplatePiece finalTemplate = new CityStructureTemplatePiece();
        			String fallbackName = fallbackSize + direction.getKey().charAt(0) + "001";
                    finalTemplate.location = new ResourceLocation("mesophils_cities",fallbackName);
                    finalTemplate.processors = null;
                	finalTemplate.height = 63;
                	finalTemplate.rotation = Rotation.NONE;
                	// System.out.println("Added fallback structure " + finalTemplate.location.toString() + " to house/" + fallbackSize + "/" + direction.getKey());
        			templateCount++;
        			direction.getValue().add(finalTemplate,1);
        		} else {
        			CityStructureTemplatePiece finalTemplate = direction.getValue().random(RandomSource.create());
        			// System.out.println("Found Structure " + finalTemplate.location.toString() + " at house/" + fallbackSize + "/" + direction.getKey());
        		}
        	}
        }
        //put fallback street and train structures

        for (Map.Entry<String,WeightedTemplatePieceList> type : StreetTemplatePool.entrySet()) {
        		if (type.getValue().isEmpty()) {
        			CityStructureTemplatePiece finalTemplate = new CityStructureTemplatePiece();
        			String fallbackName = "street_" + type.getKey();
                    finalTemplate.location = new ResourceLocation("mesophils_cities",fallbackName);
                    finalTemplate.processors = null;
                	finalTemplate.height = 62;
                	finalTemplate.rotation = Rotation.NONE;
                	// System.out.println("Added fallback structure " + finalTemplate.location.toString() + " to street/" + type.getKey());
        			templateCount++;
        			type.getValue().add(finalTemplate,1);
        		} else {
        			CityStructureTemplatePiece finalTemplate = type.getValue().random(RandomSource.create());
        			// System.out.println("Found Structure " + finalTemplate.location.toString() + " at street/" + type.getKey());
        		}
        	}
       for (Map.Entry<String,WeightedTemplatePieceList> type : TrainTemplatePool.entrySet()) {
        		if (type.getValue().isEmpty()) {
        			CityStructureTemplatePiece finalTemplate = new CityStructureTemplatePiece();
        			String fallbackName = "train_" + type.getKey();
                    finalTemplate.location = new ResourceLocation("mesophils_cities",fallbackName);
                    finalTemplate.processors = null;
                	finalTemplate.height = 62;
                	finalTemplate.rotation = Rotation.NONE;
                	// System.out.println("Added fallback structure " + finalTemplate.location.toString() + " to train/" + type.getKey());
        			templateCount++;
        			type.getValue().add(finalTemplate,1);
        		} else {
        			CityStructureTemplatePiece finalTemplate = type.getValue().random(RandomSource.create());
        			// System.out.println("Found Structure " + finalTemplate.location.toString() + " at train/" + type.getKey());
        		}
        	}
       //put fallback path structures
       for (Map.Entry<String,WeightedTemplatePieceList> type : PathTemplatePool.entrySet()) {
        		if (type.getValue().isEmpty()) {
        			CityStructureTemplatePiece finalTemplate = new CityStructureTemplatePiece();
        			String fallbackName = "path_" + type.getKey();
                    finalTemplate.location = new ResourceLocation("mesophils_cities",fallbackName);
                    finalTemplate.processors = null;
                	finalTemplate.height = 61;
                	finalTemplate.rotation = Rotation.NONE;
                	// System.out.println("Added fallback structure " + finalTemplate.location.toString() + " to path/" + type.getKey());
        			templateCount++;
        			type.getValue().add(finalTemplate,1);
        		} else {
        			CityStructureTemplatePiece finalTemplate = type.getValue().random(RandomSource.create());
        			// System.out.println("Found Structure " + finalTemplate.location.toString() + " at path/" + type.getKey());
        		}
        	}
        //put fallback station structures
        for (Map.Entry<String,Map<String, WeightedTemplatePieceList>> structureEntry : SpecialTemplatePool.entrySet()) {
        	String structure = structureEntry.getKey();
        	for (Map.Entry<String,WeightedTemplatePieceList> piece : structureEntry.getValue().entrySet()) {
        		if (piece.getValue().isEmpty()) {
        			CityStructureTemplatePiece finalTemplate = new CityStructureTemplatePiece();
        			String fallbackName = structure + "_a" + piece.getKey();
                    finalTemplate.location = new ResourceLocation("mesophils_cities",fallbackName);
                    finalTemplate.processors = null;
                	finalTemplate.height = 61;
                	finalTemplate.rotation = Rotation.NONE;
                	// System.out.println("Added fallback structure " + finalTemplate.location.toString() + " to station/" + structure + "/" + piece.getKey());
        			templateCount++;
        			piece.getValue().add(finalTemplate,1);
        		} else {
        			CityStructureTemplatePiece finalTemplate = piece.getValue().random(RandomSource.create());
        			// System.out.println("Found structure " + finalTemplate.location.toString() + " at station/" + structure + "/" + piece.getKey());
        		}
        	}
        } */

        if (templateCount == 0) {
        	LogManager.getLogger().info("All needed city templates have been provided; No additional fallback city templates were loaded");
        } else {
        	LogManager.getLogger().warn("Datapacks have not provided all needed city templates; Loaded " + templateCount + " fallback city templates");
        }    
    }

    private void jsonToTemplateType(JsonObject json, CityTemplateType type, String jsonLocation, @Nullable CityTemplateType secondaryType) {
    			int templateHeight = json.has("default_height") ? json.get("default_height").getAsInt() : 63;
            	List<String> templateProcessors = json.has("processors") ? getStringList(json.get("processors")) : new ArrayList<String>();
            	if (!json.has("directions")) {
                		LogManager.getLogger().warn("Invalid nbt file without 'directions' field found at city_template_pool/" + jsonLocation);
               			return;
            	}
            	JsonObject directions = json.get("directions").getAsJsonObject();
            	directionsLoop:
            	for (Map.Entry<String, JsonElement> directionEntry : directions.entrySet()) {
                String directionName = directionEntry.getKey();
                JsonObject direction = directionEntry.getValue().getAsJsonObject();

                if (direction.has("processors")) {
                    templateProcessors = combineProcessors(templateProcessors,getStringList(direction.get("processors")));
                }
                if (direction.has("default_height")) {
                    templateHeight = direction.get("default_height").getAsInt();
                }
                JsonArray templates = direction.get("elements").getAsJsonArray();
                
                for (JsonElement templateElement : templates) {
                	JsonObject template = templateElement.getAsJsonObject();
                	if (!template.has("location")) {
                		LogManager.getLogger().warn("Invalid nbt file location of element with direction '" + directionName + "' found at city_template_pool/" + jsonLocation);
               			continue;
                	}
                    CityStructureTemplatePiece finalTemplate = new CityStructureTemplatePiece();
                    finalTemplate.location = new ResourceLocation(template.get("location").getAsString());
                    finalTemplate.processors = getResourceLocationArray(template.has("processors") ? combineProcessors(templateProcessors,getStringList(template.get("processors"))) : templateProcessors);
                    int weight = template.has("weight") ? template.get("weight").getAsInt() : 1;
                	finalTemplate.height = template.has("height") ? template.get("height").getAsInt() : templateHeight;
                	if (secondaryType != null && directionName.contains("rotate")) {
                		//house behavior
                		if (directionName.equals("rotate") || directionName.equals("rotate_n") || directionName.equals("rotate_z")) {
                			if (type.add(finalTemplate, weight, "z") && secondaryType.add(finalTemplate.rotated(Rotation.COUNTERCLOCKWISE_90), weight, "x")) {
		                		templateCount++;
		                	} else {
		                		LogManager.getLogger().warn("Invalid direction name '" + directionName + "' found at city_template_pool/" + jsonLocation);
		                		continue directionsLoop;
		                	}
                		} else {
                			if (type.add(finalTemplate, weight, "x") && secondaryType.add(finalTemplate.rotated(Rotation.CLOCKWISE_90), weight, "z")) {
		                		templateCount++;
		                	} else {
		                		LogManager.getLogger().warn("Invalid direction name '" + directionName + "' found at city_template_pool/" + jsonLocation);
		                		continue directionsLoop;
		                	}
                		}
                		
                	} else {
                		//non-house (normal) behavior
	                	if (type.add(finalTemplate, weight, directionName)) {
	                		templateCount++;
	                	} else {
	                		LogManager.getLogger().warn("Invalid direction name '" + directionName + "' found at city_template_pool/" + jsonLocation);
	                		continue directionsLoop;
	                	}
                	}
                }
          }
    }

    private void jsonToMultiTemplateList(JsonObject json, CityMultiPieceTemplateList multiTypeList, String jsonLocation) {
    	if (!json.has("elements")) {
            LogManager.getLogger().warn("Invalid nbt file without 'elements' field found at city_template_pool/" + jsonLocation);
            return;
        }
        JsonArray elements = json.getAsJsonArray("elements");
        for (JsonElement jsonElement : elements) {
        	CityMultiPieceTemplateType multiTemplateType = new CityMultiPieceTemplateType(multiTypeList.sizeX, multiTypeList.sizeZ);
            JsonObject element = jsonElement.getAsJsonObject();
            if (!element.has("directions")) {
                LogManager.getLogger().warn("Invalid element without 'directions' field found in nbt file at city_template_pool/" + jsonLocation);
               	return;
            }
            JsonObject directions = element.get("directions").getAsJsonObject();
            directionsLoop:
            for (Map.Entry<String, JsonElement> directionEntry : directions.entrySet()) {
                String directionName = directionEntry.getKey();
                JsonObject direction = directionEntry.getValue().getAsJsonObject();
                for (Map.Entry<String, JsonElement> pieceEntry : direction.entrySet()) {
                	int pieceX = Integer.parseInt(pieceEntry.getKey().substring(0, pieceEntry.getKey().indexOf("-")));
                	int pieceZ = Integer.parseInt(pieceEntry.getKey().substring(pieceEntry.getKey().indexOf("-") + 1));
                	String location = pieceEntry.getValue().getAsString();
                	if (location.contains(":")) {
                		location = location.substring(location.indexOf(":") + 1);
                	}
                	String type = location.substring(0, location.indexOf("/"));
                	String name = location.substring(location.indexOf("/") + 1);
                	Map<String,CityTemplateType> pool;
                	switch (type) {
                		case "custom" : pool = CustomTemplatePool; break;
                		case "house" : pool = HouseTemplatePool; break;
                		case "street" : pool = StreetTemplatePool; break;
                		case "train" : pool = TrainTemplatePool; break;
                		default : LogManager.getLogger().warn("Invalid City template pool location '" + location + "' referenced in city_template_pool/" + jsonLocation); continue;
                	}
                	CityTemplateType templateType;
                	if (pool.containsKey(name)) {
                		templateType = pool.get(name);
                	} else {
                		LogManager.getLogger().warn("Invalid City template pool location '" + location + "' referenced in city_template_pool/" + jsonLocation);
                		continue;
                	}
                	List<WeightedTemplatePieceList> directionLists = templateType.getAllDirectionLists();

                	for (WeightedTemplatePieceList list : directionLists) {
	                	List<Object[]> pieces = list.getAllWithWeights();
						if (pieces == null) continue;
	                	for (Object[] o : pieces) {
	                		CityStructureTemplatePiece piece = (CityStructureTemplatePiece) o[0];
	                		int weight = (Integer) o[1];
		                	if (multiTemplateType.add(pieceZ, pieceX, piece, weight, directionName, (byte) 0)) {
		                		
		                	} else {
		                		LogManager.getLogger().warn("Invalid direction name '" + directionName + "' found at city_template_pool/" + jsonLocation);
		                		continue directionsLoop;
		                	}
	                	}
	
	                	
	
	                	
	                	
                	}
                }
            }
            int weight = element.has("weight") ? element.get("weight").getAsInt() : 1;
            multiTypeList.add(multiTemplateType, weight);
    	}
    }

    

    private void addTemplates(JsonObject pool, WeightedTemplatePieceList templatePool, List<String> templateProcessors, int templateHeight, Rotation rotation) {
    	        /* if (templatePool = null) {
    	        	// LogManager.getLogger().warn("Invalid pool found while processing city templates");
    	        	return;
    	        } */
    	        
    	
    	
                if (pool.has("processors")) {
                    templateProcessors = getStringList(pool.get("processors"));
                }
                if (pool.has("default_height")) {
                    templateHeight = pool.get("default_height").getAsInt();
                }
                JsonArray templates = pool.get("elements").getAsJsonArray();
                
                for (JsonElement templateElement : templates) {
                	JsonObject template = templateElement.getAsJsonObject();
                	if (template.get("location").getAsString() == null) {
                		continue;
                	}
                    CityStructureTemplatePiece finalTemplate = new CityStructureTemplatePiece();
                    finalTemplate.location = new ResourceLocation(template.get("location").getAsString());
                    finalTemplate.processors = getResourceLocationArray(template.has("processors") ? combineProcessors(templateProcessors, getStringList(template.get("processors"))) : templateProcessors);
                    int weight = template.has("weight") ? template.get("weight").getAsInt() : 1;
                	finalTemplate.height = template.has("height") ? template.get("height").getAsInt() : templateHeight;
                	finalTemplate.rotation = rotation;
                	templateCount++;
                	templatePool.add(finalTemplate, weight);
                	 
                	
                }
    }

    private List<String> getStringList(JsonElement json) {
		List<String> list = new ArrayList<>();
    	if (json.isJsonArray()) {
    		for (JsonElement element: json.getAsJsonArray()) {
    			list.add(element.getAsString());
    		}
    	} else {
    		list.add(json.getAsString());
    	}
    	return list;
    }

    private ResourceLocation[] getResourceLocationArray(List<String> strings) {
    	int i = 0;
    	ResourceLocation[] array = new ResourceLocation[strings.size()];
    	for (String string: strings) {
    		array[i] = new ResourceLocation(string);
    		i++;
    	}
    	return array;
    	}

    private List<String> combineProcessors(List<String> list1, List<String> list2) {
    	list1.addAll(list2);
    	return list1;
    }
}