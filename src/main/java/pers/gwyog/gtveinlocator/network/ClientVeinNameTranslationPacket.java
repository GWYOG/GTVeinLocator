package pers.gwyog.gtveinlocator.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import pers.gwyog.gtveinlocator.util.GTVeinNameHelper;

public class ClientVeinNameTranslationPacket implements IMessage {
    private String text;

    public ClientVeinNameTranslationPacket() {}
    
    public ClientVeinNameTranslationPacket(String text) {
        this.text = text;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        text = "";
        while(buf.readableBytes()>=1) {
            text += GTVeinNameHelper.getName(buf.readByte());
            if (buf.readableBytes()>=1) 
                text += ",";
        }
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        String[] textArray = text.split(",");
        for(String str: textArray) 
            buf.writeByte(GTVeinNameHelper.getIndex(str));
    }
    
    public String getText() {
        return text;
    }
}
