package pers.gwyog.gtveinlocator.api.modhelpers;

import net.minecraft.world.World;

public interface IGTModHelper {
	public void registerItems();
	
	public void initClientVeinNameHelper();
	
	public void initGTOreLayerHelper();
	
	public int getClosestIndex(double var1);
	
	public int getCoordinateFromIndex(int index);
	
	public short getGTOreMeta(World world, int posX, int posY, int posZ);

	public boolean isGTBlockOre(World world, int posX, int posY, int posZ);

    public short getValidMeta(short meta);	
}
