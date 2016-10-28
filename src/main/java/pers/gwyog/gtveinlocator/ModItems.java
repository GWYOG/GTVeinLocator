package pers.gwyog.gtveinlocator;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import pers.gwyog.gtveinlocator.compat.LoadedModHelper;
import pers.gwyog.gtveinlocator.config.ModConfig;
import pers.gwyog.gtveinlocator.items.ItemAdvancedVeinLocator;
import pers.gwyog.gtveinlocator.items.ItemEliteVeinLocator;
import pers.gwyog.gtveinlocator.items.ItemVeinLocator;

public class ModItems {
	public static Item itemVeinLocator;
	public static Item itemAdvancedVeinLocator;
	public static Item itemEliteVeinLocator;
	
    public static void init() {
    	if (ModConfig.veinLocatorEnabled) {
        	itemVeinLocator = new ItemVeinLocator("veinLocator", ModConfig.veinLocatorMaxCharge, ModConfig.veinLocatorTransferLimit, ModConfig.veinLocatorTier, ModConfig.locatorsUseEnergy);
        	itemVeinLocator.setCreativeTab(GTVeinLocator.tabGTVeinLocator);
    	}
    	if (ModConfig.advancedVeinLocatorEnabled) {
    		itemAdvancedVeinLocator = new ItemAdvancedVeinLocator("advancedVeinLocator", ModConfig.advancedVeinLocatorMaxCharge, ModConfig.advancedVeinLocatorTransferLimit, ModConfig.advancedVeinLocatorTier, ModConfig.locatorsUseEnergy);
    		itemAdvancedVeinLocator.setCreativeTab(GTVeinLocator.tabGTVeinLocator);
    	}
    	if (ModConfig.eliteVeinLocatorEnabled) {
    		itemEliteVeinLocator = new ItemEliteVeinLocator("eliteVeinLocator", ModConfig.eliteVeinLocatorMaxCharge, ModConfig.eliteVeinLocatorTransferLimit, ModConfig.eliteVeinLocatorTier, ModConfig.locatorsUseEnergy);
    		itemEliteVeinLocator.setCreativeTab(GTVeinLocator.tabGTVeinLocator);
    	}
    }
    
    public static void registerItems() {
    	if (ModConfig.veinLocatorEnabled) {
    		GameRegistry.registerItem(itemVeinLocator, "veinLocator");
        	if (!ModConfig.recipeVeinLocatorDisabled) 
        		GT_ModHandler.addCraftingRecipe(new ItemStack(ModItems.itemVeinLocator), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{
        			"SWS", "WwW", "SCS",
        			Character.valueOf('S'), OrePrefixes.plate.get(Materials.Steel),
        			Character.valueOf('W'), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1L),
        			Character.valueOf('C'), ItemList.Circuit_Basic.get(1L, new Object[0])
        		});
    	}
    	if (ModConfig.advancedVeinLocatorEnabled) {
    		GameRegistry.registerItem(itemAdvancedVeinLocator, "advancedVeinLocator");
    		if (!ModConfig.recipeAdvancedVeinLocatorDisabled)
    			GT_ModHandler.addCraftingRecipe(new ItemStack(ModItems.itemAdvancedVeinLocator), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{
    				"AWA", "WwW", "ACA",
    				Character.valueOf('A'), OrePrefixes.plate.get(Materials.Aluminium),
    				Character.valueOf('W'), GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.AnyCopper, 1L),
    				Character.valueOf('C'), ItemList.Circuit_Good.get(1L, new Object[0])
    			});
    	}
    	if (ModConfig.eliteVeinLocatorEnabled) {
    		GameRegistry.registerItem(itemEliteVeinLocator, "eliteVeinLocator");
    		if (!ModConfig.recipeEliteVeinLocatorDisabled)
    			GT_ModHandler.addCraftingRecipe(new ItemStack(ModItems.itemEliteVeinLocator), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{
        				"SWS", "WwW", "SCS",
        				Character.valueOf('S'), OrePrefixes.plate.get(Materials.StainlessSteel),
        				Character.valueOf('W'), GT_OreDictUnificator.get(OrePrefixes.cableGt04, Materials.Silver, 1L),
        				Character.valueOf('C'), ItemList.Circuit_Advanced.get(1L, new Object[0])
        			});
    	}
    }
}
