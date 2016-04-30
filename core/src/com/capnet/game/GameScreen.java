package com.capnet.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.capnet.server.HostMap;
import com.capnet.share.Map;
import com.capnet.share.Entities.Player;
import com.capnet.share.networking.PacketManager;

import java.net.Socket;

/**
 * Created by michaelpollind on 4/19/16.
 */
public class GameScreen implements Screen {
    private PlayerLocalService _playerManager ;
    private Map _map;
    private OrthographicCamera _camera;
    private SpriteBatch _batch;


    public  GameScreen(Main main, PacketManager manager, Socket serverSocket)
    {
        _batch = new SpriteBatch();
        manager.OnPacket(pair -> {

            _map = pair.Packet;
        },HostMap.class);

        _playerManager = new PlayerLocalService(manager,serverSocket,_map);


        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        _camera = new OrthographicCamera(30, 30 * (h / w));

        _camera.position.set(_camera.viewportWidth / 2f, _camera.viewportHeight / 2f, 0);
        _camera.update();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        _playerManager.Update(delta);
        Player p = _playerManager.GetOwnedPlayer();
        if(p != null)
            _camera.position.set(p.Location.x,p.Location.y,0);

        _batch.setProjectionMatrix(_camera.combined);
        _camera.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _batch.begin();
        if(_map != null)
            _map.draw(_batch,1f);
        _playerManager.Draw();
        _batch.end();
    }

    @Override
    public void resize(int width, int height) {
        _camera.viewportWidth = 30f;
        _camera.viewportHeight = 30f * height/width;
        _camera.update();
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
