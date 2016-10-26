package pers.gwyog.gtveinlocator.items;

import java.awt.Color;
import java.util.Collection;

import ic2.api.item.ElectricItem;
import gregtech.common.blocks.GT_TileEntity_Ores;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import pers.gwyog.gtveinlocator.compat.JourneyMapHelper;
import pers.gwyog.gtveinlocator.compat.LoadedModHelper;
import pers.gwyog.gtveinlocator.compat.LoadedModHelper.SupportModsEnum;
import pers.gwyog.gtveinlocator.compat.XaeroMinimapHelper;
import pers.gwyog.gtveinlocator.config.ModConfig;

public class ItemAdvancedVeinLocator extends ItemVeinLocator {
	
	public ItemAdvancedVeinLocator(String name, double maxCharge, double transferLimit, int tier, boolean showDuribilityBar) {
		super(name, maxCharge, transferLimit, tier, showDuribilityBar);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		int searchRange = getSearchRangeFromNBT(stack);
		if (player.isSneaking()) 
			if (!world.isRemote)
				switchMode(stack, searchRange);
			else
				player.addChatMessage(new ChatComponentText(I18n.format("chat.switch_range", 4-searchRange, 4-searchRange)));
		else if (!player.isSneaking()) {
			if (!ElectricItem.manager.use(stack, ModConfig.advancedVeinLocatorSingleUseCost*searchRange*searchRange, player)) 
				return stack;
			if (world.isRemote) {
				SupportModsEnum supportMod = LoadedModHelper.supportMod;
				if (supportMod == null) {
					player.addChatMessage(new ChatComponentText(I18n.format("modcompat.null_info")));
					return stack;
				}
				int indexX = getClosestIndex(player.posX);
				int indexZ = getClosestIndex(player.posZ);
				int count = 0;
				int isWaypointExist;
				int dimId = player.dimension; 
				int targetX, targetZ;
				for (int i=(1-searchRange)/2; i<(1+searchRange)/2; i++)
					for (int j=(1-searchRange)/2; j<(1+searchRange)/2; j++) {
						targetX = getCoordinateFromIndex(indexX+i);
						targetZ = getCoordinateFromIndex(indexZ+j);
						switch (supportMod) {
						case JOURNEYMAP:
							isWaypointExist = JourneyMapHelper.isWaypointExist(targetX, targetZ, dimId);
							if (isWaypointExist == 0) {
								if(JourneyMapHelper.addWaypoint(I18n.format("waypoint.unknown.name"), targetX, ModConfig.waypointYLevelForJourneyMap, targetZ, dimId))
									count++;
							}
							else if (isWaypointExist == -1) {
								player.addChatMessage(new ChatComponentText(I18n.format("modcompat.journeymap.checkwperror.info"))); 
								return stack;
							}
							break;
						case XAEROMINIMAP:
							isWaypointExist = XaeroMinimapHelper.isWaypointExist(targetX, targetZ);
							if (isWaypointExist == 0) {
								if(XaeroMinimapHelper.addWaypoint(I18n.format("waypoint.unknown.name"), targetX, ModConfig.waypointYLevelForXaeroMinimap, targetZ))
									count++;
							}
							else if (isWaypointExist == -1) {
								player.addChatMessage(new ChatComponentText(I18n.format("modcompat.xaerominimap.checkwperror.info"))); 
								return stack;
							}
							break;
						}
					}
				player.addChatMessage(new ChatComponentText(I18n.format("chat.info1", count, supportMod.getName())));
			}
		}
		return stack;
	}

}
