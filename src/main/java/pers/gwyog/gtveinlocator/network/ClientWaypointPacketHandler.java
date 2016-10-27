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
		else if (!LoadedModHelper.failedCompat) {
			int targetY = message.posY==-1? 70: message.posY;
			switch(LoadedModHelper.supportMod) {
			case JOURNEYMAP:
				if (!JourneyMapHelper.isWaypointExist(message.posX, message.posZ, message.dimId, true))
					JourneyMapHelper.addWaypoint(I18n.format(message.wpName), message.posX, targetY, message.posZ, message.dimId);		
				break;
			case XAEROMINIMAP:
				if (!XaeroMinimapHelper.isWaypointExist(message.posX, message.posZ, true))
					XaeroMinimapHelper.addWaypoint(I18n.format(message.wpName), message.posX, targetY, message.posZ);
				break;
			}
		}
		return null;
	}

}
