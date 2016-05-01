package com.capnet.server;

import com.capnet.share.Map;
import com.capnet.share.Entities.Packets.PlayerInfo;
import com.capnet.share.networking.PacketManager;

import java.io.IOException;

/**
 * Created by michaelpollind on 4/21/16.
 */
public class Server {

    private Map _map;
    private  PacketManager _manager = new PacketManager();
    private  PlayerHostService _playerHost;
    private  int _playerId = 0;

    public Server(int port) throws IOException {
        _map = new HostMap(_manager);
       _playerHost = new PlayerHostService(_map);

        Runnable run = ((Runnable) () -> {

            long last_time = System.nanoTime();
            while (true) {
                long time = System.nanoTime();
                float delta_time =  ((time - last_time) / 1000000000.0f);
                last_time = time;

                //update look and tick
                _playerHost.Update(delta_time);
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        Thread thread = new Thread(run);
        thread.start();

        _manager.OnConnected(socket -> ((Runnable) () -> {
            //wait 15 seconds for a client to connect else unbind them

            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (_manager.UnbindSocket(socket)) {
                System.out.println("client has been disconnected due to being unresolved:" + socket);
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).run());
        _manager.OnPacket(pair -> {
            _playerId++;
            System.out.println("host connected with name:" + pair.Packet.GetPlayer().GetName());
            //unbind the socket and move to player host
            _manager.UnbindSocket(pair.Out);

            //set a new id
            pair.Packet.GetPlayer().SetId(_playerId);
            //register the client
            _playerHost.RegisterClient(pair.Out,pair.Packet.GetPlayer());
        },PlayerInfo.class);


        _manager.StartSocketSender();
        _manager.StartSocketListener(port);
    }
}
