package com.capnet.share;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.capnet.share.packets.ByteHelper;
import com.capnet.share.packets.IPacket;

import java.nio.ByteBuffer;

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

    public MySquare()
    {
        projectionMatrixSet = false;
    }

    public MySquare(float x, float y, float w, float h, Color c)
    {
        this.position = new Vector2(x,y);
        this.color = c;
        this.width = w;
        this.height = h;
        projectionMatrixSet = false;
    }

    @Override
    public void draw(Batch batch, float alpha)
    {
        if(shapeRenderer == null)
            shapeRenderer = new ShapeRenderer();

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

    public  int size()
    {
        return ByteHelper.VECTOR2 + ByteHelper.FLOAT + ByteHelper.FLOAT + ByteHelper.COLOR;
    }

    public void Encode(ByteBuffer buffer) {
        ByteHelper.EncodeVector2(buffer,position);
        buffer.putFloat(width);
        buffer.putFloat(height);
        ByteHelper.EncodeColor(buffer,color);
    }

    public void Decode(ByteBuffer data) {
        this.position = ByteHelper.DecodeVector2(data);
        this.width = data.getFloat();
        this.height = data.getFloat();
        this.color = ByteHelper.DecodeColor(data);

    }
}