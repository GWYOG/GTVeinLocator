package pers.gwyog.gtveinlocator.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ic2.api.item.ElectricItem;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import pers.gwyog.gtveinlocator.GTVeinLocator;
import pers.gwyog.gtveinlocator.compat.JourneyMapHelper;
import pers.gwyog.gtveinlocator.compat.LoadedModHelper;
import pers.gwyog.gtveinlocator.compat.XaeroMinimapHelper;
import pers.gwyog.gtveinlocator.compat.LoadedModHelper.SupportModsEnum;
import pers.gwyog.gtveinlocator.config.ModConfig;
import pers.gwyog.gtveinlocator.network.ClientInfoMessageTranslationPacket;
import pers.gwyog.gtveinlocator.network.ClientVeinNameTranslationPacket;
import pers.gwyog.gtveinlocator.network.ClientWaypointPacket;
import pers.gwyog.gtveinlocator.network.GTVLNetwork;
import pers.gwyog.gtveinlocator.util.GTOreLayerHelper;
import pers.gwyog.gtveinlocator.util.GTOreLayerHelper.WorldNameEnum;
import pers.gwyog.gtveinlocator.util.GTVeinNameHelper;

public class ItemEliteVeinLocator extends ItemAdvancedVeinLocator {

    public ItemEliteVeinLocator(String name, double maxCharge, double transferLimit, int tier,
            boolean useEnergy) {
        super(name, maxCharge, transferLimit, tier, useEnergy);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        int searchRange = getSearchRangeFromNBT(stack);
        if (player.isSneaking()) 
            if (!world.isRemote)
                switchMode(stack, searchRange);
            else
                player.addChatMessage(new ChatComponentTranslation("chat.gtveinlocator.switch_range", 4-searchRange, 4-searchRange));
        else {
            if (useEnergy)
                if (!ElectricItem.manager.use(stack, ModConfig.eliteVeinLocatorSingleUseCost*searchRange*searchRange, player)) {
                    return stack;
            }
            if (!world.isRemote) {
                int indexX = getClosestIndex(player.posX);
                int indexZ = getClosestIndex(player.posZ);
                int count = 0;
                int veinCount = 0;
                int dimId = player.dimension;
                WorldProvider worldProvider = world.provider;
                int targetX, targetY, targetZ;
                String foundVeinNames = "", nameUnlocalitzed;
                for (int i=(1-searchRange)/2; i<(1+searchRange)/2; i++)
                    for (int j=(1-searchRange)/2; j<(1+searchRange)/2; j++) {
                        targetX = getCoordinateFromIndex(indexX+i);
                        targetZ = getCoordinateFromIndex(indexZ+j);
                        targetY = getVeinYLevel(world, targetX, targetZ);
                        nameUnlocalitzed = GTOreLayerHelper.judgeOreLayerName(judgeVeinComponent(world, targetX, targetY, targetZ), getWorldNameEnum(worldProvider));
                        GTVLNetwork.INSTANCE.sendTo(new ClientWaypointPacket(nameUnlocalitzed, targetX, targetY, targetZ, dimId), (EntityPlayerMP)player);
                        count++;
                        if (!nameUnlocalitzed.equals("gtveinlocator.ore.mix.empty") && !nameUnlocalitzed.equals("gtveinlocator.ore.mix.unknown")) {
                            veinCount++;
                            if (foundVeinNames.isEmpty()) {
                                foundVeinNames = nameUnlocalitzed;
                            }
                            else
                                foundVeinNames += "," + nameUnlocalitzed;
                        }
                    }
                GTVLNetwork.INSTANCE.sendTo(new ClientInfoMessageTranslationPacket(2, new int[]{veinCount, searchRange, searchRange}), (EntityPlayerMP)player);
                if (!foundVeinNames.isEmpty())
                    GTVLNetwork.INSTANCE.sendTo(new ClientVeinNameTranslationPacket(foundVeinNames), (EntityPlayerMP)player);
            }
        }
        return stack;    
    }
    
    public WorldNameEnum getWorldNameEnum(WorldProvider provider) {
        if (ModConfig.overworldLikeDimensions.contains(provider.dimensionId))
            return WorldNameEnum.overworld;
        else if (ModConfig.netherLikeDimensions.contains(provider.dimensionId))
            return WorldNameEnum.nether;
        else if (ModConfig.endLikeDimensions.contains(provider.dimensionId))
            return WorldNameEnum.end;
        else if (provider.getDimensionName().equals(StatCollector.translateToLocal("dimension.Moon.name")))
            return WorldNameEnum.moon;
        else if (provider.getDimensionName().equals(StatCollector.translateToLocal("dimension.Mars.name")))
            return WorldNameEnum.mars;
        else
            //other worlds where GT veins don't generate
            return WorldNameEnum.unknown;
    }
    
    public int getVeinYLevel(World world, int x, int z) {
        WorldNameEnum worldName = getWorldNameEnum(world.provider);
        for (int y=GTOreLayerHelper.getMinOreLevel(worldName)-2;y<=GTOreLayerHelper.getMaxOreLevel(worldName);y++) {
            for (int dx=-2;dx<3;dx++)
                for(int dz=-2;dz<3;dz++) {
                    if (GTVeinLocator.gtModHelper.isGTBlockOre(world, x+dx, y, z+dz)) {
                    	short meta = GTVeinLocator.gtModHelper.getGTOreMeta(world, x+dx, y, z+dz);
                        meta = GTVeinLocator.gtModHelper.getValidMeta(meta);
                    	if (meta == -1)
                            continue;
                        return y;
                    }
                }
            if (world.canBlockSeeTheSky(x, y-5, z)) 
                break;
        }
        return -1;
    }
    
    public List<Short> judgeVeinComponent(World world, int x, int y, int z) {
        Map<Short, Integer> map = new HashMap<Short, Integer>();
        if (y!=-1)
            return getSortedListFromMap(scanCarefully(map, world, x, y, z));
        else
            return null;
    }
    
    public Map<Short, Integer> scanCarefully(Map<Short, Integer> map, World world, int x, int y, int z) {
        //the max range of dy comes from GT's oregen algorithm 
        for(int dy=-2;dy<7;dy++)
            for(int dx=-4;dx<5;dx++)
                for(int dz=-4;dz<5;dz++) {
                    if (GTVeinLocator.gtModHelper.isGTBlockOre(world, x+dx, y, z+dz)) {
                        short meta = GTVeinLocator.gtModHelper.getGTOreMeta(world, x+dx, y, z+dz);
                        meta = GTVeinLocator.gtModHelper.getValidMeta(meta);
                        if (meta == -1)
                            continue;
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
