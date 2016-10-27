package pers.gwyog.gtveinlocator;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import pers.gwyog.gtveinlocator.config.ModConfig;
import pers.gwyog.gtveinlocator.items.ItemVeinLocator;
import pers.gwyog.gtveinlocator.util.GTOreLayerHelper;

@Mod(modid = GTVeinLocator.MODID, name = GTVeinLocator.MODNAME, version = GTVeinLocator.VERSION, dependencies = "required-after:gregtech")
public class GTVeinLocator {	
	public static final String MODID = "gtveinlocator";
	public static final String MODNAME = "GT Vein-Locator";
	public static final String VERSION = "v1.0.2";
	
    @SidedProxy(clientSide="pers.gwyog.gtveinlocator.proxies.ClientProxy", serverSide="pers.gwyog.gtveinlocator.proxies.ServerProxy")
    public static CommonProxy proxy;
    
    @Mod.Instance("gtveinlocator")
    public static GTVeinLocator instance;
    
    public static CreativeTabs tabGTVeinLocator = new CreativeTabs("GTVeinLocator") {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return ModItems.itemVeinLocator;
        }
    };
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        this.proxy.preInit(e);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent e) {
        this.proxy.init(e);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        this.proxy.postInit(e);
    }
    
    @EventHandler
    public void onServerStart(FMLServerStartedEvent e) {
    	this.proxy.onServerStart(e);
    }
    
}