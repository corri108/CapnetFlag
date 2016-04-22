package com.capnet.share.networking;
import com.capnet.share.networking.packets.*;

public class RegisterPackets {
	public static void setPackets(PacketManager manager)
	{
		manager.RegisterPacket(new Heartbeat_0());
		manager.RegisterPacket(new Player_1());
	}
}
