package pers.gwyog.gtveinlocator.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ModConfig {
	 private static Configuration config;
	 private static Logger logger;
	 public static double veinLocatorMaxCharge;
	 public static double veinLocatorTransferLimit;
	 public static int veinLocatorTier;
	 public static double veinLocatorSingleUseCost;
	 public static double advancedVeinLocatorMaxCharge;
	 public static double advancedVeinLocatorTransferLimit;
	 public static int advancedVeinLocatorTier;
	 public static double advancedVeinLocatorSingleUseCost;
	 public static int waypointYLevelForJourneyMap;
	 public static int waypointYLevelForXaeroMinimap;
	 
	 public ModConfig(FMLPreInitializationEvent event) {
		 logger = event.getModLog();
	     config = new Configuration(event.getSuggestedConfigurationFile());
	     load();
	 }

	 public static void load()
	 {        
		 logger.info("Started loading config.");           
	     config.load();
	     
	     config.addCustomCategoryComment("Locator Basic Data", "You can set the basic data of the vein locators of different levels to balance them.");
	     config.addCustomCategoryComment("Compatibility", "Things which are related with other mods can be configured here.");
	     
		 //vein locator data
		 String veinLocatorMaxChargeDes = "The max EU charge-amount of the vein locator.";
		 String veinLocatorTransferLimitDes = "The EU transfer limit of the vein locator.";
		 String veinLocatorTierDes = "The tier of the vein locator.";
		 String veinLocatorSingleUseCostDes = "The EU cost of a single use of the vein locator";
		 String advancedVeinLocatorMaxChargeDes = "The max EU charge-amount of the advanced vein locator.";
		 String advancedVeinLocatorTransferLimitDes = "The EU transfer limit of the advanced vein locator.";
		 String advancedVeinLocatorTierDes = "The tier of the advanced vein locator.";
		 String advancedVeinLocatorSingleUseCostDes = "The EU cost of a single use of the advanced vein locator";
		 
		 Property propertyVeinLocatorMaxCharge = config.get("Locator Data", "veinLocatorMaxCharge", 100000.0D, veinLocatorMaxChargeDes);
		 veinLocatorMaxCharge = getSafeDoubleFromProperty(propertyVeinLocatorMaxCharge, 1.0D, Double.MAX_VALUE);
		 Property propertyVeinLocatorTransferLimit = config.get("Locator Data", "veinLocatorTransferLimit", 128.0D, veinLocatorTransferLimitDes);
		 veinLocatorTransferLimit = getSafeDoubleFromProperty(propertyVeinLocatorTransferLimit, 1.0D, Double.MAX_VALUE);
		 Property propertyVeinLocatorTier = config.get("Locator Data", "veinLocatorTier", 1, veinLocatorTierDes);
		 veinLocatorTier = getSafeIntFromProperty(propertyVeinLocatorTier, 1, Integer.MAX_VALUE);
		 Property propertyVeinLocatorSingleUseCost = config.get("Locator Data", "veinLocatorSingleUseCost", 1000.0D, veinLocatorSingleUseCostDes);
		 veinLocatorSingleUseCost = getSafeDoubleFromProperty(propertyVeinLocatorSingleUseCost, 0.0D, veinLocatorMaxCharge);
		 
		 Property propertyAdvancedVeinLocatorMaxCharge = config.get("Locator Data", "advancedVeinLocatorMaxCharge", 1000000.0D, advancedVeinLocatorMaxChargeDes);
		 advancedVeinLocatorMaxCharge = getSafeDoubleFromProperty(propertyAdvancedVeinLocatorMaxCharge, 1.0D, Double.MAX_VALUE);
		 Property propertyAdvancedVeinLocatorTransferLimit = config.get("Locator Data", "advancedVeinLocatorTransferLimit", 512.0D, advancedVeinLocatorTransferLimitDes);
		 advancedVeinLocatorTransferLimit = getSafeDoubleFromProperty(propertyAdvancedVeinLocatorTransferLimit, 1.0D, Double.MAX_VALUE);
		 Property propertyAdvancedVeinLocatorTier = config.get("Locator Data", "advancedVeinLocatorTier", 2, advancedVeinLocatorTierDes);
		 advancedVeinLocatorTier = getSafeIntFromProperty(propertyAdvancedVeinLocatorTier, 1, Integer.MAX_VALUE);
		 Property propertyAdvancedVeinLocatorSingleUseCost = config.get("Locator Data", "advancedVeinLocatorSingleUseCost", 8000.0D, advancedVeinLocatorSingleUseCostDes);
		 advancedVeinLocatorSingleUseCost = getSafeDoubleFromProperty(propertyAdvancedVeinLocatorSingleUseCost, 0.0D, advancedVeinLocatorMaxCharge);
		 
		 //compatibility
		 String yLevelDesForJourneyMap = "The Y level of the auto-generated waypoints from the Advanced Vein-Locator for JourneyMap.";        
		 String yLevelDesForXaeroMinimap = "The Y level of the auto-generated waypoints from the Advanced Vein-Locator for Xaero's Minimap.";   
		 
		 Property propertyYLevelForJourneyMap = config.get("Compatibility", "waypointYLevelForJourneyMap", 70, yLevelDesForJourneyMap);
		 waypointYLevelForJourneyMap = getSafeIntFromProperty(propertyYLevelForJourneyMap, 0, 255);
		 Property propertyYLevelForXaeroMinimap = config.get("Compatibility", "waypointYLevelForXaeroMinimap", 70, yLevelDesForXaeroMinimap);
		 waypointYLevelForXaeroMinimap = getSafeIntFromProperty(propertyYLevelForXaeroMinimap, 0, 255);          	 
		 
		 config.save();        
		 logger.info("Finished loading config.");
	 }
    
	 public static Logger getLogger() {  
		 return logger;
	 }
	 
	 public static double getSafeDoubleFromProperty(Property property, double min, double max) {
		 double temp = property.getDouble();
		 if (temp<min || temp>max) {
			 property.setToDefault();
			 temp = property.getDouble();
		 }
		 return temp;
	 }
	 
	 public static int getSafeIntFromProperty(Property property, int min, int max) {
		 int temp = property.getInt();
		 if (temp<min || temp>max) {
			 property.setToDefault();
			 temp = property.getInt();
		 }
		 return temp;
	 }

}
