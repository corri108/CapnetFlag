package com.capnet.share.networking;

import com.capnet.share.networking.packets.IPacket;

/**
 * Created by michaelpollind on 4/9/16.
 */
public interface IPacketCallback<T extends IPacket> {
    void onPacket(TransportPair<T> pair);
}
