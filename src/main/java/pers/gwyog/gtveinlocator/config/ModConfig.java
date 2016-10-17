package pers.gwyog.gtveinlocator.config;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ModConfig {
	
	 private static Configuration config;
	 private static Logger logger;
	 public static int waypointYLevelForJourneyMap;
	 public static int waypointYLevelForXaeroMinimap;
	 
	 public ModConfig(FMLPreInitializationEvent event) {
		 logger = event.getModLog();
	     config = new Configuration(event.getSuggestedConfigurationFile());
	     config.load();
	     load();
	 }

	 public static void load()
	 {        
		 logger.info("Started loading config.");           
	        
		 String yLevelDesForJourneyMap = "The Y level of the auto-generated waypoints from the Advanced Vein-Locator for JourneyMap.";        
		 waypointYLevelForJourneyMap = config.get(Configuration.CATEGORY_GENERAL, "waypointYLevelForJourneyMap", 70, yLevelDesForJourneyMap).getInt();
		 String yLevelDesForXaeroMinimap = "The Y level of the auto-generated waypoints from the Advanced Vein-Locator for Xaero's Minimap.";        
		 waypointYLevelForXaeroMinimap = config.get(Configuration.CATEGORY_GENERAL, "waypointYLevelForXaeroMinimap", 70, yLevelDesForXaeroMinimap).getInt();           
		 
		 config.save();        
		 logger.info("Finished loading config.");
	 }
    
	 public static Logger getLogger() {  
		 return logger;
	 }

}
