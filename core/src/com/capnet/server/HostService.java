package com.capnet.server;

import com.capnet.share.Player;
import com.capnet.share.networking.IPacketCallback;
import com.capnet.share.networking.ISocketConnect;
import com.capnet.share.networking.PacketManager;
import com.capnet.share.networking.TransportPair;
import com.capnet.share.networking.packets.IPacket;
import com.capnet.share.networking.packets.Player_Simple_2;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by michaelpollind on 4/21/16.
 */
public class HostService {


    private  PacketManager _manager = new PacketManager();
    private  PlayerHostService _playerHost = new PlayerHostService();


    public HostService(int port) throws IOException {
        _manager.OnConnected(socket -> ((Runnable) () -> {
            //wait 5 seconds for a client to connect else unbind them

            try {
                Thread.sleep(5000);
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

            //unbind the socket and move to player host
            _manager.UnbindSocket(pair.Out);
            _playerHost.RegisterClient(pair.Out,new Player());
            _manager.SendPacket(pair.Packet,pair.Out);
        },Player.class);


        _manager.StartSocketSender();
        _manager.StartSocketListener(port);
    }
}
