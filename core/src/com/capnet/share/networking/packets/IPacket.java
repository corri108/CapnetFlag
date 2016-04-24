package com.capnet.share.networking.packets;

import java.nio.ByteBuffer;

public interface IPacket{

	ByteBuffer Encode();
	void Decode(ByteBuffer data);
}
