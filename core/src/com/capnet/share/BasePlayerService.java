package com.capnet.share;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.capnet.game.InputHandle;
import com.capnet.share.Entities.Player;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by michaelpollind on 4/25/16.
 */
public class BasePlayerService {
    protected ConcurrentHashMap<Integer,Player> _playerCollection = new ConcurrentHashMap<>();
    protected  static  final  float PLAYER_BASE_VELOCITY = 20.0f;
    protected Map map;
    public BasePlayerService(Map map)
    {
        this.map = map;
    }

    /**
     * general update for entities
     * @param delta
     */
    public void Update(float delta)
    {
        for (java.util.Map.Entry<Integer,Player> player: _playerCollection.entrySet()) {
            float multiplier = 1;
            if(map != null) {
                int speed = map.GetSpeed(player.getValue().Location);
                /*switch (speed) {
                    case MySquare.SLOW:
                        multiplier = .9f;
                        break;
                    case MySquare.MEDIUM:
                        multiplier = 1.2f;
                        break;
                    case MySquare.FAST:
                        multiplier = 3f;
                        break;
                    case MySquare.SUPER_SLOW:
                        multiplier = .3f;
                        break;
                }*/

                System.out.println(speed);
            }
            Vector2 velocity = new Vector2(player.getValue().Velocity.x* delta *multiplier,player.getValue().Velocity.y* delta *multiplier);
            player.getValue().Location = player.getValue().Location.add(velocity);
        }
    }

    /**
     * functions at a lower tick rate
     * @param delta
     */
    public void PacketUpdate(float delta)
    {

    }



    public  Player GetPlayer(int id)
    {
        return _playerCollection.get(id);
    }

    public Iterator<Player> player_iterator()
    {
        return _playerCollection.values().iterator();
    }


}
