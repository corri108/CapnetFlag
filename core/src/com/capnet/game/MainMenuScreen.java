package com.capnet.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import javafx.scene.Scene;

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
    TextButton _login;
    TextField _nameField;
    TextField _ipField;
    TextField _portField;
    BitmapFont _font;
    TextButtonStyle _buttonStyle;

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
        _buttonStyle.checked = uiSkin.getDrawable("default-round");
        //end style

        //button connect
        _login = new TextButton("Connect", _buttonStyle);
        _login.setPosition(270,125);
        _login.setWidth(100);
        _login.setHeight(15);
        _login.addListener(new ClickListener(){
            public void clicked(InputEvent e, float x, float y) {
                main.setScreen(new GameScreen(main));
                Gdx.app.log("Click", "performed"); // -> never happend
            }
        });
        //end button

        //fields of text
        _nameField = new TextField("user", uiSkin);
        _nameField.setPosition(270,300);
        _nameField.setWidth(400);
        _nameField.setHeight(15);


        _ipField = new TextField("localhost", uiSkin);
        _ipField.setPosition(270,250);
        _ipField.setWidth(400);
        _ipField.setHeight(15);

        _portField = new TextField("25565", uiSkin);
        _portField.setPosition(270,200);
        _portField.setWidth(400);
        _portField.setHeight(15);
        //end text fields


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
        _stage.addActor(titleField);

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
