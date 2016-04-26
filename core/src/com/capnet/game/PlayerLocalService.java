package com.capnet.game;

import com.capnet.share.BaseMap;
import com.capnet.share.Entities.Player;
import com.capnet.share.BasePlayerService;
import com.capnet.share.networking.PacketManager;
import com.capnet.share.Entities.PlayerSimple;
import com.capnet.share.networking.packets.ClientHandshake;

import java.net.Socket;
import java.util.Iterator;

/**
 * Created by michaelpollind on 4/21/16.
 */
public class PlayerLocalService extends BasePlayerService {
    private  int _playerOwned = -1;
    private Socket _server;

    public PlayerLocalService(PacketManager manager, Socket server, BaseMap map) {
        super(map);
        _server = server;
        manager.OnPacket(pair -> {

        },PlayerSimple.class);

        manager.OnPacket(pair -> {
            _playerCollection.put(pair.Packet.id,pair.Packet);
        }, Player.class);

        manager.OnPacket(pair -> {
            _playerOwned = pair.Packet.id;

        }, ClientHandshake.class);

        manager.SendPacket(new Player(),server);
    }

    public Player GetOwnedPlayer()
    {
        return  _playerCollection.get(_playerOwned);
    }

    public Iterator<Player> player_iterator()
    {
        return _playerCollection.values().iterator();
    }




}
