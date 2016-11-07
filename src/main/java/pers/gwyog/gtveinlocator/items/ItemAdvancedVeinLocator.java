package pers.gwyog.gtveinlocator.items;

import java.awt.Color;
import java.util.Collection;

import ic2.api.item.ElectricItem;
import gregtech.common.blocks.GT_TileEntity_Ores;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import pers.gwyog.gtveinlocator.compat.JourneyMapHelper;
import pers.gwyog.gtveinlocator.compat.LoadedModHelper;
import pers.gwyog.gtveinlocator.compat.LoadedModHelper.SupportModsEnum;
import pers.gwyog.gtveinlocator.compat.XaeroMinimapHelper;
import pers.gwyog.gtveinlocator.config.ModConfig;
import pers.gwyog.gtveinlocator.util.ClientVeinNameHelper;
import pers.gwyog.gtveinlocator.util.GTOreLayerHelper;

public class ItemAdvancedVeinLocator extends ItemVeinLocator {
    
    public ItemAdvancedVeinLocator(String name, double maxCharge, double transferLimit, int tier, boolean useEnergy) {
        super(name, maxCharge, transferLimit, tier, useEnergy);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        int searchRange = getSearchRangeFromNBT(stack);
        if (player.isSneaking()) 
            if (!world.isRemote)
                switchMode(stack, searchRange);
            else
                player.addChatMessage(new ChatComponentText(I18n.format("chat.gtveinlocator.switch_range", 4-searchRange, 4-searchRange)));
        else if (!player.isSneaking()) {
            if (useEnergy)
                if (!ElectricItem.manager.use(stack, ModConfig.advancedVeinLocatorSingleUseCost*searchRange*searchRange, player)) 
                    return stack;
            if (world.isRemote) {
                SupportModsEnum supportMod = LoadedModHelper.supportMod;
                if (!ClientVeinNameHelper.basicSupport) {
                    player.addChatMessage(new ChatComponentText(I18n.format("modcompat.gtveinlocator.oldversion_gt.info")));
                    return stack;
                }
                if (supportMod == null) {
                    player.addChatMessage(new ChatComponentText(I18n.format("modcompat.gtveinlocator.no_minimap.info")));
                    return stack;
                }
                else if (LoadedModHelper.failedCompat) {
                    player.addChatMessage(new ChatComponentTranslation("modcompat.gtveinlocator.oldversion_minimap.info", supportMod.getName()));
                    return stack;
                }
                int indexX = getClosestIndex(player.posX);
                int indexZ = getClosestIndex(player.posZ);
                int count = 0;
                int dimId = player.dimension; 
                int targetX, targetZ;
                for (int i=(1-searchRange)/2; i<(1+searchRange)/2; i++)
                    for (int j=(1-searchRange)/2; j<(1+searchRange)/2; j++) {
                        targetX = getCoordinateFromIndex(indexX+i);
                        targetZ = getCoordinateFromIndex(indexZ+j);
                        switch (supportMod) {
                        case JOURNEYMAP:
                            if (!JourneyMapHelper.isWaypointExist(targetX, targetZ, dimId, false))
                                if(JourneyMapHelper.addWaypoint(I18n.format("waypoint.gtveinlocator.unknown.name"), targetX, ModConfig.waypointYLevelAdvancedLocator, targetZ, dimId))
                                    count++;
                            break;
                        case XAEROMINIMAP:
                            if (!XaeroMinimapHelper.isWaypointExist(targetX, targetZ, false)) {
                                if(XaeroMinimapHelper.addWaypoint(I18n.format("waypoint.gtveinlocator.unknown.name"), targetX, ModConfig.waypointYLevelAdvancedLocator, targetZ))
                                    count++;
                            }
                            break;
                        }
                    }
                player.addChatMessage(new ChatComponentText(I18n.format("chat.gtveinlocator.info1", count, supportMod.getName())));
            }
        }
        return stack;
    }

}
