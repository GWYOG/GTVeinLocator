package pers.gwyog.gtveinlocator;

import ic2.api.item.ElectricItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import pers.gwyog.gtveinlocator.compat.LoadedModHelper;

public class ModLoots {

	public static void init() {
        //add chest loot
        ItemStack chargedVeinLocator = new ItemStack(ModItems.itemVeinLocator, 1);
	    ElectricItem.manager.charge(chargedVeinLocator, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, false);
        ChestGenHooks.getInfo("villageBlacksmith").addItem(new WeightedRandomChestContent(chargedVeinLocator, 1, 2, 4));
        if (LoadedModHelper.isJourneyMapLoaded || LoadedModHelper.isXaeroMinimapLoaded) {
            ItemStack chargedAdvancedVeinLocator = new ItemStack(ModItems.itemVeinLocator, 1);
    	    ElectricItem.manager.charge(chargedAdvancedVeinLocator, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, false);
            ChestGenHooks.getInfo("villageBlacksmith").addItem(new WeightedRandomChestContent(chargedAdvancedVeinLocator, 1, 1, 1));
        }
	}
}
