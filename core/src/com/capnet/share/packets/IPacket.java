package com.capnet.share.packets;

import java.nio.ByteBuffer;

public interface IPacket{

	ByteBuffer Encode();
	void Decode(ByteBuffer data);
}
