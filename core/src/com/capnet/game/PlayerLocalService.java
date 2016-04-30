package com.capnet.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.capnet.share.Map;
import com.capnet.share.Entities.Packets.PlayerInfo;
import com.capnet.share.Entities.Player;
import com.capnet.share.BasePlayerService;
import com.capnet.share.networking.PacketManager;
import com.capnet.share.Entities.Packets.PlayerSimple;
import com.capnet.share.packets.ClientHandshake;

import java.net.Socket;

/**
 * Created by michaelpollind on 4/21/16.
 */
public class PlayerLocalService extends BasePlayerService {
    private  int _playerOwned = -1;
    private Socket _server;
    private PacketManager _manager;

    public PlayerLocalService(PacketManager manager, Socket server, Map map) {
        super(map);
        _server = server;
        _manager = manager;
        manager.OnPacket(pair -> {
            //very simple player positon update but this can be fixed
            Player p = _playerCollection.get(pair.Packet.PlayerId());
            p.Location = pair.Packet.position;
            p.Velocity = pair.Packet.velocity;
        },PlayerSimple.class);

        manager.OnPacket(pair -> {
            _playerCollection.put(pair.Packet.GetPlayer().GetPlayerId(),pair.Packet.GetPlayer());
        }, PlayerInfo.class);

        manager.OnPacket(pair -> {
            _playerOwned = pair.Packet.id;

        }, ClientHandshake.class);

        manager.SendPacket(new PlayerInfo(new Player()),server);
    }

    public Player GetOwnedPlayer()
    {
        return  _playerCollection.get(_playerOwned);
    }

    @Override
    public  void Update()
    {
        super.Update();

        //this implementation should be temporary
        Player current = this.GetOwnedPlayer();
        if(current != null)
        {
            if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
            {
                current.Location.add(-.05f,0);
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
            {
                current.Location.add(.05f,0);
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            {
                current.Location.add(0,.05f);
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            {
                current.Location.add(0,-.05f);
            }

            _manager.SendPacket(new PlayerSimple(current),_server);

        }

    }

    public  void Draw()
    {
        for (java.util.Map.Entry<Integer,Player> player: _playerCollection.entrySet()) {
            player.getValue().Draw();

        }
    }
}
