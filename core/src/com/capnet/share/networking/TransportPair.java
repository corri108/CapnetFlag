package com.capnet.share.networking;

import com.capnet.share.networking.packets.IPacket;

import java.net.Socket;
public class TransportPair {
	public Socket Out;
	public IPacket Packet;
	
	public TransportPair(IPacket Packet,Socket out)
	{
		this.Out = out;
		this.Packet = Packet;
	}
}
