package com.capnet.game;

import com.capnet.share.BaseMessage;
import com.capnet.share.BasePlayerService;
import com.capnet.share.networking.PacketManager;

/**
 * Created by michaelpollind on 4/25/16.
 */
public class LocalMessageService extends BaseMessage{


    public LocalMessageService(BasePlayerService playerService, PacketManager manager) {
        super(playerService, manager);
    }
}
