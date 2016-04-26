package com.capnet.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.capnet.server.HostService;
import com.capnet.share.networking.PacketManager;

import java.io.IOException;

/**
 * Created by michaelpollind on 4/19/16.
 */
public class MainMenuScreen implements Screen {
    //tutorial stuff: http://www.gamefromscratch.com/post/2013/12/18/LibGDX-Tutorial-9-Scene2D-Part-3-UI-Skins.aspx

    private SpriteBatch batch;
    private Texture img;

    private Stage _stage;
    private Main main;

    //main window
    private TextButton _login;
    private TextField _nameField;
    private TextField _ipField;
    private TextField _portField;
    private BitmapFont _font;
    private TextButtonStyle _buttonStyle;
    private TextButtonStyle _toggledButtonStyle;
    private Button _isHostToggle;


    public  MainMenuScreen(Main main)
    {
        this.main = main;

        _stage = new Stage(new ScreenViewport());
        Skin uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        _font = new BitmapFont();

        //end menu window

        //textButtonStyle
        _buttonStyle = new TextButtonStyle();
        _buttonStyle.font = _font;
        _buttonStyle.up = uiSkin.getDrawable("default-round");
        _buttonStyle.down = uiSkin.getDrawable("default-round-down");
        _buttonStyle.checked = uiSkin.getDrawable("default-round-down");

        _toggledButtonStyle = new TextButtonStyle();
        _toggledButtonStyle.font = _font;
        _toggledButtonStyle.up = uiSkin.getDrawable("default-round");
        _toggledButtonStyle.down = uiSkin.getDrawable("default-round-down");
        _toggledButtonStyle.checked = uiSkin.getDrawable("default-round");
        //end style

        //button connect
        _login = new TextButton("Connect", _buttonStyle);
        _login.setPosition(270,125);
        _login.setWidth(100);
        _login.setHeight(15);
        _login.addListener(new ClickListener(){
            public void clicked(InputEvent e, float x, float y) {

                int port = Integer.parseInt(_portField.getText());
                String ipAddress = _ipField.getText();
                //TODO: get toggle to work
                try {
                    new HostService(port);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                PacketManager packetManager = new PacketManager();
                packetManager.OnConnected(socket -> {
                    main.setScreen(new GameScreen(main,packetManager,socket));
                });
                packetManager.RegisterSocket(ipAddress,port);
                try {
                    packetManager.StartSocketSender();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                Gdx.app.log("Click", "performed"); // -> never happend
            }
        });
        //end button

        //fields of text
        _nameField = new TextField("user", uiSkin);
        _nameField.setWidth(400);
        _nameField.setHeight(15);


        _ipField = new TextField("localhost", uiSkin);
        _ipField.setWidth(400);
        _ipField.setHeight(15);

        _portField = new TextField("25565", uiSkin);
        _portField.setWidth(400);
        _portField.setHeight(15);
        //end text fields

        //toggle works just need to change button when in toggle state
        _isHostToggle = new Button();
        _isHostToggle.toggle();
        _isHostToggle.setWidth(400);
        _isHostToggle.setHeight(15);
        _isHostToggle.setStyle(_buttonStyle);

        //host
        TextField hostField;
        //host
        hostField = new TextField("Host?", uiSkin);
        hostField.setPosition(120,100);
        hostField.setWidth(400);
        hostField.setHeight(15);

        TextField titleField;
        //title
        titleField = new TextField("Capnet Flag", uiSkin);
        titleField.setPosition(220,400);
        titleField.setWidth(200);
        titleField.setHeight(80);
        //end title

        //add all of our objects to the render list
        _stage.addActor(_login);
        _stage.addActor(_nameField);
        _stage.addActor(_ipField);
        _stage.addActor(_portField);
        _stage.addActor(_isHostToggle);
        _stage.addActor(titleField);
        _stage.addActor(hostField);

        Gdx.input.setInputProcessor(_stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        _stage.act(delta);
        _stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        _stage.getViewport().update(width, height,true);


        _login.setPosition(width/2.0f,(height/2.0f)+10 -15*6, Align.center);

        _nameField.setPosition(width/2.0f,(height/2.0f)+10 -16*2, Align.center);
         _ipField.setPosition(width/2.0f,(height/2.0f)+10 -16*3, Align.center);

        _portField.setPosition(width/2.0f,(height/2.0f)+10 -16*4, Align.center);

        _isHostToggle.setPosition(width/2.0f,(height/2.0f)+10 -16*10, Align.center);
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
        _stage.dispose();
    }
}
