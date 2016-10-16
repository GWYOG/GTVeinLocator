package pers.gwyog.gtveinlocator;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import pers.gwyog.gtveinlocator.items.ItemAdvancedVeinLocator;
import pers.gwyog.gtveinlocator.items.ItemVeinLocator;

public class ModItems {
	public static Item itemVeinLocator;
	public static Item itemAdvancedVeinLocator;
	
    public static void init() {
    	itemVeinLocator = new ItemVeinLocator("veinLocator");
    	itemVeinLocator.setCreativeTab(GTVeinLocator.tabGTVeinLocator);
    	if (Loader.isModLoaded("journeymap")) {
    		itemAdvancedVeinLocator = new ItemAdvancedVeinLocator("advancedVeinLocator");
    		itemAdvancedVeinLocator.setCreativeTab(GTVeinLocator.tabGTVeinLocator);
    	}
    }
    
    public static void registerItems() {
        GameRegistry.registerItem(itemVeinLocator, "veinLocator");
        if (Loader.isModLoaded("journeymap")) 
			GameRegistry.registerItem(itemAdvancedVeinLocator, "advancedVeinLocator");
    	if (Loader.isModLoaded("gregtech")) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(ModItems.itemVeinLocator), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{
    				"SSS", "SwS", "SRS",
    				Character.valueOf('S'), OrePrefixes.plate.get(Materials.Steel),
    				Character.valueOf('R'), OrePrefixes.plate.get(Materials.RedAlloy)
    		});
    		if (itemAdvancedVeinLocator!=null) {
        		GT_ModHandler.addCraftingRecipe(new ItemStack(ModItems.itemAdvancedVeinLocator), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{
        				"AAA", "AwA", "ACA",
        				Character.valueOf('A'), OrePrefixes.plate.get(Materials.Aluminium),
        				Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Basic)
        		});
    		}
    	}
    }
}
