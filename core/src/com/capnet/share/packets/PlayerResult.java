package com.capnet.share.packets;

import com.capnet.share.Entities.Packets.PlayerInfo;
import com.capnet.share.Entities.Packets.PlayerSimple;
import com.capnet.share.Entities.Player;

import java.nio.ByteBuffer;

/**
 * Created by michaelpollind on 5/9/16.
 */
public class PlayerResult extends PlayerInfo {
    public  PlayerResult(Player player)
    {
        super(player);
    }

    public  PlayerResult()
    {

    }



}
