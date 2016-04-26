package com.capnet.server;

import com.capnet.share.BaseMessage;
import com.capnet.share.BasePlayerService;
import com.capnet.share.networking.PacketManager;
import com.capnet.share.networking.packets.Message;

/**
 * Created by michaelpollind on 4/25/16.
 */
public class HostMessageService extends BaseMessage{
    public HostMessageService(PlayerHostService playerService, PacketManager manager)
    {
        super(playerService,manager);

        manager.OnPacket(pair -> {
            manager.SendPacket(new Message(playerService.GetPlayer(pair.Out),pair.Packet.Message),playerService.GetSockets());
        }, Message.class);



    }

}
