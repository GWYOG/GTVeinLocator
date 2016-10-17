package pers.gwyog.gtveinlocator;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import pers.gwyog.gtveinlocator.config.ModConfig;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e) {
		new ModConfig(e);
        ModItems.init();
        ModItems.registerItems();
	}
	
    public void init(FMLInitializationEvent e) {

    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}

