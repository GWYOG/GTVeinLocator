package pers.gwyog.gtveinlocator.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import gregtech.common.GT_Worldgen_GT_Ore_Layer;
import net.minecraftforge.client.model.AdvancedModelLoader;

public class GTOreLayerHelper {
	public static boolean basicSupport = true;
	public static boolean gcSupport = true;
	public static HashMap<List<Short>, String> mapGTOverworldOreLayer = new HashMap<List<Short>, String>();
	public static HashMap<List<Short>, String> mapGTNetherOreLayer = new HashMap<List<Short>, String>();
	public static HashMap<List<Short>, String> mapGTEndOreLayer = new HashMap<List<Short>, String>();
	public static HashMap<List<Short>, String> mapGTMoonOreLayer = new HashMap<List<Short>, String>();
	public static HashMap<List<Short>, String> mapGTMarsOreLayer = new HashMap<List<Short>, String>();
	public static short minLevelOreOverworld = 255;
	public static short maxLevelOreOverworld = 0;
	public static short minLevelOreNether = 255;
	public static short maxLevelOreNether = 0;
	public static short minLevelOreEnd = 255;
	public static short maxLevelOreEnd = 0;
	public static short minLevelOreMoon = 255;
	public static short maxLevelOreMoon = 0;
	public static short minLevelOreMars = 255;
	public static short maxLevelOreMars = 0;
	
	public enum WorldNameEnum{
		overworld, nether, end, moon, mars, unknown;
	}
	
	public static void init() {
		// in case that the user is using old version of GT
		Class clazzGTOreLayer = null;
		try {
			clazzGTOreLayer = Class.forName("gregtech.common.GT_Worldgen_GT_Ore_Layer");
			Field fieldOverworld= clazzGTOreLayer.getField("mOverworld");
			Field fieldNether = clazzGTOreLayer.getField("mNether");
			Field fieldEnd = clazzGTOreLayer.getField("mEnd");
			Field fieldEnabled = clazzGTOreLayer.getField("mEnabled");
			Field fieldPrimaryMeta = clazzGTOreLayer.getField("mPrimaryMeta");
			Field fieldSecondaryMeta = clazzGTOreLayer.getField("mSecondaryMeta");
			Field fieldBetweenMeta = clazzGTOreLayer.getField("mBetweenMeta");
			Field fieldSporadicMeta = clazzGTOreLayer.getField("mSporadicMeta");
			Field fieldName = clazzGTOreLayer.getField("mWorldGenName");
			Field fieldList = clazzGTOreLayer.getField("sList");
		} catch (Exception e) {
			basicSupport = false;
		} 
		if (clazzGTOreLayer != null && clazzGTOreLayer != null) 
			try {
				Field fieldMoon = clazzGTOreLayer.getField("mMoon");
				Field fieldMars = clazzGTOreLayer.getField("mMars");
			} catch (Exception e) {
				gcSupport = false;
			}
		
		// initialization starts
		if (basicSupport) {
			GTVeinNameHelper.registerVeinName("gtveinlocator.ore.mix.empty");
			GTVeinNameHelper.registerVeinName("gtveinlocator.ore.mix.unknown");
			for (GT_Worldgen_GT_Ore_Layer worldGen : GT_Worldgen_GT_Ore_Layer.sList) {
				if (worldGen.mEnabled) {
					GTVeinNameHelper.registerVeinName("gtveinlocator." + worldGen.mWorldGenName);
					List<Short> componentList = new LinkedList<Short>();
					componentList.add(worldGen.mPrimaryMeta);
					componentList.add(worldGen.mSecondaryMeta);
					componentList.add(worldGen.mBetweenMeta);
					componentList.add(worldGen.mSporadicMeta);
					if (worldGen.mOverworld) {
						mapGTOverworldOreLayer.put(componentList, "gtveinlocator." + worldGen.mWorldGenName);
						minLevelOreOverworld = worldGen.mMinY<minLevelOreOverworld? worldGen.mMinY: minLevelOreOverworld;
						maxLevelOreOverworld = worldGen.mMaxY>maxLevelOreOverworld? worldGen.mMaxY: maxLevelOreOverworld;
					}
					if (worldGen.mNether) {
						mapGTNetherOreLayer.put(componentList, "gtveinlocator." + worldGen.mWorldGenName);
						minLevelOreNether = worldGen.mMinY<minLevelOreNether? worldGen.mMinY: minLevelOreNether;
						maxLevelOreNether = worldGen.mMaxY>maxLevelOreNether? worldGen.mMaxY: maxLevelOreNether;
					}
					if (worldGen.mEnd) {
						mapGTEndOreLayer.put(componentList, "gtveinlocator." + worldGen.mWorldGenName);
						minLevelOreEnd = worldGen.mMinY<minLevelOreEnd? worldGen.mMinY: minLevelOreEnd;
						maxLevelOreEnd = worldGen.mMaxY>maxLevelOreEnd? worldGen.mMaxY: maxLevelOreEnd;
					}
					if (gcSupport) {
						if (worldGen.mMoon) {
							mapGTMoonOreLayer.put(componentList, "gtveinlocator." + worldGen.mWorldGenName); 
							minLevelOreMoon = worldGen.mMinY<minLevelOreMoon? worldGen.mMinY: minLevelOreMoon;
							maxLevelOreMoon = worldGen.mMaxY>maxLevelOreMoon? worldGen.mMaxY: maxLevelOreMoon;
						}
						if (worldGen.mMars) {
							mapGTMarsOreLayer.put(componentList, "gtveinlocator." + worldGen.mWorldGenName); 
							minLevelOreMars = worldGen.mMinY<minLevelOreMars? worldGen.mMinY: minLevelOreMars;
							maxLevelOreMars = worldGen.mMaxY>maxLevelOreMars? worldGen.mMaxY: maxLevelOreMars;
						}
					}
				}
			}
		}
	}
	
	public static short getMinOreLevel(WorldNameEnum worldName) {
		switch (worldName) {
		case overworld:
			return minLevelOreOverworld;
		case nether:
			return minLevelOreNether;
		case end:
			return minLevelOreEnd;
		case moon:
			return minLevelOreMoon;
		case mars:
			return minLevelOreMars;
		default:
			//never return this
			return 2;
		}
	}
	
	public static short getMaxOreLevel(WorldNameEnum worldName) {
		switch (worldName) {
		case overworld:
			return maxLevelOreOverworld;
		case nether:
			return maxLevelOreNether;
		case end:
			return maxLevelOreEnd;
		case moon:
			return maxLevelOreMoon;
		case mars:
			return maxLevelOreMars;
		default:
			//never return this
			return 255;
		}
	}
	
	
	//list.size() must equal to 4
	public static String judgeOreLayerName(List<Short> list, WorldNameEnum worldName) {
		switch (worldName) {
		case overworld:
			return judgeOreLayerName(list, mapGTOverworldOreLayer);
		case nether:
			return judgeOreLayerName(list, mapGTNetherOreLayer);
		case end:
			return judgeOreLayerName(list, mapGTEndOreLayer);
		case moon:
			return judgeOreLayerName(list, mapGTMoonOreLayer);
		case mars:
			return judgeOreLayerName(list, mapGTMarsOreLayer);
		default:			
			//never return this
			return "gtveinlocator.ore.mix.unknown";
		}
	}
		
	public static String judgeOreLayerName(List<Short> list, HashMap<List<Short>, String> map) {
		boolean flag;
		if (list==null || list.isEmpty())
			return "gtveinlocator.ore.mix.empty";	
		for (List<Short> componentList : map.keySet()) {
			flag = true;
			//judge if variable componentList contains variable list
			for (short meta : list)
				if (!componentList.contains(meta)) {
					flag = false;
					break;
				}
			if (!flag)
				continue;
			//judge the primary and secondary ore types in the vein
			if (advancedJudge(list, componentList)) {
				return map.get(componentList);
			}
		}
		return "gtveinlocator.ore.mix.unknown";
	}
	
	public static boolean advancedJudge(List<Short>list, List<Short>componentList) {
		//to-do: complete this
		return true;
	}
	
	
}
