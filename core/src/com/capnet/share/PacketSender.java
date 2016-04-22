package com.capnet.share;

import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PacketSender implements Runnable{

	private PacketManager _packetManager;
	public PacketSender( PacketManager manager)
	{
		_packetManager = manager;
	}
	
	@Override
	public void run() {
		System.out.println("Started Packet Sending Thread");
		while(true)
		{
			if(_packetManager._packetsOut.size() > 0)
			{
				TransportPair tranportPair =  _packetManager._packetsOut.remove();
				try {


					ByteBuffer payloadBuffer= tranportPair.Packet.Encode();
					payloadBuffer.rewind();
					byte[] data = new byte[payloadBuffer.remaining()];
					payloadBuffer.get(data);

					//add the length of the the collection plus the two ints
					ByteBuffer buffer =  ByteBuffer.allocate(data.length + 4*3 +1);
					buffer.put((byte)125);
					buffer.putInt(tranportPair.Packet.Id());
					buffer.putInt(data.length);
					buffer.put(data);
					buffer.rewind();
					
					System.out.println("Packing Packet ID:" + tranportPair.Packet.Id());
					
					byte[] out = new byte[buffer.remaining()];
					buffer.get(out);
					tranportPair.Out.getOutputStream().write(out);
					
			
					
				}
				catch (SocketException e)
				{
					this._packetManager.UnbindSocket(tranportPair.Out);
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
					//sleep for a moment and then try to collect packets
					//TODO: really poor practice to spin on the thread like this
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
			
		}
		
	}

}
