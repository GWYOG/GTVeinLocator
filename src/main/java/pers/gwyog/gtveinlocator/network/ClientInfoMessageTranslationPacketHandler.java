package pers.gwyog.gtveinlocator.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentTranslation;
import pers.gwyog.gtveinlocator.compat.LoadedModHelper;
import pers.gwyog.gtveinlocator.compat.LoadedModHelper.SupportModsEnum;

public class ClientInfoMessageTranslationPacketHandler implements IMessageHandler<ClientInfoMessageTranslationPacket, IMessage> {

	@Override
	public IMessage onMessage(ClientInfoMessageTranslationPacket message, MessageContext ctx) {
		SupportModsEnum supportMod = LoadedModHelper.supportMod;
		if (supportMod == null) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentTranslation("modcompat.nominimap.info"));
			return null;
		}
		int[] intParaArray = message.intParaArray;
		int infoIndex = message.infoIndex;
		switch (infoIndex) {
		case 2:
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentTranslation("chat.info"+infoIndex, intParaArray[0], intParaArray[1], intParaArray[2], supportMod.getName()));
			break;
		default:
			break;
		}
		return null;
	}

}
