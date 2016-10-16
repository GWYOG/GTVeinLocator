package pers.gwyog.gtveinlocator.items;

import java.awt.Color;
import java.util.Collection;

import journeymap.client.JourneymapClient;
import journeymap.client.model.Waypoint;
import journeymap.client.properties.WaypointProperties;
import journeymap.client.waypoint.WaypointStore;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemAdvancedVeinLocator extends ItemVeinLocator {

	public ItemAdvancedVeinLocator(String name) {
		super(name);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		int searchRange = getSearchRangeFromNBT(stack);
		if (player.isSneaking() && !world.isRemote) 
			switchMode(stack, player, searchRange);
		else if (!player.isSneaking() && world.isRemote) {
			int indexX = getClosestIndex(player.posX);
			int indexZ = getClosestIndex(player.posZ);
			int count = 0;
			int dimID = player.dimension; 
			int targetX, targetZ;
			boolean setFlag = true;
			WaypointProperties waypointProperties = JourneymapClient.getWaypointProperties();
		    Collection<Waypoint> waypoints = WaypointStore.instance().getAll();
			for (int i=(1-searchRange)/2; i<(1+searchRange)/2; i++)
				for (int j=(1-searchRange)/2; j<(1+searchRange)/2; j++) {
					targetX = getCoordinateFromIndex(indexX+i);
					targetZ = getCoordinateFromIndex(indexZ+j);
					for (Waypoint wp : waypoints)
						if (wp.getX()==targetX && wp.getZ()==targetZ && wp.getDimensions().contains(dimID)) {
							setFlag = false;
							break;
						}
					if (setFlag) {
						Waypoint waypoint = new Waypoint(I18n.format("waypoint.unknown.name"), targetX, 64, targetZ, Color.white, Waypoint.Type.Normal, world.provider.dimensionId);
						waypoint.setRandomColor();
						WaypointStore.instance().save(waypoint);
						count++;
					}
					setFlag = true;
				}
			player.addChatMessage(new ChatComponentText(I18n.format("chat.count_info", count)));
		}
		return stack;
	}

}
