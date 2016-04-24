package com.capnet.share.networking;

import com.capnet.share.networking.packets.IPacket;
import com.capnet.share.networking.packets.PacketHeading;

import org.reflections.Reflections;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PacketManager {

	protected  ConcurrentLinkedQueue<TransportPair> _packetsOut = new ConcurrentLinkedQueue<TransportPair>();
	protected ConcurrentHashMap<Integer,IPacketCallback> _packetCallback = new ConcurrentHashMap<Integer,IPacketCallback>();

	//selection of packets to match against
	protected HashMap<Integer, Class<? extends  IPacket>> _packets = new HashMap<>();
	protected  HashMap<Class<? extends  IPacket>,Integer> _packet_id = new HashMap<>();

    protected ISocketConnect _socketConnected = socket -> {

    };
	protected ISocketConnect _socketDisconnect = socket -> {

    };

	private ConcurrentHashMap<Socket,Thread> _packetListner = new ConcurrentHashMap<Socket,Thread>();


	public PacketManager()
	{
		Reflections reflections = new Reflections("com.capnet");
		Set<Class<?>> packets = reflections.getTypesAnnotatedWith(PacketHeading.class);

		int id = 0;
		for(Class<?> packet : packets)
		{
			if(IPacket.class.isAssignableFrom(packet))
			{
				_packets.put(id, (Class<? extends IPacket>) packet);
				_packet_id.put((Class<? extends IPacket>) packet,id);
				id++;
			}
		}


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

	/*
	* Unbinde the socket from the packet manager
	* */
	public boolean UnbindSocket(Socket s)
	{
		if(_packetListner.contains(s)) {
			Thread thread = _packetListner.get(s);
			thread.interrupt();
			_packetListner.remove(s);
			return true;
		}
		return false;
	}

	/*
	*returns an instance of the associated packet id
	 */
	public IPacket GetPacketInstance(int id) throws IllegalAccessException, InstantiationException {
		Class<? extends  IPacket> result = _packets.get(new Integer(id));
		if(result == null)
		{
			System.out.println("unknown packet ID:" + id);
		}
		return result.newInstance();
	}
	
	//sends packet
	public void SendPacket(IPacket packet, Socket s)
	{
		_packetsOut.add(new TransportPair(packet,s));
	
	}
	
	public void SendPacket(IPacket packet, Socket[] socket)
	{
		for(int x =0; x < socket.length; x++)
		{
			_packetsOut.add(new TransportPair(packet,socket[x]));
		}
		
	}
	
	public void SendPacket(IPacket packet, Set<Socket> keySet) {
		for(Socket s : keySet)
			_packetsOut.add(new TransportPair(packet,s));
		
	}
	

	public void SendPacket(IPacket packet,Socket[] socket,Socket excluded)
	{
		for(int x =0; x < socket.length; x++)
		{
			if(socket[x] != excluded)
			_packetsOut.add(new TransportPair(packet,socket[x]));
		}
	}
	
	public void SendPacket(IPacket packet, Set<Socket> keySet,Socket excluded) {
		for(Socket s : keySet)
		{
			if(s != excluded)
			{
				_packetsOut.add(new TransportPair(packet,s));
			}
		}
		
	}


	//called when either a client has connected on either the server or client
	public void OnConnected(ISocketConnect connection)
	{
        _socketConnected = connection;
	}


	public void OnDisconnect(ISocketConnect connection)
	{
		_socketDisconnect = connection;
	}

	public<T extends IPacket> void OnPacket(int id, IPacketCallback callback)
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
