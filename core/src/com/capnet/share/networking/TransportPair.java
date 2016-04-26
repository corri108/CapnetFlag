package com.capnet.share.networking;

import com.capnet.share.packets.IPacket;

import java.net.Socket;
public class TransportPair<T extends  IPacket> {
	public Socket Out;
	public T Packet;

	
	public TransportPair(T Packet,Socket out)
	{
		this.Out = out;
		this.Packet = Packet;
	}

}
