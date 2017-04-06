package pers.gwyog.gtveinlocator.api.modhelpers;

public interface IXaeroMinimapHelper {
	public void init();
	
	public boolean isWaypointExist(int posX, int posZ, boolean forceAdd);
	
    public boolean addWaypoint(String name, int posX, int posY, int posZ);
}
