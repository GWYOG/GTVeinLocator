package pers.gwyog.gtveinlocator.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ModConfig {
     private static Configuration config;
     private static Logger logger;
     public static boolean locatorsUseEnergy;
     public static boolean veinLocatorEnabled;
     public static boolean advancedVeinLocatorEnabled;
     public static boolean eliteVeinLocatorEnabled;
     public static double veinLocatorMaxCharge;
     public static double veinLocatorTransferLimit;
     public static int veinLocatorTier;
     public static double veinLocatorSingleUseCost;
     public static double advancedVeinLocatorMaxCharge;
     public static double advancedVeinLocatorTransferLimit;
     public static int advancedVeinLocatorTier;
     public static double advancedVeinLocatorSingleUseCost;
     public static double eliteVeinLocatorMaxCharge;
     public static double eliteVeinLocatorTransferLimit;
     public static int eliteVeinLocatorTier;
     public static double eliteVeinLocatorSingleUseCost;
     public static int waypointYLevelAdvancedLocator;
     public static int waypointYLevelEliteLocator;
     public static int waypointColorJourneyMap;
     public static int waypointColorXaeroMinimap;
     public static String waypointSymbolXaeroMinimap;
     public static boolean lootVeinLocatorEnabled;
     public static String lootVeinLocatorChest;
     public static int lootVeinLocatorMinimumChance;
     public static int lootVeinLocatorMaximumChance;
     public static boolean lootAdvancedVeinLocatorEnabled;
     public static String lootAdvancedVeinLocatorChest;
     public static int lootAdvancedVeinLocatorMinimumChance;
     public static int lootAdvancedVeinLocatorMaximumChance;
     public static boolean lootEliteVeinLocatorEnabled;
     public static String lootEliteVeinLocatorChest;
     public static int lootEliteVeinLocatorMinimumChance;
     public static int lootEliteVeinLocatorMaximumChance;
     public static boolean recipeVeinLocatorDisabled;
     public static boolean recipeAdvancedVeinLocatorDisabled;
     public static boolean recipeEliteVeinLocatorDisabled;
     public static boolean creativeTabIconCompass;
     public static List<Integer> overworldLikeDimensions;
     public static List<Integer> netherLikeDimensions;
     public static List<Integer> endLikeDimensions;
     public static boolean matchMisplacement;
     
     public ModConfig(FMLPreInitializationEvent event) {
         logger = event.getModLog();
         config = new Configuration(event.getSuggestedConfigurationFile());
         load();
     }

     public static void load()
     {        
         logger.info("Started loading config.");           
         config.load();
         
         config.addCustomCategoryComment("Locator Enabled", "You can set if specific locator is enabled or whether it consumes energy when using.");
         config.addCustomCategoryComment("Locator Use Energy", "You can configure a setting here to make the mod in an esay mode.");
         config.addCustomCategoryComment("Locator Data", "You can set the basic data of the vein locators of different levels to balance them.");
         config.addCustomCategoryComment("Compatibility", "Things that are related with other mods can be configured here.");
         config.addCustomCategoryComment("Loot Tweaks", "You can set if the locators would generate in the loot chests and the possibility to find them.\nTips: The basic name of the chests are: \n    mineshaftCorridor, pyramidDesertyChest, pyramidJungleChest, pyramidJungleDispenser \n    strongholdCorridor, strongholdLibrary, strongholdCrossing, villageBlacksmith \n    bonusChest, dungeonChest \nNote: If you want to make specific locator appear in multiple kinds of loot-chests, you should use MineTweaker.");
         config.addCustomCategoryComment("Recipe Disabled", "You can disable the recipe of specific locator here.");
         config.addCustomCategoryComment("Creative Tab Icon", "For those whose client crashes everytime switching to GTVL's creative tab, you can now change the icon to minecraft's compass to avoid crashing.");
         config.addCustomCategoryComment("Dimension White List", "Elite locator will only function in these dimensions and GalactiCraft's planets. This catagory aims mainly at compatibility for bukkit plugins like Multiverse-Core.");
         
         //locator enabled
         String veinLocatorEnabledDes = "Set to false will disable the basic vein locator in-game which means you won't see it any more.";
         String advancedVeinLocatorEnabledDes = "Set to false will disable the advanced vein locator in-game which means you won't see it any more.";
         String eliteVeinLocatorEnabledDes = "Set to false will disable the elite vein locator in-game which means you won't see it any more.";  
         veinLocatorEnabled = config.get("Locator Enabled", "veinLocatorEnabled", true, veinLocatorEnabledDes).getBoolean();
         advancedVeinLocatorEnabled = config.get("Locator Enabled", "advancedVeinLocatorEnabled", true, advancedVeinLocatorEnabledDes).getBoolean();
         eliteVeinLocatorEnabled = config.get("Locator Enabled", "eliteVveinLocatorEnabled", true, advancedVeinLocatorEnabledDes).getBoolean();

         
         //simple mode
         String locatorsUseEnergyDes = "Set to false will make all the locators do not consume energy when using them.";
         locatorsUseEnergy = config.get("Locator Use Energy", "locatorsUseEnergy", true, locatorsUseEnergyDes).getBoolean();

         
         //vein locator data
         String veinLocatorMaxChargeDes = "The max EU charge-amount of the vein locator.";
         String veinLocatorTransferLimitDes = "The EU transfer limit of the basic vein locator.";
         String veinLocatorTierDes = "The tier of the basic vein locator.";
         String veinLocatorSingleUseCostDes = "The EU cost of a single use of the basic vein locator";
         String advancedVeinLocatorMaxChargeDes = "The max EU charge-amount of the advanced vein locator.";
         String advancedVeinLocatorTransferLimitDes = "The EU transfer limit of the advanced vein locator.";
         String advancedVeinLocatorTierDes = "The tier of the advanced vein locator.";
         String advancedVeinLocatorSingleUseCostDes = "The EU cost of a single use of the advanced vein locator";
         String eliteVeinLocatorMaxChargeDes = "The max EU charge-amount of the elite vein locator.";
         String eliteVeinLocatorTransferLimitDes = "The EU transfer limit of the elite vein locator.";
         String eliteVeinLocatorTierDes = "The tier of the elite vein locator.";
         String eliteVeinLocatorSingleUseCostDes = "The EU cost of a single use of the elite vein locator";
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
         Property propertyEliteVeinLocatorMaxCharge = config.get("Locator Data", "eliteVeinLocatorMaxCharge", 10000000.0D, eliteVeinLocatorMaxChargeDes);
         eliteVeinLocatorMaxCharge = getSafeDoubleFromProperty(propertyEliteVeinLocatorMaxCharge, 1.0D, Double.MAX_VALUE);
         Property propertyEliteVeinLocatorTransferLimit = config.get("Locator Data", "eliteVeinLocatorTransferLimit", 2048.0D, eliteVeinLocatorTransferLimitDes);
         eliteVeinLocatorTransferLimit = getSafeDoubleFromProperty(propertyEliteVeinLocatorTransferLimit, 1.0D, Double.MAX_VALUE);
         Property propertyEliteVeinLocatorTier = config.get("Locator Data", "eliteVeinLocatorTier", 3, eliteVeinLocatorTierDes);
         eliteVeinLocatorTier = getSafeIntFromProperty(propertyEliteVeinLocatorTier, 1, Integer.MAX_VALUE);
         Property propertyEliteVeinLocatorSingleUseCost = config.get("Locator Data", "eliteVeinLocatorSingleUseCost", 64000.0D, eliteVeinLocatorSingleUseCostDes);
         eliteVeinLocatorSingleUseCost = getSafeDoubleFromProperty(propertyEliteVeinLocatorSingleUseCost, 0.0D, eliteVeinLocatorMaxCharge);
    
         
         //compatibility
         String yLevelAdvancedLocatorDes = "The Y level of the auto-generated waypoints from the Advanced Vein-Locator.";   
         String yLevelEliteLocatorDes = "The Y level of the auto-generated empty or unknown waypoints from the Elite Vein-Locator.";   
         String waypointColorJourneyMapDes = "The color of the waypoints on the JourneyMap added by advanced and elite locators. Please use hexadecimal. For example, 0xFFFFFF means white. Set to -1 will use the random color.";
         String waypointColorXaeroMinimapDes = "The color of the waypoints on the XaeroMinimap added by advanced and elite locators. The range is 0-15, which corresponds with the 16 colors of XaeroMinimap's waypoints. Set to -1 will use the random color.";    
         String waypointSymbolXaeroMinimapDes = "The symbol of the waypoints on the XaeroMinimap. That is to say, this is the string symbol shown above the waypoints in the minimap. The default value is 'X'.";
         Property propertyYLevelAdvancedLocator = config.get("Compatibility", "waypointYLevelAdvancedLocator", 70, yLevelAdvancedLocatorDes);
         waypointYLevelAdvancedLocator = getSafeIntFromProperty(propertyYLevelAdvancedLocator, 0, 255);
         Property propertyYLevelEliteLocator = config.get("Compatibility", "waypointYLevelEliteLocator", 70, yLevelEliteLocatorDes);
         waypointYLevelEliteLocator = getSafeIntFromProperty(propertyYLevelEliteLocator, 0, 255);               
         Property propertyWaypointColorJourneyMap = config.get("Compatibility", "waypointColorJourneyMap", "-1", waypointColorJourneyMapDes);
         waypointColorJourneyMap = getSafeColorFromProperty(propertyWaypointColorJourneyMap, -1, Integer.MAX_VALUE);
         Property propertyWaypointColorXaeroMinimap = config.get("Compatibility", "waypointColorXaeroMinimap", -1, waypointColorXaeroMinimapDes);
         waypointColorXaeroMinimap = getSafeIntFromProperty(propertyWaypointColorXaeroMinimap, -1, 15);
         waypointSymbolXaeroMinimap = config.get("Compatibility", "waypointSymbolXaeroMinimap", "X", waypointSymbolXaeroMinimapDes).getString();
        
         //loot tweaks
         String lootVeinLocatorEnabledDes = "Set to false will disable the chest-gen of the basic vein locators.";
         String lootVeinLocatorChestDes = "The name of the chest where the basic vein locator would generate. Tips: The name list can be found in the previous lines.";
         String lootVeinLocatorMinimumChanceDes = "The minimum chance that the basic vein locator would generate in the loot-chest.";
         String lootVeinLocatorMaximumChanceDes = "The maximum chance that the basic vein locator would generate in the loot-chest.";
         String lootAdvancedVeinLocatorEnabledDes = "Set to false will disable the chest-gen of the advanced vein locators.";
         String lootAdvancedVeinLocatorChestDes = "The name of the chest where the advanced vein locator would generate. Tips: The name list can be found in the previous lines.";
         String lootAdvancedVeinLocatorMinimumChanceDes = "The minimum chance that the advanced vein locator would generate in the loot-chest.";
         String lootAdvancedVeinLocatorMaximumChanceDes = "The maximum chance that the advanced vein locator would generate in the loot-chest.";
         String lootEliteVeinLocatorEnabledDes = "Set to false will disable the chest-gen of the elite vein locators.";
         String lootEliteVeinLocatorChestDes = "The name of the chest where the elite vein locator would generate. Tips: The name list can be found in the previous lines.";
         String lootEliteVeinLocatorMinimumChanceDes = "The minimum chance that the elite vein locator would generate in the loot-chest.";
         String lootEliteVeinLocatorMaximumChanceDes = "The maximum chance that the elite vein locator would generate in the loot-chest.";
         lootVeinLocatorEnabled = config.get("Loot Tweaks", "lootVeinLocatorEnabled", true, lootVeinLocatorEnabledDes).getBoolean();
         lootVeinLocatorChest = config.get("Loot Tweaks", "lootVeinLocatorChest", "villageBlacksmith", lootVeinLocatorChestDes).getString();
         Property propertyLootVeinLocatorMinimumChance = config.get("Loot Tweaks", "lootVeinLocatorMinimumChance", 2, lootVeinLocatorMinimumChanceDes);
         lootVeinLocatorMinimumChance = getSafeIntFromProperty(propertyLootVeinLocatorMinimumChance, 1, Integer.MAX_VALUE);
         Property propertyLootVeinLocatorMaximumChance = config.get("Loot Tweaks", "lootVeinLocatorMaximumChance", 5, lootVeinLocatorMaximumChanceDes);
         lootVeinLocatorMaximumChance = getSafeIntFromProperty(propertyLootVeinLocatorMaximumChance, lootVeinLocatorMinimumChance, Integer.MAX_VALUE);
         lootAdvancedVeinLocatorEnabled = config.get("Loot Tweaks", "lootAdvancedVeinLocatorEnabled", true, lootAdvancedVeinLocatorEnabledDes).getBoolean();
         lootAdvancedVeinLocatorChest = config.get("Loot Tweaks", "lootAdvancedVeinLocatorChest", "villageBlacksmith", lootAdvancedVeinLocatorChestDes).getString();
         Property propertyLootAdvancedVeinLocatorMinimumChance = config.get("Loot Tweaks", "lootAdvancedVeinLocatorMinimumChance", 1, lootAdvancedVeinLocatorMinimumChanceDes);
         lootAdvancedVeinLocatorMinimumChance = getSafeIntFromProperty(propertyLootAdvancedVeinLocatorMinimumChance, 1, Integer.MAX_VALUE);
         Property propertyLootAdvancedVeinLocatorMaximumChance = config.get("Loot Tweaks", "lootAdvancedVeinLocatorMaximumChance", 1, lootAdvancedVeinLocatorMaximumChanceDes);
         lootAdvancedVeinLocatorMaximumChance = getSafeIntFromProperty(propertyLootAdvancedVeinLocatorMaximumChance, lootAdvancedVeinLocatorMinimumChance, Integer.MAX_VALUE);
         lootEliteVeinLocatorEnabled = config.get("Loot Tweaks", "lootEliteVeinLocatorEnabled", false, lootEliteVeinLocatorEnabledDes).getBoolean();
         lootEliteVeinLocatorChest = config.get("Loot Tweaks", "lootEliteVeinLocatorChest", "strongholdLibrary", lootEliteVeinLocatorChestDes).getString();
         Property propertyLootEliteVeinLocatorMinimumChance = config.get("Loot Tweaks", "lootEliteVeinLocatorMinimumChance", 1, lootEliteVeinLocatorMinimumChanceDes);
         lootEliteVeinLocatorMinimumChance = getSafeIntFromProperty(propertyLootEliteVeinLocatorMinimumChance, 1, Integer.MAX_VALUE);
         Property propertyLootEliteVeinLocatorMaximumChance = config.get("Loot Tweaks", "lootEliteVeinLocatorMaximumChance", 1, lootEliteVeinLocatorMaximumChanceDes);
         lootEliteVeinLocatorMaximumChance = getSafeIntFromProperty(propertyLootEliteVeinLocatorMaximumChance, lootEliteVeinLocatorMinimumChance, Integer.MAX_VALUE);
         
         
         //recipe disabled
         String recipeVeinLocatorDisabledDes = "Set to true will disable the origin recipe of the basic vein locator";
         String recipeAdvancedVeinLocatorDisabledDes = "Set to true will disable the origin recipe of the advanced vein locator";
         String recipeEliteVeinLocatorDisabledDes = "Set to true will disable the origin recipe of the elite vein locator"; 
         recipeVeinLocatorDisabled = config.get("Recipe Disabled", "recipeVeinLocatorDisabled", false, recipeVeinLocatorDisabledDes).getBoolean();
         recipeAdvancedVeinLocatorDisabled = config.get("Recipe Disabled", "recipeAdvancedVeinLocatorDisabled", false, recipeAdvancedVeinLocatorDisabledDes).getBoolean();
         recipeEliteVeinLocatorDisabled = config.get("Recipe Disabled", "recipeEliteVeinLocatorDisabled", false, recipeEliteVeinLocatorDisabledDes).getBoolean();
         
         
         //creative tab icon
         String creativeTabCompassDes = "Set this to true will set GTVL's creative icon to minecraft's compass";
         creativeTabIconCompass = config.get("Creative Tab Icon", "creativeTabIconCompass", false, creativeTabCompassDes).getBoolean();
         
         //dimension white list
         String overworldLikeDimensionsDes = "This list of dimension ids for overworld-like dimensions which elite vein locator should work in.";
         String netherLikeDimensionsDes = "This list of dimension ids for nether-like dimensions which elite vein locator should work in.";
         String endLikeDimensionsDes = "This list of dimension ids for end-like dimensions which elite vein locator should work in.";         
         overworldLikeDimensions = Arrays.asList(ArrayUtils.toObject(config.get("Dimension White List", "overworldLikeDimensionWhitelist", new int[]{0}, overworldLikeDimensionsDes).getIntList()));
         netherLikeDimensions = Arrays.asList(ArrayUtils.toObject(config.get("Dimension White List", "netherLikeDimensionWhitelist", new int[]{-1}, netherLikeDimensionsDes).getIntList()));
         endLikeDimensions = Arrays.asList(ArrayUtils.toObject(config.get("Dimension White List", "endLikeDimensionWhitelist", new int[]{1}, endLikeDimensionsDes).getIntList()));
         
         //misc
         String matchMisplacementDes = "Set this to true to match the vein location misplacement issue before GT5.09.29";
         matchMisplacement = config.get("Match Misplacement", "matchMisplacement", false, matchMisplacementDes).getBoolean();
         
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
     
     public static int getSafeColorFromProperty(Property property, int min, int max) {
    	 String colorString = property.getString();
    	 if (colorString.startsWith("0x"))
    		 colorString = colorString.replace("0x", "");
    	 try {
    		 int color = Integer.parseInt(colorString, 16);
    		 if (color<min || color>max)
    			 return -1;
    		 else
    			 return color;
    	 }
    	 catch (NumberFormatException e) {
    		 property.setToDefault();
    		 return -1;
    	 }
     }
     
}
