package com.capnet.share;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SocketListener implements Runnable{

	private PacketManager _manager;
	private ServerSocket _socket;
	public SocketListener(PacketManager manager,ServerSocket socket)
	{
		_socket = socket;
		_manager = manager;
	}
	@Override
	public void run() {
		System.out.println("Started Socket Listening Thread");
		while(true)
		{
		
			try {
				Socket connection = _socket.accept();
				
				//add a new socket to the collection
				System.out.println("New unregistered connection");
				
				_manager.RegisterSocket(connection);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
