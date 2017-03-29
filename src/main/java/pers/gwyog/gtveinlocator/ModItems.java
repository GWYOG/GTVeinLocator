package pers.gwyog.gtveinlocator;

import java.lang.reflect.Method;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
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
    
}
