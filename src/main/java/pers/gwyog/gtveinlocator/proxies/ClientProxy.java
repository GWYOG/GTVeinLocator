package pers.gwyog.gtveinlocator.proxies;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import pers.gwyog.gtveinlocator.CommonProxy;
import pers.gwyog.gtveinlocator.compat.JourneyMapHelper;
import pers.gwyog.gtveinlocator.compat.LoadedModHelper;
import pers.gwyog.gtveinlocator.compat.XaeroMinimapHelper;
import pers.gwyog.gtveinlocator.util.ClientVeinNameHelper;

public class ClientProxy extends CommonProxy{
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        FMLCommonHandler.instance().bus().register(new ClientVeinNameHelper());
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        JourneyMapHelper.init();
        XaeroMinimapHelper.init();
        LoadedModHelper.init();
    }
    
}