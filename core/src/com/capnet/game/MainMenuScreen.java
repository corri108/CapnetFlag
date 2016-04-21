package com.capnet.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

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
    TextButton button;
    TextField namefield;
    TextField ipfield;
    TextField portfield;
    BitmapFont font;
    TextButtonStyle textButtonStyle;
    Dialog dialog;

    public  MainMenuScreen(Main main)
    {
        this.main = main;

        _stage = new Stage();
        Skin uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        font = new BitmapFont();
        //make menu takeup the whole screen
        dialog = new Dialog("Capnet Flag : Menu", uiSkin);
        dialog.setPosition(0,600);
        dialog.setWidth(640);
        dialog.setHeight(480);
        //end menu window

        //textButtonStyle
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = uiSkin.getDrawable("default-round");
        textButtonStyle.down = uiSkin.getDrawable("default-round-down");
        textButtonStyle.checked = uiSkin.getDrawable("default-round");
        //end style

        //button connect
        button = new TextButton("Connect", textButtonStyle);
        button.setPosition(270,125);
        button.setWidth(100);
        button.setHeight(40);
        //end button

        //fields of text
        namefield = new TextField("Enter Name:", uiSkin);
        namefield.setPosition(270,300);
        namefield.setWidth(100);
        namefield.setHeight(40);
        ipfield = new TextField("Enter IP:", uiSkin);
        ipfield.setPosition(270,250);
        ipfield.setWidth(100);
        ipfield.setHeight(40);
        portfield = new TextField("Enter Port:", uiSkin);
        portfield.setPosition(270,200);
        portfield.setWidth(100);
        portfield.setHeight(40);
        //end text fields

        //add all of our objects to the render list
        _stage.addActor(dialog);
        _stage.addActor(button);
        _stage.addActor(namefield);
        _stage.addActor(ipfield);
        _stage.addActor(portfield);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        _stage.act(Gdx.graphics.getDeltaTime());
        _stage.draw();
    }

    @Override
    public void resize(int width, int height) {

        _stage.getViewport().update(width, height,true);
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
