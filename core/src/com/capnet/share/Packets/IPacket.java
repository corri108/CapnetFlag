package com.capnet.share.Packets;

public interface IPacket<E> {
	
	public IPacket<?> Instance();
	public byte[] Encode();
	public void Decode(byte[] data);
	public int Id();

}
