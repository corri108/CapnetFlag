package com.capnet.share;

import com.capnet.share.Packets.IPacket;

import java.net.Socket;
public class TransportPair {
	public Socket Out;
	public IPacket<?> Packet;
	
	public TransportPair(IPacket<?> Packet,Socket out)
	{
		this.Out = out;
		this.Packet = Packet;
	}
}
