package com.capnet.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.capnet.share.Map;
import com.capnet.share.Entities.Player;
import com.capnet.share.networking.PacketManager;
import com.capnet.share.packets.GameState;

import java.net.Socket;

/**
 * Created by michaelpollind on 4/19/16.
 */
public class GameScreen implements Screen {
    private PlayerLocalService _playerManager ;
    private Map _map;
    private OrthographicCamera _camera;
    private SpriteBatch _batch;
    private ShapeRenderer _shape;

    private Stage _stage;

    public int GameState = com.capnet.share.packets.GameState.WAITING;

    public  GameScreen(Main main, PacketManager manager, Socket serverSocket)
    {

        _stage = new Stage(new ScreenViewport());

        _shape = new ShapeRenderer();
        _batch = new SpriteBatch();
        manager.OnPacket(pair -> {
            _map = pair.Packet;
            _playerManager.UpdateMap(_map);
        },Map.class);

        _playerManager = new PlayerLocalService(manager,serverSocket,_map);


        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        _camera = new OrthographicCamera(500, 500 * (h / w));

        _camera.position.set(_camera.viewportWidth / 2f, _camera.viewportHeight / 2f, 0);
        _camera.update();

        Gdx.input.setInputProcessor(_stage);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        _playerManager.Update(delta);
        Player p = _playerManager.GetOwnedPlayer();
        if(p != null) {
            _camera.position.set(p.Location.x, Map.MAP_HEIGHT/2.0f,0);
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        _camera.update();
        _batch.setProjectionMatrix(_camera.combined);
        _shape.setProjectionMatrix(_camera.combined);

        if(_map != null)
            _map.draw(_shape);
        _playerManager.Draw(_shape);

        _batch.begin();
        _batch.end();

    }

    @Override
    public void resize(int width, int height) {
        _camera.viewportWidth = 500f * width/height;
        _camera.viewportHeight =Map.MAP_HEIGHT ;
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
