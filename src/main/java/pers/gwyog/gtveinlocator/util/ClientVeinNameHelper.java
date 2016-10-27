package pers.gwyog.gtveinlocator.util;

import java.lang.reflect.Field;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import gregtech.common.GT_Worldgen_GT_Ore_Layer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;

public class ClientVeinNameHelper {
	public static boolean basicSupport = true;
	public static boolean mapInit = false;

	@SubscribeEvent
	public void onTick(ClientTickEvent event) {
		if(!mapInit) {
			// in case that the user is using old version of GT
			try {
				Class clazzGTOreLayer = Class.forName("gregtech.common.GT_Worldgen_GT_Ore_Layer");
				Field fieldOverworld= clazzGTOreLayer.getField("mOverworld");
				Field fieldNether = clazzGTOreLayer.getField("mNether");
				Field fieldEnd = clazzGTOreLayer.getField("mEnd");
				Field fieldName = clazzGTOreLayer.getField("mWorldGenName");
				Field fieldList = clazzGTOreLayer.getField("sList");
			} catch (Exception e) {
				basicSupport = false;
			} 
			
			//map initialization start
			if (basicSupport) {
				GTVeinNameHelper.registerVeinName("ore.mix.empty");
				GTVeinNameHelper.registerVeinName("ore.mix.unknown");
				for (GT_Worldgen_GT_Ore_Layer worldGen : GT_Worldgen_GT_Ore_Layer.sList)
					if (worldGen.mEnabled) 
						GTVeinNameHelper.registerVeinName(worldGen.mWorldGenName);
			}
			mapInit = true;
		}
	}
}
