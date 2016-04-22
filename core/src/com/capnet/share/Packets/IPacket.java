package com.capnet.share.Packets;

import java.nio.ByteBuffer;

public interface IPacket<E> {
	
	IPacket<?> Instance();
	ByteBuffer Encode();
	void Decode(ByteBuffer data);
	int Id();

}
