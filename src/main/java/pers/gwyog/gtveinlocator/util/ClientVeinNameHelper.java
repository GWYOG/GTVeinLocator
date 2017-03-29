package pers.gwyog.gtveinlocator.util;

import java.lang.reflect.Field;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import pers.gwyog.gtveinlocator.GTVeinLocator;

public class ClientVeinNameHelper {
    public static boolean basicSupport = true;
    public static boolean mapInit = false;

    @SubscribeEvent
    public void onTick(ClientTickEvent event) {
        if(!mapInit) {
        	GTVeinLocator.gtModHelper.initClientVeinNameHelper();
            mapInit = true;
        }
    }
}
