package pers.gwyog.gtveinlocator;

import ic2.api.item.ElectricItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import pers.gwyog.gtveinlocator.compat.LoadedModHelper;
import pers.gwyog.gtveinlocator.config.ModConfig;

public class ModLoots {

	public static void init() {
        //add chest loot
		if (ModConfig.veinLocatorEnabled && ModConfig.lootVeinLocatorEnabled) {
			ChestGenHooks chestGenHooks= ChestGenHooks.getInfo(ModConfig.lootVeinLocatorChest);
			if (chestGenHooks != null) {
				ItemStack chargedVeinLocator = new ItemStack(ModItems.itemVeinLocator, 1);
				ElectricItem.manager.charge(chargedVeinLocator, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, false);
				chestGenHooks.addItem(new WeightedRandomChestContent(chargedVeinLocator, 1, ModConfig.lootVeinLocatorMinimumChance, ModConfig.lootVeinLocatorMaximumChance));     
			}
		}
        if (ModConfig.advancedVeinLocatorEnabled && ModConfig.lootAdvancedVeinLocatorEnabled) {
			ChestGenHooks chestGenHooks= ChestGenHooks.getInfo(ModConfig.lootAdvancedVeinLocatorChest);
			if (chestGenHooks != null) {
			    ItemStack chargedAdvancedVeinLocator = new ItemStack(ModItems.itemAdvancedVeinLocator, 1);
	    	    ElectricItem.manager.charge(chargedAdvancedVeinLocator, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, false);
	            chestGenHooks.addItem(new WeightedRandomChestContent(chargedAdvancedVeinLocator, 1, ModConfig.lootAdvancedVeinLocatorMinimumChance, ModConfig.lootAdvancedVeinLocatorMaximumChance));
			}
        }
        if (ModConfig.eliteVeinLocatorEnabled && ModConfig.lootEliteVeinLocatorEnabled) {
        	ChestGenHooks chestGenHooks= ChestGenHooks.getInfo(ModConfig.lootEliteVeinLocatorChest);
			if (chestGenHooks != null) {
			    ItemStack chargedEliteVeinLocator = new ItemStack(ModItems.itemEliteVeinLocator, 1);
	    	    ElectricItem.manager.charge(chargedEliteVeinLocator, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, false);
	            chestGenHooks.addItem(new WeightedRandomChestContent(chargedEliteVeinLocator, 1, ModConfig.lootEliteVeinLocatorMinimumChance, ModConfig.lootEliteVeinLocatorMaximumChance));
			}
        }
	}
}
