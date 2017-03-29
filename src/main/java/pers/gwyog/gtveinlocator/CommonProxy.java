package pers.gwyog.gtveinlocator;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.common.MinecraftForge;
import pers.gwyog.gtveinlocator.compat.LoadedModHelper;
import pers.gwyog.gtveinlocator.config.ModConfig;
import pers.gwyog.gtveinlocator.items.ItemVeinLocator;
import pers.gwyog.gtveinlocator.network.GTVLNetwork;
import pers.gwyog.gtveinlocator.util.ClientVeinNameHelper;
import pers.gwyog.gtveinlocator.util.GTOreLayerHelper;

public class CommonProxy {
    
    public void preInit(FMLPreInitializationEvent e) {
        new ModConfig(e);
        ModItems.init();
        ModLoots.init();
        new GTVLNetwork();
    }
    
    public void init(FMLInitializationEvent e) {
    	// we put it here just in case gt6's hv sensor has not been set in the preInit period
        GTVeinLocator.gtModHelper.registerItems();
    }

    public void postInit(FMLPostInitializationEvent e) {
        
    }
    
    public void onServerStart(FMLServerStartedEvent e) {
        GTOreLayerHelper.init();
    }
}

