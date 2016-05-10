package com.capnet.server;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.capnet.game.InputHandle;
import com.capnet.share.Entities.InputSnapshot;
import com.capnet.share.Map;
import com.capnet.share.BasePlayerService;
import com.capnet.share.Entities.Packets.PlayerInfo;
import com.capnet.share.Entities.Player;
import com.capnet.share.Entities.Packets.PlayerSimple;
import com.capnet.share.networking.PacketManager;
import com.capnet.share.packets.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Random;

/**
 * Created by michaelpollind on 4/21/16.
 */
public class PlayerHostService extends BasePlayerService{

    protected ConcurrentHashMap<Socket,Player> _playerSocketMapping = new ConcurrentHashMap<>();

    private PacketManager _manager = new PacketManager();

    public  PlayerHostService(Map map) throws IOException {
        super(map);

        _manager.OnPacket(pair -> {
            _playerSocketMapping.get(pair.Out).Location = pair.Packet.position;
            _playerSocketMapping.get(pair.Out).Velocity = pair.Packet.velocity;
        }, PlayerSimple.class);

        _manager.OnPacket(pair -> {
            Player p = _playerSocketMapping.get(pair.Out);
            if(p != null) {
                p.isReady = pair.Packet.ready;
            }
        },Ready.class);

        _manager.OnPacket(pair -> {

            Player p =  _playerSocketMapping.get(pair.Out);

            if(pair.Packet.GetKey() == Input.Keys.A)
                p.Velocity = new Vector2(-PLAYER_BASE_VELOCITY * (pair.Packet.IsPressed() == true ? 1 : 0),  p.Velocity.y);
            if(pair.Packet.GetKey() == Input.Keys.W)
                p.Velocity = new Vector2(p.Velocity.x, PLAYER_BASE_VELOCITY * (pair.Packet.IsPressed() == true ? 1 : 0));
            if(pair.Packet.GetKey() == Input.Keys.D)
                p.Velocity = new Vector2(PLAYER_BASE_VELOCITY * (pair.Packet.IsPressed() == true ? 1 : 0), p.Velocity.y);
            if(pair.Packet.GetKey() == Input.Keys.S)
                p.Velocity =  new Vector2(p.Velocity.x, -PLAYER_BASE_VELOCITY * (pair.Packet.IsPressed() == true ? 1 : 0));
        }, InputSnapshot.class);
        _manager.StartSocketSender();

    }

    public  void RegisterClient(Socket socket, Player player) {
        //send the player packet with the player id
        _manager.SendPacket(new ClientHandshake(player.GetPlayerId()),socket);

        _playerSocketMapping.put(socket,player);
        _playerCollection.put(player.GetPlayerId(),player);
        _manager.RegisterSocket(socket);

        player.color = randColor();


        //send the players the new player
        _manager.SendPacket(new PlayerInfo(player),_playerSocketMapping.keySet(),socket);
        for (java.util.Map.Entry<Socket,Player> iter : _playerSocketMapping.entrySet())
        {
            _manager.SendPacket(new PlayerInfo(iter.getValue()),socket);
        }

        //send the player the map
        _manager.SendPacket(map,socket);
        _manager.SendPacket(new GameState(map.GetGamestate()),socket);

    }

    private Color randColor()
    {
        Random r = new Random();

        int rx = r.nextInt(11);

        switch (rx)
        {
            case 0:
                return Color.WHITE;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.RED;
            case 3:
                return Color.YELLOW;
            case 4:
                return Color.GREEN;
            case 5:
                return Color.PURPLE;
            case 6:
                return Color.PINK;
            case 7:
                return Color.ORANGE;
            case 8:
                return Color.CHARTREUSE;
            case 9:
                return Color.CORAL;
            case 10:
                return Color.MAGENTA;
            case 11:
                return Color.TEAL;
        }

        return Color.BLACK;
    }

    @Override
    public void  Update(float delta)
    {
        //loop through the players and send updated positions
        boolean isReady = true;
        for (java.util.Map.Entry<Socket,Player> iter : _playerSocketMapping.entrySet())
        {
            if(iter.getValue().isInPlay) {
                //send the update positions to all the clients
                _manager.SendPacket(new PlayerSimple(iter.getValue()), _playerSocketMapping.keySet());
                if (map.HitEnd(iter.getValue())) {
                    iter.getValue().isInPlay = false;
                    _manager.SendPacket(new GameState(GameState.CLOSING), iter.getKey());
                    _manager.SendPacket(new InPlay(false,iter.getValue().GetPlayerId()),_playerSocketMapping.keySet());
                    _manager.SendPacket(new PlayerResult(iter.getValue()),_playerSocketMapping.keySet());
                }
            }
            if(!iter.getValue().isReady)
                isReady = false;
        }
        if(map.GetGamestate() == GameState.WAITING && isReady && _playerSocketMapping.size() != 0)
        {
            map.SetGamestate(GameState.IN_PLAY);
            _manager.SendPacket(new GameState(GameState.IN_PLAY),_playerSocketMapping.keySet());
        }
        super.Update(delta);
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
