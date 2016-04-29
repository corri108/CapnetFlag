package com.capnet.server;

import com.badlogic.gdx.utils.compression.lzma.Base;
import com.capnet.share.BaseMap;
import com.capnet.share.networking.PacketManager;

import java.net.Socket;

/**
 * Created by michaelpollind on 4/25/16.
 */
public class HostMap extends BaseMap{

    PacketManager _manager;

    //TODO: generate map
    public  HostMap(PacketManager manager )
    {
        super();
        GenerateMap();
        this._manager = manager;
    }


}
