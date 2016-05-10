package com.capnet.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.capnet.share.Entities.Packets.PlayerInfo;
import com.capnet.share.Map;
import com.capnet.share.Entities.Player;
import com.capnet.share.networking.PacketManager;
import com.capnet.share.packets.GameState;
import com.capnet.share.packets.PlayerResult;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Created by michaelpollind on 4/19/16.
 */
public class GameScreen implements Screen {
    private PlayerLocalService _playerManager ;
    private Map _map;
    private OrthographicCamera _camera;
    private SpriteBatch _batch;
    private  SpriteBatch _overlay;
    private ShapeRenderer _shape;
    private Label _label;
    BitmapFont bmf;
    private Stage _stage;
    private Container _container;

    private  int _width = 0;
    private  int _height = 0;

    private  boolean _isDirty = false;

    private  Socket _serverSocket;

    private ArrayList<Label> playerOrder = new ArrayList<>();

    public int GameState = com.capnet.share.packets.GameState.WAITING;

    public  GameScreen(Main main, PacketManager manager, Socket serverSocket, String initialName)
    {
        Skin uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

        _serverSocket = serverSocket;

        _stage = new Stage(new ScreenViewport());

        _shape = new ShapeRenderer();

        bmf = new BitmapFont();
        Label.LabelStyle ls = new Label.LabelStyle(bmf, Color.WHITE);
        _label = new Label("test", ls);

        _batch = new SpriteBatch();
        _overlay = new SpriteBatch();
        manager.OnPacket(pair -> {
            _map = pair.Packet;
            _playerManager.UpdateMap(_map);
        },Map.class);

        manager.OnPacket(pair -> {
            CharSequence out = pair.Packet.GetPlayer().GetName();
           playerOrder.add(new Label(out,uiSkin));
            _stage.addActor(playerOrder.get(playerOrder.size()-1));
            _isDirty = true;
        }, PlayerResult.class);

        _playerManager = new PlayerLocalService(manager,serverSocket,_map, initialName);



        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        _camera = new OrthographicCamera(500, 500 * (h / w));

        _camera.position.set(_camera.viewportWidth / 2f, _camera.viewportHeight / 2f, 0);
        _camera.update();

        _container = new Container();
        //_container.background();

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

        _playerManager.Draw(_shape, _batch, bmf,
                new Vector2(_camera.position.x - _camera.viewportWidth / 2,
                        _camera.position.y + _camera.viewportWidth / 3));


        if(_map != null && _map.GetGamestate() == com.capnet.share.packets.GameState.CLOSING) {
            _stage.act();
            _stage.draw();
        }

        if(_isDirty)
        {
            resize( Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            _isDirty = false;
        }
    }

    @Override
    public void resize(int width, int height) {

        _stage.getViewport().update(width, height,true);

        _width = width;
        _height = height;

        for(int x = 0; x < playerOrder.size(); x++)
        {
            playerOrder.get(x).setPosition(width/2.0f,(height/2.0f) + x * 20, Align.center);
        }


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
