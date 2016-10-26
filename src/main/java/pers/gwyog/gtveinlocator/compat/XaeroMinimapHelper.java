package pers.gwyog.gtveinlocator.compat;

import java.io.IOException;

import com.minimap.XaeroMinimap;
import com.minimap.minimap.Minimap;
import com.minimap.minimap.Waypoint;
import com.minimap.settings.ModSettings;

public class XaeroMinimapHelper {
	
	//For XaeroMinimap, Minimap.waypoints.list is always the list of waypoints of the current world.
	public static int isWaypointExist(int posX, int posZ) {
		try {
			for (Waypoint wp : Minimap.waypoints.list)
				if (wp.x==posX && wp.z==posZ)
					return 1;
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	
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
