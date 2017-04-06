package pers.gwyog.gtveinlocator.compat;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import pers.gwyog.gtveinlocator.api.modhelpers.IXaeroMinimapHelper;

public class XaeroMinimapHelper {
    public static boolean failedCompat = true;
	public static IXaeroMinimapHelper xaeroMinimapHelper;
    
    public static void init() {
        if (!Loader.isModLoaded("XaeroMinimap"))
            return;
        xaeroMinimapHelper = new XaeroMinimapHelper1_11();
        xaeroMinimapHelper.init();
        if (failedCompat) {
        	xaeroMinimapHelper = new XaeroMinimapHelper1_12();
        	xaeroMinimapHelper.init();
        }
    }
    
    @SideOnly(Side.CLIENT)
    public static boolean isWaypointExist(int posX, int posZ, boolean forceAdd) {
        return xaeroMinimapHelper.isWaypointExist(posX, posZ, forceAdd);
    }
    
    @SideOnly(Side.CLIENT)
    public static boolean addWaypoint(String name, int posX, int posY, int posZ) {
        return xaeroMinimapHelper.addWaypoint(name, posX, posY, posZ);
    }
}
