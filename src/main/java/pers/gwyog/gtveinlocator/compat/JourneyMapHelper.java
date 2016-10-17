package pers.gwyog.gtveinlocator.compat;

import java.awt.Color;
import java.util.Collection;

import journeymap.client.JourneymapClient;
import journeymap.client.model.Waypoint;
import journeymap.client.properties.WaypointProperties;
import journeymap.client.waypoint.WaypointStore;
import net.minecraft.client.resources.I18n;

public class JourneyMapHelper {
	
	public static boolean isWaypointExist(int posX, int posZ, int dimId) {
		WaypointProperties waypointProperties = JourneymapClient.getWaypointProperties();
	    Collection<Waypoint> waypoints = WaypointStore.instance().getAll();
		for (Waypoint wp : waypoints)
			if (wp.getX()==posX && wp.getZ()==posZ && wp.getDimensions().contains(dimId))
				return true;
		return false;
	}
	
	public static boolean addWaypoint(String name, int posX, int posY, int posZ, int dimId) {
	    try {
			Waypoint waypoint = new Waypoint(name, posX, posY, posZ, Color.white, Waypoint.Type.Normal, dimId);
			waypoint.setRandomColor();
			WaypointStore.instance().save(waypoint);
	    }
		catch (Throwable t) {
			t.printStackTrace();
			return false;
	    }
	    return true;
	}
}
