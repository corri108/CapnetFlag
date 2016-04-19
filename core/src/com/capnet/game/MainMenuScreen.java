package com.capnet.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

/**
 * Created by michaelpollind on 4/19/16.
 */
public class MainMenuScreen implements Screen {
    //tutorial stuff: http://www.gamefromscratch.com/post/2013/12/18/LibGDX-Tutorial-9-Scene2D-Part-3-UI-Skins.aspx

    private SpriteBatch batch;
    private Texture img;

    private Stage _stage;
    private Main main;

    public  MainMenuScreen(Main main)
    {
        this.main = main;

        _stage = new Stage();
        Skin uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

        Dialog dialog = new Dialog("test", uiSkin);

        _stage.addActor(dialog);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClearColor(1, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        batch.begin();
//        batch.draw(img, 0, 0);
//        batch.end();

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
