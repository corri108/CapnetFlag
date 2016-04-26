package com.capnet.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.capnet.share.BaseMap;
import com.capnet.share.Entities.Player;
import com.capnet.share.networking.PacketManager;

import java.net.Socket;

/**
 * Created by michaelpollind on 4/19/16.
 */
public class GameScreen implements Screen {
    private PlayerLocalService _playerManager ;
    private LocalGameMap _map;
    private OrthographicCamera _camera;
    private SpriteBatch _batch;


    public  GameScreen(Main main, PacketManager manager, Socket serverSocket)
    {
        _batch = new SpriteBatch();
        _map= new LocalGameMap();
        _playerManager = new PlayerLocalService(manager,serverSocket,_map);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Player p = _playerManager.GetOwnedPlayer();
        if(p != null)
            _camera.position.set(p.Location.x,p.Location.y,0);

        _camera.update();
        _batch.setProjectionMatrix(_camera.combined);
        _batch.begin();
        _playerManager.Update();
        _playerManager.Draw();
        _batch.end();
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) width / (float) height;
        _camera = new OrthographicCamera(2f * aspectRatio, 2f);
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
