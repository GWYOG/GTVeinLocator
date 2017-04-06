package pers.gwyog.gtveinlocator;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import pers.gwyog.gtveinlocator.api.modhelpers.GT5ModHelper;
import pers.gwyog.gtveinlocator.api.modhelpers.GT6ModHelper;
import pers.gwyog.gtveinlocator.api.modhelpers.IGTModHelper;
import pers.gwyog.gtveinlocator.config.ModConfig;
import pers.gwyog.gtveinlocator.items.ItemVeinLocator;
import pers.gwyog.gtveinlocator.util.GTOreLayerHelper;

@Mod(modid = GTVeinLocator.MODID, name = GTVeinLocator.MODNAME, version = GTVeinLocator.VERSION, dependencies = "required-after:gregtech")
public class GTVeinLocator {    
    public static final String MODID = "gtveinlocator";
    public static final String MODNAME = "GT Vein-Locator";
    public static final String VERSION = "v1.0.7";
    public static IGTModHelper gtModHelper; 
    
    // default is GT5, it will become "GT6" when GT6 is installed
    public static String GTVersion = "GT5";
    
    @SidedProxy(clientSide="pers.gwyog.gtveinlocator.proxies.ClientProxy", serverSide="pers.gwyog.gtveinlocator.proxies.ServerProxy")
    public static CommonProxy proxy;
    
    @Mod.Instance(MODID)
    public static GTVeinLocator instance;
    
    public static CreativeTabs tabGTVeinLocator = new CreativeTabs("GTVeinLocator") {      
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return null;
        } 
        
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getIconItemStack() {
            ItemStack chargedVeinLocator = new ItemStack(ModItems.itemVeinLocator, 1);
            ElectricItem.manager.charge(chargedVeinLocator, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, false);
            return chargedVeinLocator;
        }
    };
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
    	initGTHelper();
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
    
    public void initGTHelper() {
        try {
            Class clazzGT6API = Class.forName("gregapi.GT_API");
            GTVersion = "GT6";
        } catch (ClassNotFoundException e) {}
        gtModHelper = GTVersion.equals("GT6") ? new GT6ModHelper() : new GT5ModHelper();
    }
    
}