package pers.gwyog.gtveinlocator.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import pers.gwyog.gtveinlocator.GTVeinLocator;

public class GTVLNetwork {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(GTVeinLocator.MODID);
    public static int id = 0;
    
    public GTVLNetwork() {
        registerPacketClient(ClientVeinNameTranslationPacketHandler.class, ClientVeinNameTranslationPacket.class);
        registerPacketClient(ClientInfoMessageTranslationPacketHandler.class, ClientInfoMessageTranslationPacket.class);
        registerPacketClient(ClientWaypointPacketHandler.class, ClientWaypointPacket.class);
    }
    
    private void registerPacketClient(Class handlerClazz, Class packetClazz) {
        INSTANCE.registerMessage(handlerClazz, packetClazz, id++, Side.CLIENT);
    }
    
    private void registerPacketServer(Class handlerClazz, Class packetClazz) {
        INSTANCE.registerMessage(handlerClazz, packetClazz, id++, Side.SERVER);
    }
}
