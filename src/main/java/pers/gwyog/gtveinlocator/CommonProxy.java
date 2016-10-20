package pers.gwyog.gtveinlocator;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import pers.gwyog.gtveinlocator.compat.LoadedModHelper;
import pers.gwyog.gtveinlocator.config.ModConfig;
import pers.gwyog.gtveinlocator.items.ItemVeinLocator;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e) {
		new ModConfig(e);
		LoadedModHelper.init();
        ModItems.init();
        ModItems.registerItems();
        ModLoots.init();
	}
	
    public void init(FMLInitializationEvent e) {

    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}

