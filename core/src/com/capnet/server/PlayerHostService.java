package com.capnet.server;

import com.capnet.share.BaseMap;
import com.capnet.share.BasePlayerService;
import com.capnet.share.Entities.Packets.PlayerInfo;
import com.capnet.share.Entities.Player;
import com.capnet.share.Entities.Packets.PlayerSimple;
import com.capnet.share.networking.PacketManager;
import com.capnet.share.packets.ClientHandshake;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by michaelpollind on 4/21/16.
 */
public class PlayerHostService extends BasePlayerService{

    protected ConcurrentHashMap<Socket,Player> _playerSocketMapping = new ConcurrentHashMap<>();

    private PacketManager _manager = new PacketManager();

    public  PlayerHostService(BaseMap map) throws IOException {
        super(map);

        _manager.OnPacket(pair -> {
            _playerSocketMapping.get(pair.Out).Location = pair.Packet.position;
            _playerSocketMapping.get(pair.Out).Velocity = pair.Packet.velocity;
        }, PlayerSimple.class);
        _manager.StartSocketSender();

    }

    public  void RegisterClient(Socket socket, Player player) {
        //send the player packet with the player id
        _manager.SendPacket(new ClientHandshake(player.GetPlayerId()),socket);
        _manager.SendPacket(new PlayerInfo(player),socket);

        _playerSocketMapping.put(socket,player);
        _playerCollection.put(player.GetPlayerId(),player);
        _manager.RegisterSocket(socket);
    }



    @Override
    public  void  Update()
    {
        //loop through the players and send updated positions
        for (Map.Entry<Socket,Player> iter : _playerSocketMapping.entrySet())
        {
            //send the update positions to all the clients
            _manager.SendPacket(new PlayerSimple(iter.getValue()),_playerSocketMapping.keySet());

        }
        super.Update();
    }

    public Player GetPlayer(Socket s)
    {
        return _playerSocketMapping.get(s);
    }

    public Set<Socket> GetSockets()
    {
        return _playerSocketMapping.keySet();
    }

}
