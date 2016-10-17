package pers.gwyog.gtveinlocator.items;

import java.awt.Color;
import java.util.Collection;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import pers.gwyog.gtveinlocator.compat.JourneyMapHelper;
import pers.gwyog.gtveinlocator.compat.XaeroMinimapHelper;

public class ItemAdvancedVeinLocator extends ItemVeinLocator {

	private SupportModsEnum supportMod;
	
	public enum SupportModsEnum {
		journeymap("JourneyMap"), XaeroMinimap("XaeroMinimap");
		
		private String name;
		
		private SupportModsEnum(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
		
	}
	
	public ItemAdvancedVeinLocator(String name, SupportModsEnum supportMod) {
		super(name);
		this.supportMod = supportMod;
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
			int dimId = player.dimension; 
			int targetX, targetZ;
			for (int i=(1-searchRange)/2; i<(1+searchRange)/2; i++)
				for (int j=(1-searchRange)/2; j<(1+searchRange)/2; j++) {
					targetX = getCoordinateFromIndex(indexX+i);
					targetZ = getCoordinateFromIndex(indexZ+j);
					switch (this.supportMod) {
					case journeymap:
						if (!JourneyMapHelper.isWaypointExist(targetX, targetZ, dimId))
							if(JourneyMapHelper.addWaypoint(I18n.format("waypoint.unknown.name"), targetX, 64, targetZ, dimId))
								count++;
						break;
					case XaeroMinimap:
						if (!XaeroMinimapHelper.isWaypointExist(targetX, targetZ))
							if(XaeroMinimapHelper.addWaypoint(I18n.format("waypoint.unknown.name"), targetX, 64, targetZ))
								count++;
						break;
					}
				}
			player.addChatMessage(new ChatComponentText(I18n.format("chat.count_info", count, this.supportMod.getName())));
		}
		return stack;
	}

}
