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

import akka.dispatch.sysmsg.Failed;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;

public class XaeroMinimapHelper {
	public static boolean failedCompat = true;
	
	public static void init() {
		if (!Loader.isModLoaded("XaeroMinimap"))
			return;
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
			failedCompat = false;
		} catch (Exception e) {}
	}
	
	//For XaeroMinimap, Minimap.waypoints.list is always the list of waypoints of the current world.
	@SideOnly(Side.CLIENT)
	public static boolean isWaypointExist(int posX, int posZ, boolean forceAdd) {
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
	
	@SideOnly(Side.CLIENT)
	public static boolean addWaypoint(String name, int posX, int posY, int posZ) {
		Waypoint wp = new Waypoint(posX, posY, posZ, name, "X", (int)(Math.random() * ModSettings.ENCHANT_COLORS.length));
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
