package pers.gwyog.gtveinlocator.api.modhelpers;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import gregapi.block.prefixblock.PrefixBlockTileEntity;
import gregapi.data.CS;
import gregapi.data.IL;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.util.UT;
import gregapi.worldgen.Worldgen_GT_Ore_Layer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import pers.gwyog.gtveinlocator.ModItems;
import pers.gwyog.gtveinlocator.config.ModConfig;
import pers.gwyog.gtveinlocator.util.ClientVeinNameHelper;
import pers.gwyog.gtveinlocator.util.GTOreLayerHelper;
import pers.gwyog.gtveinlocator.util.GTVeinNameHelper;

public class GT6ModHelper implements IGTModHelper {

	@Override
	public void registerItems() {
        if (ModConfig.veinLocatorEnabled) {
            GameRegistry.registerItem(ModItems.itemVeinLocator, "veinLocator");
            if (!ModConfig.recipeVeinLocatorDisabled) 
            	UT.Crafting.shaped(new ItemStack(ModItems.itemVeinLocator), UT.Crafting.Bits.DEFAULT, new Object[]{
                    "SWS", "WwW", "SCS",
                    Character.valueOf('S'), OP.plate.dat(MT.Steel),
                    Character.valueOf('W'), OP.cableGt01.dat(MT.Tin),
                    Character.valueOf('C'), OP.circuit.dat(MT.Basic)
                });
        }
        if (ModConfig.advancedVeinLocatorEnabled) {
            GameRegistry.registerItem(ModItems.itemAdvancedVeinLocator, "advancedVeinLocator");
            if (!ModConfig.recipeAdvancedVeinLocatorDisabled)
            	UT.Crafting.shaped(new ItemStack(ModItems.itemAdvancedVeinLocator), UT.Crafting.Bits.DEFAULT, new Object[]{
                    "AWA", "WwW", "ACA",
                    Character.valueOf('A'), OP.plate.dat(MT.Aluminium),
                    Character.valueOf('W'), OP.cableGt02.dat(MT.Copper),
                    Character.valueOf('C'), OP.circuit.dat(MT.Good)
                });
        }
        if (ModConfig.eliteVeinLocatorEnabled) {
            GameRegistry.registerItem(ModItems.itemEliteVeinLocator, "eliteVeinLocator");
            if (!ModConfig.recipeEliteVeinLocatorDisabled)
            	UT.Crafting.shaped(new ItemStack(ModItems.itemEliteVeinLocator), UT.Crafting.Bits.DEFAULT, new Object[]{
                        "STS", "WwW", "SCS",
                        Character.valueOf('S'), OP.plate.dat(MT.StainlessSteel),
                        Character.valueOf('T'), IL.SENSORS[3].get(1L, new Object[0]),
                        Character.valueOf('W'), OP.cableGt04.dat(MT.Silver),
                        Character.valueOf('C'), OP.circuit.dat(MT.Advanced)
                    });
        }
	}

	@Override
	public void initClientVeinNameHelper() {      
        // map initialization start
        if (ClientVeinNameHelper.basicSupport) {
            GTVeinNameHelper.registerVeinName("gtveinlocator.ore.mix.empty");
            GTVeinNameHelper.registerVeinName("gtveinlocator.ore.mix.unknown");
            for (Worldgen_GT_Ore_Layer worldGen : Worldgen_GT_Ore_Layer.sList)
                if (worldGen.mEnabled) 
                    GTVeinNameHelper.registerVeinName("gtveinlocator." + worldGen.mWorldGenName);
        }
	}

	@Override
	public void initGTOreLayerHelper() {
		// gt6 does not support galacticraft
    	GTOreLayerHelper.gcSupport = false; 
    	
        // initialization starts
        if (GTOreLayerHelper.basicSupport) {
            GTVeinNameHelper.registerVeinName("gtveinlocator.ore.mix.empty");
            GTVeinNameHelper.registerVeinName("gtveinlocator.ore.mix.unknown");
            for (Worldgen_GT_Ore_Layer worldGen : Worldgen_GT_Ore_Layer.sList) {
                if (worldGen.mEnabled) {
                    GTVeinNameHelper.registerVeinName("gtveinlocator." + worldGen.mWorldGenName);
                    List<Short> componentList = new LinkedList<Short>();
                    componentList.add(worldGen.mPrimaryMeta);
                    componentList.add(worldGen.mSecondaryMeta);
                    componentList.add(worldGen.mBetweenMeta);
                    componentList.add(worldGen.mSporadicMeta);
                    if (worldGen.mOverworld) {
                    	GTOreLayerHelper.mapGTOverworldOreLayer.put(componentList, "gtveinlocator." + worldGen.mWorldGenName);
                    	GTOreLayerHelper.minLevelOreOverworld = worldGen.mMinY<GTOreLayerHelper.minLevelOreOverworld? worldGen.mMinY: GTOreLayerHelper.minLevelOreOverworld;
                    	GTOreLayerHelper.maxLevelOreOverworld = worldGen.mMaxY>GTOreLayerHelper.maxLevelOreOverworld? worldGen.mMaxY: GTOreLayerHelper.maxLevelOreOverworld;
                    }
                    if (worldGen.mNether) {
                    	GTOreLayerHelper.mapGTNetherOreLayer.put(componentList, "gtveinlocator." + worldGen.mWorldGenName);
                    	GTOreLayerHelper.minLevelOreNether = worldGen.mMinY<GTOreLayerHelper.minLevelOreNether? worldGen.mMinY: GTOreLayerHelper.minLevelOreNether;
                    	GTOreLayerHelper.maxLevelOreNether = worldGen.mMaxY>GTOreLayerHelper.maxLevelOreNether? worldGen.mMaxY: GTOreLayerHelper.maxLevelOreNether;
                    }
                    if (worldGen.mEnd) {
                    	GTOreLayerHelper.mapGTEndOreLayer.put(componentList, "gtveinlocator." + worldGen.mWorldGenName);
                    	GTOreLayerHelper.minLevelOreEnd = worldGen.mMinY<GTOreLayerHelper.minLevelOreEnd? worldGen.mMinY: GTOreLayerHelper.minLevelOreEnd;
                    	GTOreLayerHelper.maxLevelOreEnd = worldGen.mMaxY>GTOreLayerHelper.maxLevelOreEnd? worldGen.mMaxY: GTOreLayerHelper.maxLevelOreEnd;
                    }
                }
            }
        }
		
	}

	@Override
	public int getClosestIndex(double var1) {
        return (int)(Math.round((var1-24)/48));
	}

	@Override
	public int getCoordinateFromIndex(int index) {
        return 24+48*index;
	}
	
	@Override
	public boolean isGTBlockOre(World world, int posX, int posY, int posZ) {
		String unlocalizedName = world.getBlock(posX, posY, posZ).getUnlocalizedName();
		return unlocalizedName.startsWith("gt.meta.ore.normal") && !unlocalizedName.startsWith("gt.meta.ore.normal.bedrock");
	}
	
	@Override
	public short getGTOreMeta(World world, int posX, int posY, int posZ) {
		return ((PrefixBlockTileEntity)(world.getTileEntity(posX, posY, posZ))).mMetaData;
	}

	@Override
	public short getValidMeta(short meta) {
		return meta;
	}

}
