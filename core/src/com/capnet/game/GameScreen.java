package com.capnet.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.capnet.share.networking.PacketManager;

import java.net.Socket;

/**
 * Created by michaelpollind on 4/19/16.
 */
public class GameScreen implements Screen {
    private PlayerManager _playerManager ;
    public  GameScreen(Main main, PacketManager manager, Socket serverSocket)
    {
        _playerManager = new PlayerManager(manager,serverSocket);
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
