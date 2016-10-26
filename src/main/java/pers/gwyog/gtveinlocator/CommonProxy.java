package pers.gwyog.gtveinlocator;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import pers.gwyog.gtveinlocator.compat.LoadedModHelper;
import pers.gwyog.gtveinlocator.config.ModConfig;
import pers.gwyog.gtveinlocator.items.ItemVeinLocator;
import pers.gwyog.gtveinlocator.network.GTVLNetwork;
import pers.gwyog.gtveinlocator.util.GTOreLayerHelper;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e) {
		new ModConfig(e);
        ModItems.init();
        ModItems.registerItems();
        ModLoots.init();
        new GTVLNetwork();
	}
	
    public void init(FMLInitializationEvent e) {

    }

    public void postInit(FMLPostInitializationEvent e) {
    	
    }
}

