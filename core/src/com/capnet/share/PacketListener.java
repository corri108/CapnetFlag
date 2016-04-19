package com.capnet.share;

import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

import com.capnet.share.Packets.IPacket;

public class PacketListener implements Runnable{
	private Socket _socket = null;
	private PacketManager _packetManager;
	private Queue<IPacket<?>> _reprocess = new LinkedList<>();
	PacketListener(Socket sock, PacketManager manager)
	{
		this._socket = sock;
		this._packetManager = manager;
	}

	@Override
	public void run() {
		try
		{
			
			InputStream stream = _socket.getInputStream();
			System.out.println("Started Packet Listener");
			while (!Thread.currentThread().isInterrupted())
			{
				if(stream.available() > 0)
				{
					//marks the beginning of the packet with an arbitrary value
					if(stream.read() == 125)
					{
						ByteBuffer packetHeader = ByteBuffer.allocate(16);
						// Get data sent from the server
						byte[] header = new byte[8];

						stream.read(header);
						packetHeader.put(header);
						packetHeader.rewind();

						int id = packetHeader.getInt();
						int length = packetHeader.getInt();

						System.out.println("Recieved packet with ID:"+id);

						byte[] data = new byte[length];
						stream.read(data);
						IPacket<?> p = _packetManager.GetPacketInstance(id);
						if(p != null)
						{
							p.Decode(data);

							if(!_packetManager._packetCallback.containsKey(p.Id())) {
								_reprocess.add(p);
							}
							else {
								if (!Thread.currentThread().isInterrupted()) {
									_packetManager._packetCallback.get(p.Id()).onPacket(new TransportPair(p, _socket));//.add(new TransportPair(p,_socket));
								}
							}


						}
					}
				}
				else {

					Thread.sleep(200);
				}
					//attempts to reprocess the packet if the handler isn't ready
					if (_reprocess.size() > 0) {
						IPacket<?> p = _reprocess.remove();
						if (p != null) {
							if (_packetManager._packetCallback.containsKey(p.Id()))
								_packetManager._packetCallback.get(p.Id()).onPacket(new TransportPair(p, _socket));
							else
								_reprocess.add(p);
						}
					}


			}
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e.toString());
		}
		System.out.println("Packet Listener has stopped");
		
	}


}
