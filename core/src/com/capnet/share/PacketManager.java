package com.capnet.share;

import com.capnet.share.Packets.IPacket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public class PacketManager {

	protected  ConcurrentLinkedQueue<TransportPair> _packetsOut = new ConcurrentLinkedQueue<TransportPair>();
	protected ConcurrentHashMap<Integer,IPacketCallback> _packetCallback = new ConcurrentHashMap<Integer,IPacketCallback>();
	//selection of packets to match against
	protected  ConcurrentHashMap<Integer, IPacket<?>> _packets = new ConcurrentHashMap<Integer,IPacket<?>>();
    protected ISocketConnect _socketConnected;

	private ConcurrentHashMap<Socket,Thread> _packetListner = new ConcurrentHashMap<Socket,Thread>();


	public PacketManager()
	{
        RegisterPackets.setPackets(this);

    }


    public  void  StartSocketListener(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);

        SocketListener packetSender = new SocketListener(this,serverSocket);
        Thread sendingThread = new Thread(packetSender);
        sendingThread.start();
    }

    /**
     * starts the socket sender
     * @throws IOException
     */
    public  void  StartSocketSender() throws IOException {
        PacketSender packetSender = new PacketSender(this);
        Thread sendingThread = new Thread(packetSender);
        sendingThread.start();
    }


    public boolean RegisterSocket(String host,int port){
        try {
            return RegisterSocket(new Socket(host, port));
        } catch (IOException e) {
           return false;
        }
    }

	public boolean RegisterSocket(Socket s)
    {

		PacketListener packetListner = new PacketListener(s,this);
		Thread listningThread = new Thread(packetListner);
		listningThread.start();
		_packetListner.put(s, listningThread);
        _socketConnected.onSocket(s);

		return true;
	}
	
	public boolean UnbindSocket(Socket s)
	{
		
		Thread thread = _packetListner.get(s);
		thread.interrupt();
		_packetListner.remove(s);

		return true;
	}
	
	public IPacket<?> GetPacketInstance(int id)
	{
		IPacket<?> result = _packets.get(new Integer(id)).Instance();
		if(result == null)
		{
			System.out.println("unknown packet ID:" + id);
		}
		return result;
	}
	
	//sends packet
	public void SendPacket(IPacket<?> packet, Socket s)
	{
		_packetsOut.add(new TransportPair(packet,s));
	
	}
	
	public void SendPacket(IPacket<?> packet, Socket[] socket)
	{
		for(int x =0; x < socket.length; x++)
		{
			_packetsOut.add(new TransportPair(packet,socket[x]));
		}
		
	}
	
	public void SendPacket(IPacket<?> packet, Set<Socket> keySet) {
		for(Socket s : keySet)
			_packetsOut.add(new TransportPair(packet,s));
		
	}
	

	public void SendPacket(IPacket<?> packet,Socket[] socket,Socket excluded)
	{
		for(int x =0; x < socket.length; x++)
		{
			if(socket[x] != excluded)
			_packetsOut.add(new TransportPair(packet,socket[x]));
		}
	}
	
	public void SendPacket(IPacket<?> packet, Set<Socket> keySet,Socket excluded) {
		for(Socket s : keySet)
		{
			if(s != excluded)
			{
				_packetsOut.add(new TransportPair(packet,s));
			}
		}
		
	}
	
	protected void RegisterPacket(IPacket<?> packet)
	{
		_packets.put(packet.Id(),packet);
	}


	public void OnConnected(ISocketConnect connection)
	{
        _socketConnected = connection;
	}

	public void OnPacket(int id, IPacketCallback callback)
	{
        _packetCallback.put(id,callback);
	}

	public void UnbindPacket(int id){
		_packetCallback.remove(id);
	}

	public void  ClearPacketHandles(){
		_packetCallback.clear();
	}

}
