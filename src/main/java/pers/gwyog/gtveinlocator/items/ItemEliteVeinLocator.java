package pers.gwyog.gtveinlocator.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import gregtech.common.blocks.GT_TileEntity_Ores;
import ic2.api.item.ElectricItem;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import pers.gwyog.gtveinlocator.compat.JourneyMapHelper;
import pers.gwyog.gtveinlocator.compat.XaeroMinimapHelper;
import pers.gwyog.gtveinlocator.config.ModConfig;
import pers.gwyog.gtveinlocator.util.GTOreLayerHelper;
import pers.gwyog.gtveinlocator.util.GTOreLayerHelper.WorldNameEnum;

public class ItemEliteVeinLocator extends ItemAdvancedVeinLocator {

	public ItemEliteVeinLocator(String name, double maxCharge, double transferLimit, int tier,
			boolean showDuribilityBar, SupportModsEnum supportMod) {
		super(name, maxCharge, transferLimit, tier, showDuribilityBar, supportMod);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		int searchRange = getSearchRangeFromNBT(stack);
		if (world.isRemote || !ElectricItem.manager.use(stack, ModConfig.advancedVeinLocatorSingleUseCost*searchRange*searchRange, player)) {
			return stack;
		}
		if (player.isSneaking()) 
			switchMode(stack, player, searchRange);
		else {
			int indexX = getClosestIndex(player.posX);
			int indexZ = getClosestIndex(player.posZ);
			int count = 0;
			int veinCount = 0;
			int dimId = player.dimension;
			WorldProvider worldProvider = world.provider;
			int targetX, targetZ;
			String foundVeinNames = "";
			for (int i=(1-searchRange)/2; i<(1+searchRange)/2; i++)
				for (int j=(1-searchRange)/2; j<(1+searchRange)/2; j++) {
					targetX = getCoordinateFromIndex(indexX+i);
					targetZ = getCoordinateFromIndex(indexZ+j);
					switch (this.supportMod) {
					case journeymap:
						if (!JourneyMapHelper.isWaypointExist(targetX, targetZ, dimId)) {
							String nameUnlocalitzed = GTOreLayerHelper.judgeOreLayerName(judgeVeinComponent(world, targetX, targetZ), getWorldNameEnum(worldProvider));
							String name = I18n.format(nameUnlocalitzed);
							if(JourneyMapHelper.addWaypoint(name, targetX, ModConfig.waypointYLevelForJourneyMap, targetZ, dimId)) {
								count++;
								if (!nameUnlocalitzed.equals("ore.mix.empty") && !nameUnlocalitzed.equals("ore.mix.unknown")) {
									veinCount++;
									if (foundVeinNames.isEmpty()) {
										foundVeinNames = name;
									}
									else
										foundVeinNames += ", " + name;
								}
							}
						}
						break;
					case XaeroMinimap:
						if (!XaeroMinimapHelper.isWaypointExist(targetX, targetZ)) {
							String nameUnlocalitzed = GTOreLayerHelper.judgeOreLayerName(judgeVeinComponent(world, targetX, targetZ), getWorldNameEnum(worldProvider));
							String name = I18n.format(nameUnlocalitzed);
							if(XaeroMinimapHelper.addWaypoint(name, targetX, ModConfig.waypointYLevelForXaeroMinimap, targetZ)) {
								count++;
								if (!nameUnlocalitzed.equals("ore.mix.empty") && !nameUnlocalitzed.equals("ore.mix.unknown")) {
									veinCount++;
									if (foundVeinNames.isEmpty()) {
										foundVeinNames = name;
									}
									else
										foundVeinNames += ", " + name;
								}
							}
						}
						break;
					}
				}
			player.addChatMessage(new ChatComponentText(I18n.format("chat.count_info2", veinCount, searchRange, searchRange, this.supportMod.getName())));
			player.addChatMessage(new ChatComponentText(I18n.format("chat.found_info", foundVeinNames)));
		}
		return stack;	
	}
	
	public WorldNameEnum getWorldNameEnum (WorldProvider provider) {
		if (provider.dimensionId == 0)
			return WorldNameEnum.overworld;
		else if (provider.dimensionId == -1)
			return WorldNameEnum.nether;
		else if (provider.dimensionId == 1)
			return WorldNameEnum.end;
		else if (provider.getDimensionName().equals("Moon"))
			return WorldNameEnum.moon;
		else if (provider.getDimensionName().equals("Mars"))
			return WorldNameEnum.mars;
		else
			//never return this
			return WorldNameEnum.overworld;
	}
	
	public List<Short> judgeVeinComponent(World world, int x, int z) {
		Map<Short, Integer> map = new HashMap<Short, Integer>();
		for (int y=0;y<=130;y++) {
			for (int dx=-2;dx<3;dx++)
				for(int dz=-2;dz<3;dz++)
					if (world.getBlock(x+dx, y, z+dz).getUnlocalizedName().equals("gt.blockores")) {
						short meta = ((GT_TileEntity_Ores)world.getTileEntity(x+dx, y, z+dz)).mMetaData;
						//avoid counting the small_ores.
						if (meta>=16000)
							continue;
						System.out.println("metadata="+meta);
						System.out.println("metadata="+((GT_TileEntity_Ores)world.getTileEntity(x+dx, y, z+dz)).mMetaData);
						System.out.println("x="+(x+dx)+", y="+(y)+", z="+(z+dz));
						return getSortedListFromMap(scanCarefully(map, world, x+dx, y, z+dz));
					}
			if (world.canBlockSeeTheSky(x, y, z)) 
				break;
		}
		return null;
	}
	
	public Map<Short, Integer> scanCarefully(Map<Short, Integer> map, World world, int x, int y, int z) {
		//the max range of dy comes from GT's oregen algorithm 
		for(int dy=-2;dy<7;dy++)
			for(int dx=-4;dx<5;dx++)
				for(int dz=-4;dz<5;dz++) {
					if (world.getBlock(x+dx, y, z+dz).getUnlocalizedName().equals("gt.blockores")) {
						short meta = ((GT_TileEntity_Ores)world.getTileEntity(x+dx, y, z+dz)).mMetaData;
						if (meta>=16000)
							continue;
						meta = (short)(meta % 1000);
						if (map.containsKey(meta)) 
							map.put(meta, map.get(meta)+1);
						else
							map.put(meta, 1);
					}
				}
		return map;
	}
	
	public List<Short> getSortedListFromMap(Map<Short, Integer> map) {
		List<Map.Entry<Short, Integer>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Short, Integer>>() {
            @Override
            public int compare(Map.Entry<Short, Integer> o1, Map.Entry<Short, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }   
        });

        
        List<Short> ret = new LinkedList<Short>();
        for(short meta : map.keySet()){
        	if(meta==0)
        		System.out.println(map.get(0));
        	else
        		ret.add(meta);
        }
        return ret;
	}
	
}
