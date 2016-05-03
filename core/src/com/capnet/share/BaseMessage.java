package com.capnet.share;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.capnet.share.networking.PacketManager;

/**
 * Created by michaelpollind on 4/25/16.
 */
public class BaseMessage {
    protected BasePlayerService _playerService;
    protected PacketManager _manger;

    private TextField _entry;

    public  BaseMessage(BasePlayerService playerService, PacketManager manager)
    {
        this._playerService = playerService;
        this._manger = manager;

    }
}
