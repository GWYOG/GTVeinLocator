package pers.gwyog.gtveinlocator.compat;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

import net.minecraft.client.resources.I18n;
import pers.gwyog.gtveinlocator.api.modhelpers.IXaeroMinimapHelper;
import pers.gwyog.gtveinlocator.config.ModConfig;
import xaero.common.minimap.Minimap;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.minimap.XaeroMinimap;

public class XaeroMinimapHelper1_12 implements IXaeroMinimapHelper {

	@Override
	public void init() {
        try {
            Class clazzXMap = Class.forName("xaero.minimap.XaeroMinimap");
            Class clazzXMapMinimap = Class.forName("xaero.common.minimap.Minimap");
            Class clazzXMapWaypoint = Class.forName("xaero.common.minimap.waypoints.Waypoint");
            Class clazzXMapWaypointSet = Class.forName("xaero.common.minimap.waypoints.WaypointSet");
            Class clazzXMapModSettings = Class.forName("xaero.common.settings.ModSettings");
            Class clazzXMapWaypointWorld = Class.forName("xaero.common.minimap.waypoints.WaypointWorld");
            Field fieldWaypoints = clazzXMapMinimap.getField("waypoints");
            Field fieldList = clazzXMapWaypointSet.getField("list");
            Field fieldEnchantColors = clazzXMapModSettings.getField("ENCHANT_COLORS");
            Field fieldSettings = clazzXMap.getDeclaredField("settings");
            Method methodGetCurrentWorld = clazzXMapMinimap.getMethod("getCurrentWorld");
            Method methodSaveWaypoints = clazzXMapModSettings.getMethod("saveWaypoints", clazzXMapWaypointWorld);
            Constructor constructorWaypoint = clazzXMapWaypoint.getConstructor(int.class, int.class, int.class, String.class, String.class, int.class);
            XaeroMinimapHelper.failedCompat = false;
        } catch (Exception e) {}		
	}

	@Override
	public boolean isWaypointExist(int posX, int posZ, boolean forceAdd) {
        Minimap minimap = XaeroMinimap.instance.getInterfaces().getMinimap();
		if (forceAdd) {
            Iterator<Waypoint> iter = minimap.waypoints.list.iterator();
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
            for (Waypoint wp : minimap.waypoints.list)
                if (wp.x==posX && wp.z==posZ)
                    return true;
        }
        return false;
	}

	@Override
	public boolean addWaypoint(String name, int posX, int posY, int posZ) {
        Minimap minimap = XaeroMinimap.instance.getInterfaces().getMinimap();
        int color = ModConfig.waypointColorXaeroMinimap == -1? (int)(Math.random() * XaeroMinimap.instance.getSettings().ENCHANT_COLORS.length): ModConfig.waypointColorXaeroMinimap;
        Waypoint wp = new Waypoint(posX, posY, posZ, name, ModConfig.waypointSymbolXaeroMinimap, color);
        minimap.waypoints.list.add(wp);
        try {
          XaeroMinimap.instance.getSettings().saveWaypoints(minimap.getCurrentWorld());
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
	}

}
