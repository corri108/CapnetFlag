package com.capnet.server;

import com.capnet.share.Map;
import com.capnet.share.networking.PacketManager;

/**
 * Created by michaelpollind on 4/25/16.
 */
public class HostMap extends Map {

    PacketManager _manager;

    //TODO: generate map
    public  HostMap(PacketManager manager )
    {
        super();
        GenerateMap();
        this._manager = manager;
    }

    public  HostMap()
    {

    }


}
