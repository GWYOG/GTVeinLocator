package pers.gwyog.gtveinlocator.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import pers.gwyog.gtveinlocator.util.GTVeinNameHelper;

public class ClientWaypointPacket implements IMessage {
	public String wpName;
	public int posX;
	public int posY;
	public int posZ;
	public int dimId;
	
	public ClientWaypointPacket() {}
	
	public ClientWaypointPacket(String wpName, int posX, int posY, int posZ, int dimId) {
		this.wpName = wpName;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.dimId = dimId;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		wpName = GTVeinNameHelper.getName(buf.readByte());
		posX = buf.readInt();
		posY = buf.readInt();
		posZ = buf.readInt();
		dimId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(GTVeinNameHelper.getIndex(wpName));
		buf.writeInt(posX);
		buf.writeInt(posY);
		buf.writeInt(posZ);
		buf.writeInt(dimId);
	}
	
}
