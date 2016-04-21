package com.capnet.game;

import com.capnet.share.IPacketCallback;
import com.capnet.share.PacketManager;
import com.capnet.share.Packets.Player_1;
import com.capnet.share.TransportPair;

/**
 * Created by michaelpollind on 4/21/16.
 */
public class PlayerManager {

    public PlayerManager(PacketManager pkt) {
        pkt.OnPacket(1, new IPacketCallback() {
            @Override
            public void onPacket(TransportPair pair) {
                Player_1 player = (Player_1) pair.Packet;
            }
        });

    }

}
