package com.capnet.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.capnet.share.Map;
import com.capnet.share.Entities.Packets.PlayerInfo;
import com.capnet.share.Entities.Player;
import com.capnet.share.BasePlayerService;
import com.capnet.share.networking.PacketManager;
import com.capnet.share.Entities.Packets.PlayerSimple;
import com.capnet.share.packets.ClientHandshake;
import com.capnet.share.packets.GameState;
import com.capnet.share.packets.InPlay;
import com.capnet.share.packets.Ready;

import java.net.Socket;

/**
 * Created by michaelpollind on 4/21/16.
 */
public class PlayerLocalService extends BasePlayerService {
    private  int _playerOwned = -1;
    private Socket _server;
    private PacketManager _manager;

    private  InputHandle left;
    private  InputHandle up;
    private  InputHandle right;
    private  InputHandle down;

    public PlayerLocalService(PacketManager manager, Socket server, Map map, String initialName) {
        super(map);
        _server = server;
        _manager = manager;
        manager.OnPacket(pair -> {
            //very simple player positon update but this can be fixed
            Player p = _playerCollection.get(pair.Packet.PlayerId());
            if(p != null) {
                p.Location = pair.Packet.position;
                p.Velocity = pair.Packet.velocity;
                p.isReady = pair.Packet.ready;
            }
        },PlayerSimple.class);

        manager.OnPacket(pair -> {
            _playerCollection.put(pair.Packet.GetPlayer().GetPlayerId(),pair.Packet.GetPlayer());
        }, PlayerInfo.class);

        manager.OnPacket(pair -> {
            Thread t = new Thread(() -> {
                while (PlayerLocalService.super.map == null)
                {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                PlayerLocalService.super.map.SetGamestate(pair.Packet.gameState);
            });
            t.start();

        }, GameState.class);

        manager.OnPacket(pair -> {
            Player p = _playerCollection.get(pair.Packet.id);
            if(p != null) {
                p.isInPlay = pair.Packet.inplay;
            }
        }, InPlay.class);

        manager.OnPacket(pair -> {
            _playerOwned = pair.Packet.id;

        }, ClientHandshake.class);

        manager.OnPacket(pair -> {
            Player p = _playerCollection.get(pair.Packet.id);
            if(p != null) {
                p.isReady = pair.Packet.ready;
            }
        },Ready.class);


        Player toSend = new Player();
        toSend.SetName(initialName);
        manager.SendPacket(new PlayerInfo(toSend),server);

        left = new InputHandle(Input.Keys.A,manager,server);
        up = new InputHandle(Input.Keys.W,manager,server);
        right = new InputHandle(Input.Keys.D,manager,server);
        down = new InputHandle(Input.Keys.S,manager,server);
    }

    public void  UpdateMap(Map map)
    {
        this.map = map;
    }

    public Player GetOwnedPlayer()
    {
        return  _playerCollection.get(_playerOwned);
    }


    @Override
    public void Update(float delta)
    {
        super.Update(delta);

        Player p = this.GetOwnedPlayer();
        if(p != null) {
            if(p.isInPlay)
            {
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    p.isReady = !p.isReady;
                    _manager.SendPacket(new Ready(p.isReady, p.GetPlayerId()), _server);
                }

                if (left.Update())
                    p.Velocity = new Vector2(-PLAYER_BASE_VELOCITY * (left.IsPressed() == true ? 1 : 0), p.Velocity.y);
                if (up.Update())
                    p.Velocity = new Vector2(p.Velocity.x, PLAYER_BASE_VELOCITY * (up.IsPressed() == true ? 1 : 0));
                if (right.Update())
                    p.Velocity = new Vector2(PLAYER_BASE_VELOCITY * (right.IsPressed() == true ? 1 : 0), p.Velocity.y);

                if (down.Update())
                    p.Velocity = new Vector2(p.Velocity.x, -PLAYER_BASE_VELOCITY * (down.IsPressed() == true ? 1 : 0));
            }
        }
    }

    public  void Draw(ShapeRenderer shape, Batch batch, BitmapFont font, Vector2 screenLoc)
    {
        for (java.util.Map.Entry<Integer,Player> player: _playerCollection.entrySet())
        {
            if(player.getValue().isInPlay)
                player.getValue().Draw(shape);
        }

        int i = 0;
        for (java.util.Map.Entry<Integer,Player> player: _playerCollection.entrySet())
        {
            batch.begin();
            if(player.getValue().isInPlay)
            {
                if(map.GetGamestate() == GameState.WAITING)
                {
                    if (player.getValue().isReady) {
                        font.draw(batch, player.getValue().GetName() + ": !", player.getValue().Location.x - 10, player.getValue().Location.y + 20);
                    } else {
                        font.draw(batch, player.getValue().GetName() + ": X", player.getValue().Location.x - 10, player.getValue().Location.y + 20);
                    }
                }
                else
                    font.draw(batch, player.getValue().GetName(), player.getValue().Location.x - 10, player.getValue().Location.y + 20);
            }
           // font.draw(batch, player.getValue().GetName() + ": " + player.getValue().Wins, screenLoc.x + 10, screenLoc.y - 20 * i);
            batch.end();

            ++i;
        }
    }
}
