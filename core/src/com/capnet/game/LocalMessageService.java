package com.capnet.game;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.capnet.share.BaseMessage;
import com.capnet.share.BasePlayerService;
import com.capnet.share.networking.PacketManager;

/**
 * Created by michaelpollind on 4/25/16.
 */
public class LocalMessageService extends BaseMessage{


    private TextField entry;

    public LocalMessageService(Main main,BasePlayerService playerService, PacketManager manager) {
        super(playerService, manager);
    }
}
