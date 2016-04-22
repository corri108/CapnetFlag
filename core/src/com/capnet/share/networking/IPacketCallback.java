package com.capnet.share.networking;

/**
 * Created by michaelpollind on 4/9/16.
 */
public interface IPacketCallback {
    void onPacket(TransportPair pair);
}
