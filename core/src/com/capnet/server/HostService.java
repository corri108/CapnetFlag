package com.capnet.server;

import com.capnet.game.Player;
import com.capnet.share.IPacketCallback;
import com.capnet.share.ISocketConnect;
import com.capnet.share.PacketManager;
import com.capnet.share.Packets.Player_1;
import com.capnet.share.TransportPair;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
                        _manager.UnbindSocket(socket);
                    }
                }.run();
            }
        });
        _manager.OnPacket(1, new IPacketCallback() {
            @Override
            public void onPacket(TransportPair pair) {
                Player_1 pkt = (Player_1) pair.Packet;
                //unbind the socket and move to player host
                _manager.UnbindSocket(pair.Out);
                _playerHost.RegisterClient(pair.Out,new Player());
                _manager.SendPacket(pkt,pair.Out);

            }
        });
        _manager.StartSocketSender();
        _manager.StartSocketListener(port);
    }
}
