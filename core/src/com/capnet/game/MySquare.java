package com.capnet.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.capnet.server.HostService;
import com.capnet.share.networking.PacketManager;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * created by willcorrin on 4/27/16
 */
public class MySquare extends Actor
{
    private ShapeRenderer shapeRenderer;
    static private boolean projectionMatrixSet;
    public Vector2 position = new Vector2();
    public float width = 50;
    public float height = 50;
    public Color color = Color.GREEN;

    public MySquare(float x, float y, float w, float h, Color c)
    {
        this.position = new Vector2(x,y);
        this.color = c;
        this.width = w;
        this.height = h;
        shapeRenderer = new ShapeRenderer();
        projectionMatrixSet = false;
    }

    @Override
    public void draw(Batch batch, float alpha)
    {
        batch.begin();
        if(!projectionMatrixSet)
        {
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        }
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(position.x, position.y, width, height);
        shapeRenderer.end();
        batch.end();

    }
}