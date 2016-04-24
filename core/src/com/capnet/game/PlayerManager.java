package com.capnet.game;

import com.capnet.share.Entities.Player;
import com.capnet.share.networking.PacketManager;
import com.capnet.share.Entities.PlayerSimple;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by michaelpollind on 4/21/16.
 */
public class PlayerManager {
    public ConcurrentHashMap<Integer,Player > _players = new ConcurrentHashMap<>();
    private Socket _server;

    public PlayerManager(PacketManager manager, Socket server) {
        _server = server;
        manager.OnPacket(pair -> {

        },PlayerSimple.class);

        manager.OnPacket(pair -> {
            _players.put(pair.Packet.id,pair.Packet);
        }, Player.class);

        manager.SendPacket(new Player(),server);
    }


}
