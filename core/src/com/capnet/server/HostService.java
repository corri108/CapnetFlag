package com.capnet.server;

import com.capnet.game.Player;
import com.capnet.share.networking.ISocketConnect;
import com.capnet.share.networking.PacketManager;
import com.capnet.share.networking.packets.Player_1;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by michaelpollind on 4/21/16.
 */
public class HostService {


    private  PacketManager _manager = new PacketManager();
    private  PlayerHostService _playerHost = new PlayerHostService();


    public HostService(int port) throws IOException {
        _manager.OnConnected(new ISocketConnect() {
            @Override
            public void onSocket(final Socket socket) {
                new Runnable() {
                    @Override
                    public void run() {
                        //wait 5 seconds for a client to connect else unbind them
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(_manager.UnbindSocket(socket)) {
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.run();
            }
        });
        _manager.OnPacket(1, pair -> {
            Player_1 pkt = (Player_1) pair.Packet;
            //unbind the socket and move to player host
            _manager.UnbindSocket(pair.Out);
            _playerHost.RegisterClient(pair.Out,new Player());
            _manager.SendPacket(pkt,pair.Out);

        });
        _manager.StartSocketSender();
        _manager.StartSocketListener(port);
    }
}
