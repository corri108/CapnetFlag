package com.capnet.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.capnet.share.BaseMap;
import com.capnet.share.networking.PacketManager;

import java.net.Socket;

/**
 * Created by michaelpollind on 4/19/16.
 */
public class GameScreen implements Screen {
    private PlayerLocalService _playerManager ;
    private BaseMap _map;
    public  GameScreen(Main main, PacketManager manager, Socket serverSocket)
    {
        _map= new BaseMap();
        _playerManager = new PlayerLocalService(manager,serverSocket,_map);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
