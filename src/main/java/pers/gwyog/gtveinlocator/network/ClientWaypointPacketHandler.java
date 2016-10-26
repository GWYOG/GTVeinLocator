package pers.gwyog.gtveinlocator.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.event.world.ChunkDataEvent.Load;
import pers.gwyog.gtveinlocator.compat.JourneyMapHelper;
import pers.gwyog.gtveinlocator.compat.LoadedModHelper;
import pers.gwyog.gtveinlocator.compat.XaeroMinimapHelper;
import pers.gwyog.gtveinlocator.util.GTOreLayerHelper;

public class ClientWaypointPacketHandler implements IMessageHandler<ClientWaypointPacket, IMessage> {

	@Override
	public IMessage onMessage(ClientWaypointPacket message, MessageContext ctx) {
		if (LoadedModHelper.supportMod == null) 
			return null;
		else{
			int isWaypointExist;
			int targetY = message.posY==-1? 70: message.posY;
			switch(LoadedModHelper.supportMod) {
			case JOURNEYMAP:
				isWaypointExist = JourneyMapHelper.isWaypointExist(message.posX, message.posZ, message.dimId);
				if (isWaypointExist == 0)
					if (!JourneyMapHelper.addWaypoint(I18n.format(message.wpName), message.posX, targetY, message.posZ, message.dimId))
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentTranslation("modcompat.addwperror.info", LoadedModHelper.supportMod.getName()));				
				else if (isWaypointExist == -1)
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentTranslation("modcompat.checkwperror.info", LoadedModHelper.supportMod.getName()));
				break;
			case XAEROMINIMAP:
				isWaypointExist = XaeroMinimapHelper.isWaypointExist(message.posX, message.posZ);
				if (isWaypointExist == 0)
					if (!XaeroMinimapHelper.addWaypoint(I18n.format(message.wpName), message.posX, targetY, message.posZ)) 
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentTranslation("modcompat.addwperror.info", LoadedModHelper.supportMod.getName()));				
				else if (isWaypointExist == -1)
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentTranslation("modcompat.checkwperror.info", LoadedModHelper.supportMod.getName()));
				break;
			}
		}
		return null;
	}

}
