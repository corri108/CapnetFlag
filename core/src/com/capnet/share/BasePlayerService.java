package com.capnet.share;

import com.capnet.share.Entities.Player;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by michaelpollind on 4/25/16.
 */
public class BasePlayerService {
    protected ConcurrentHashMap<Integer,Player> _playerCollection = new ConcurrentHashMap<>();
    protected Map map;
    public BasePlayerService(Map map)
    {
        this.map = map;
    }

    public void Update()
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
