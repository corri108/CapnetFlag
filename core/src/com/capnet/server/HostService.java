package com.capnet.server;

import com.capnet.share.BaseMap;
import com.capnet.share.Entities.Player;
import com.capnet.share.BasePlayerService;
import com.capnet.share.networking.PacketManager;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by michaelpollind on 4/21/16.
 */
public class HostService  {

    private  PacketManager _manager = new PacketManager();
    private  PlayerHostService _playerHost = new PlayerHostService(new BaseMap());
    private  int _playerId = 0;

    public HostService(int port) throws IOException {

        _manager.OnConnected(socket -> ((Runnable) () -> {
            //wait 15 seconds for a client to connect else unbind them

            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("client has been disconnected due to being unresolved:" + socket);
            if (_manager.UnbindSocket(socket)) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).run());
        _manager.OnPacket(pair -> {
            _playerId++;
            System.out.println("host connected with name:" + pair.Packet.name);
            //unbind the socket and move to player host
            _manager.UnbindSocket(pair.Out);

            //set a new id
            pair.Packet.id = _playerId;
            //register the client
            _playerHost.RegisterClient(pair.Out,pair.Packet);
        },Player.class);


        _manager.StartSocketSender();
        _manager.StartSocketListener(port);
    }
}
