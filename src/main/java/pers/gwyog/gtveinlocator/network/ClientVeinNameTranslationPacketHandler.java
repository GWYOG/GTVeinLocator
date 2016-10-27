package pers.gwyog.gtveinlocator.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import pers.gwyog.gtveinlocator.compat.LoadedModHelper;
import pers.gwyog.gtveinlocator.util.ClientVeinNameHelper;
import pers.gwyog.gtveinlocator.util.GTVeinNameHelper;

public class ClientVeinNameTranslationPacketHandler implements IMessageHandler<ClientVeinNameTranslationPacket, IMessage>{

	@Override
	public IMessage onMessage(ClientVeinNameTranslationPacket message, MessageContext ctx) {
		if (LoadedModHelper.supportMod == null || LoadedModHelper.failedCompat)
			return null;
		String unlocalizedText = message.getText();
		String[] unlocalizedTextArray = unlocalizedText.split(",");
		String localizedText = "";
		for (String str: unlocalizedTextArray) {
			if (!localizedText.isEmpty())
				localizedText += ", ";
			localizedText += I18n.format(str);
		}
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentTranslation("chat.found_info", localizedText));
		return null;
	}

}
