package com.capnet.share;

import com.capnet.share.networking.PacketManager;
import com.capnet.share.networking.packets.Message;

/**
 * Created by michaelpollind on 4/25/16.
 */
public class BaseMessage {
    protected BasePlayerService _playerService;
    protected PacketManager _manger;

    public  BaseMessage(BasePlayerService playerService, PacketManager manager)
    {
        this._playerService = playerService;
        this._manger = manager;

    }
}
