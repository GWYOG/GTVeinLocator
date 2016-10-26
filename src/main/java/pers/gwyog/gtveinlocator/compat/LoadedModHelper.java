package pers.gwyog.gtveinlocator.compat;

import cpw.mods.fml.common.Loader;

public class LoadedModHelper {
	public static boolean isJourneyMapLoaded;
	public static boolean isXaeroMinimapLoaded;
	public static SupportModsEnum supportMod;
	
	public enum SupportModsEnum {
		JOURNEYMAP("JourneyMap"), XAEROMINIMAP("XaeroMinimap");
		
		private String name;
		
		private SupportModsEnum(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
		
	}
	
	public static void init() {
		isJourneyMapLoaded = Loader.isModLoaded("journeymap");
		isXaeroMinimapLoaded = Loader.isModLoaded("XaeroMinimap");
		supportMod = isJourneyMapLoaded? SupportModsEnum.JOURNEYMAP: isXaeroMinimapLoaded? SupportModsEnum.XAEROMINIMAP: null;
	}
}
