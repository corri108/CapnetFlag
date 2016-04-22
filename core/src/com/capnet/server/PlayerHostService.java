package com.capnet.server;

import com.capnet.game.Player;
import com.capnet.share.networking.PacketManager;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by michaelpollind on 4/21/16.
 */
public class PlayerHostService {

    private PacketManager _manager = new PacketManager();
    public ConcurrentHashMap<Socket,Player> _playerCollection = new ConcurrentHashMap<>();

    public  PlayerHostService()
    {

    }

    public  void RegisterClient(Socket socket, Player player) {
        _playerCollection.put(socket,player);
        _manager.RegisterSocket(socket);


    }

}
