package com.capnet.game;

import com.capnet.share.networking.IPacketCallback;
import com.capnet.share.networking.PacketManager;
import com.capnet.share.networking.TransportPair;
import com.capnet.share.networking.packets.Player_Simple_2;

/**
 * Created by michaelpollind on 4/21/16.
 */
public class PlayerManager {

    public PlayerManager(PacketManager pkt) {
        pkt.OnPacket(1, new IPacketCallback() {
            @Override
            public void onPacket(TransportPair pair) {
                Player_Simple_2 player = (Player_Simple_2) pair.Packet;
            }
        });

    }

}
