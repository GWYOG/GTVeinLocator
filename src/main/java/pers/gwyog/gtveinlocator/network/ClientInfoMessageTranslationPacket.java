package pers.gwyog.gtveinlocator.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class ClientInfoMessageTranslationPacket implements IMessage {
    public int infoIndex;
    public int[] intParaArray;
    
    public ClientInfoMessageTranslationPacket() {}
    
    public ClientInfoMessageTranslationPacket(int infoIndex, int[] intParaArray) {
        this.infoIndex = infoIndex;
        this.intParaArray = intParaArray;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        infoIndex = buf.readInt();
        int maxLength = buf.readableBytes()/4;
        intParaArray = new int[maxLength];
        for (int i=0; i<maxLength; i++) 
            intParaArray[i] = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(infoIndex);
        for (int i: intParaArray)
            buf.writeInt(i);
    }
    
}
