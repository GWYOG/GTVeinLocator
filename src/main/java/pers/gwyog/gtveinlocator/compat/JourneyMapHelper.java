package pers.gwyog.gtveinlocator.compat;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import journeymap.client.JourneymapClient;
import journeymap.client.model.Waypoint;
import journeymap.client.properties.WaypointProperties;
import journeymap.client.waypoint.WaypointStore;
import net.minecraft.client.resources.I18n;

public class JourneyMapHelper {
	public static boolean failedCompat = true;
	
	public static void init() {
		if (!Loader.isModLoaded("journeymap"))
			return;
		try {
			Class clazzJMapClient = Class.forName("journeymap.client.JourneymapClient");
			Class clazzJMapWaypointStore = Class.forName("journeymap.client.waypoint.WaypointStore");
			Class clazzJMapWaypointProperties = Class.forName("journeymap.client.properties.WaypointProperties");
			Class clazzJMapWaypoint = Class.forName("journeymap.client.model.Waypoint");
			Class clazzJMapWaypointType = Class.forName("journeymap.client.model.Waypoint$Type");
			Method methodGetWaypointProperties = clazzJMapClient.getMethod("getWaypointProperties");
			Method methodInstance = clazzJMapWaypointStore.getMethod("instance");
			Method methodGetAll = clazzJMapWaypointStore.getMethod("getAll");
			Method methodSetRandomColor = clazzJMapWaypoint.getMethod("setRandomColor");
			Method methodSave = clazzJMapWaypointStore.getMethod("save", clazzJMapWaypoint);
			Constructor constuctorWaypoint = clazzJMapWaypoint.getConstructor(String.class, int.class, int.class, int.class, Color.class, clazzJMapWaypointType, Integer.class);
			failedCompat = false;
		} catch (Exception e) {}
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean isWaypointExist(int posX, int posZ, int dimId, boolean forceAdd) {
		WaypointProperties waypointProperties = JourneymapClient.getWaypointProperties();
	    Collection<Waypoint> waypoints = WaypointStore.instance().getAll();
	    if (forceAdd) {
	    	for (Waypoint wp : waypoints)
	    		if (wp.getX()==posX && wp.getZ()==posZ && wp.getDimensions().contains(dimId))
	    			if (wp.getName().equals(I18n.format("ore.mix.empty")) || wp.getName().equals(I18n.format("ore.mix.unknown")))
	    				WaypointStore.instance().remove(wp);
	    			else
	    				return true;
	    }
	    else
	    	for (Waypoint wp : waypoints)
	    		if (wp.getX()==posX && wp.getZ()==posZ && wp.getDimensions().contains(dimId))
	    			return true;
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean addWaypoint(String name, int posX, int posY, int posZ, int dimId) {
		try {
			Waypoint waypoint = new Waypoint(name, posX, posY, posZ, Color.white, Waypoint.Type.Normal, dimId);
			waypoint.setRandomColor();
			WaypointStore.instance().save(waypoint); 
		}
		catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
