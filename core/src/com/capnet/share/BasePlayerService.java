package com.capnet.share;

import com.capnet.share.Entities.Player;
import com.capnet.share.networking.PacketManager;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by michaelpollind on 4/25/16.
 */
public class BasePlayerService {
    protected ConcurrentHashMap<Integer,Player> _playerCollection = new ConcurrentHashMap<>();
    protected  boolean _isServer = false;

    public BasePlayerService(BaseMap map)
    {

    }

    public void Update()
    {

    }
}
