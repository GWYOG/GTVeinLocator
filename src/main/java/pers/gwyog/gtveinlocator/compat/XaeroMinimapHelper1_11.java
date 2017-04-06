package pers.gwyog.gtveinlocator.compat;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

import com.minimap.XaeroMinimap;
import com.minimap.minimap.Minimap;
import com.minimap.minimap.Waypoint;
import com.minimap.settings.ModSettings;

import net.minecraft.client.resources.I18n;
import pers.gwyog.gtveinlocator.api.modhelpers.IXaeroMinimapHelper;
import pers.gwyog.gtveinlocator.config.ModConfig;

public class XaeroMinimapHelper1_11 implements IXaeroMinimapHelper {

	@Override
	public void init() {
        try {
            Class clazzXMap = Class.forName("com.minimap.XaeroMinimap");
            Class clazzXMapMinimap = Class.forName("com.minimap.minimap.Minimap");
            Class clazzXMapWaypoint = Class.forName("com.minimap.minimap.Waypoint");
            Class clazzXMapWaypointSet = Class.forName("com.minimap.minimap.WaypointSet");
            Class clazzXMapModSettings = Class.forName("com.minimap.settings.ModSettings");
            Class clazzXMapWaypointWorld = Class.forName("com.minimap.minimap.WaypointWorld");
            Field fieldWaypoints = clazzXMapMinimap.getField("waypoints");
            Field fieldList = clazzXMapWaypointSet.getField("list");
            Field fieldEnchantColors = clazzXMapModSettings.getField("ENCHANT_COLORS");
            Field fieldSettings = clazzXMap.getField("settings");
            Method methodGetCurrentWorld = clazzXMapMinimap.getMethod("getCurrentWorld");
            Method methodSaveWaypoints = clazzXMapModSettings.getMethod("saveWaypoints", clazzXMapWaypointWorld);
            Constructor constructorWaypoint = clazzXMapWaypoint.getConstructor(int.class, int.class, int.class, String.class, String.class, int.class);
            XaeroMinimapHelper.failedCompat = false;
        } catch (Exception e) {}	
	}

	@Override
	public boolean isWaypointExist(int posX, int posZ, boolean forceAdd) {
        if (forceAdd) {
            Iterator<Waypoint> iter = Minimap.waypoints.list.iterator();
            while (iter.hasNext()) {
                Waypoint wp = iter.next();
                if (wp.x==posX && wp.z==posZ)
                    if (wp.getName().equals(I18n.format("gtveinlocator.ore.mix.empty")) || wp.getName().equals(I18n.format("gtveinlocator.ore.mix.unknown")))
                        iter.remove();
                    else
                        return true;
            }
        }
        else {
            for (Waypoint wp : Minimap.waypoints.list)
                if (wp.x==posX && wp.z==posZ)
                    return true;
        }
        return false;
	}

	@Override
	public boolean addWaypoint(String name, int posX, int posY, int posZ) {
    	int color = ModConfig.waypointColorXaeroMinimap == -1? (int)(Math.random() * ModSettings.ENCHANT_COLORS.length): ModConfig.waypointColorXaeroMinimap;
        Waypoint wp = new Waypoint(posX, posY, posZ, name, ModConfig.waypointSymbolXaeroMinimap, color);
        Minimap.waypoints.list.add(wp);
        try {
          XaeroMinimap.settings.saveWaypoints(Minimap.getCurrentWorld());
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
	}

}
