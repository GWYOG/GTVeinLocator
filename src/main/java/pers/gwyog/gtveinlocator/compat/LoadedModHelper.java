package pers.gwyog.gtveinlocator.compat;

import cpw.mods.fml.common.Loader;

public class LoadedModHelper {
	public static boolean isJourneyMapLoaded;
	public static boolean isXaeroMinimapLoaded;
	
	public static void init() {
		isJourneyMapLoaded = Loader.isModLoaded("journeymap");
		isXaeroMinimapLoaded = Loader.isModLoaded("XaeroMinimap");
	}
}
