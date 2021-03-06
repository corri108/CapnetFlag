package com.capnet.share;

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
            if(map != null)
                map.UpdatePlayer(player.getValue(),delta);
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
