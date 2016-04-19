package com.capnet.share;
import com.capnet.share.Packets.*;

public class RegisterPackets {
	public static void setPackets(PacketManager manager)
	{
		manager.RegisterPacket(new Heartbeat_0());
	}
}
