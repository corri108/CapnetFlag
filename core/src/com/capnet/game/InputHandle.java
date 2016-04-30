package com.capnet.game;

import com.badlogic.gdx.Gdx;
import com.capnet.share.Entities.InputSnapshot;
import com.capnet.share.networking.PacketManager;

import java.net.Socket;

/**
 * Created by michaelpollind on 4/30/16.
 */
public class InputHandle {
    private  int key;
    private  PacketManager manager;
    private Socket server;
    private  boolean isPressed;

    public  boolean IsPressed()
    {
        return isPressed;
    }

    public  int Key()
    {
        return  key;
    }

    public InputHandle(int key, PacketManager manager, Socket server)
    {
        this.key = key;
        this.manager = manager;
        this.server = server;

    }

    public boolean  Update()
    {
        boolean press = Gdx.input.isKeyPressed(key);
        if(isPressed != press) {
            isPressed = press;
            manager.SendPacket(new InputSnapshot(key, isPressed), server);

            return  true;
        }
        return false;

    }

}
