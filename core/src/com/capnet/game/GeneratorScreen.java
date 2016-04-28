package com.capnet.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
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
 * created by willcorrin on 4/27/16
 */
public class GeneratorScreen implements Screen {
    //tutorial stuff: http://www.gamefromscratch.com/post/2013/12/18/LibGDX-Tutorial-9-Scene2D-Part-3-UI-Skins.aspx

    private SpriteBatch batch;
    private Texture img;

    private Stage _stage;
    private Main main;
    MySquareGenerator gen;

    public  GeneratorScreen(Main main)
    {
        this.main = main;
        //makes random stage with a random color out of
        // //BLUE, RED, ORANGE, GREEN, YELLOW, PURPLE
        gen = new MySquareGenerator(35, 80, 80, 300, 300, .5f);
        _stage = new Stage(new ScreenViewport());
        Skin uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        batch = new SpriteBatch();
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
        gen.draw(batch, 1f);
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
