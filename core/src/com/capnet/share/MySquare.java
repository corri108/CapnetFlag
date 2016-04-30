package com.capnet.share;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.capnet.share.packets.ByteHelper;


import java.nio.ByteBuffer;

/**
 * created by willcorrin on 4/27/16
 */
public class MySquare extends Actor
{
    public static final int SLOW = 0;
    public static final int MEDIUM= 1;
    public static final int FAST= 2;

    public static  final  Color SLOW_COLOR = new Color(246/255f,251/255f,150/255f,255/255f);
    public static  final  Color MEDIUM_COLOR = new Color(155/255f,158/255f,104/255f,255/255f);
    public static  final  Color FAST_COLOR = new Color(42/255f,43/255f,31/255f,255/255f);

    private ShapeRenderer shapeRenderer;
    private Vector2 position = new Vector2();
    private float size = 50;
    private int speed;
    private  float rotation = 0;

    public MySquare()
    {

    }

    public MySquare(float x, float y, float size,float rotation, int speed)
    {
        this.position = new Vector2(x,y);
        this.speed = speed;
        this.size = size;
        this.rotation = rotation;

    }

    @Override
    public void draw(Batch batch, float alpha)
    {
        if(shapeRenderer == null)
            shapeRenderer = new ShapeRenderer();

        this.shapeRenderer.begin(ShapeType.Filled);
        switch (speed)
        {
            case SLOW:
                this.shapeRenderer.setColor(SLOW_COLOR);
                break;
            case MEDIUM:
                this.shapeRenderer.setColor(MEDIUM_COLOR);
                break;
            case FAST:
                this.shapeRenderer.setColor(FAST_COLOR);
                break;
        }

        this.shapeRenderer.identity();
        this.shapeRenderer.rect(position.x, position.y, size/2.0f, size/2.0f,size,size,1,1,rotation);
        this.shapeRenderer.end();

    }

    public  int size()
    {
        return ByteHelper.VECTOR2 + ByteHelper.FLOAT + ByteHelper.FLOAT  + ByteHelper.INT;
    }

    public boolean PointIntersects(Vector2 point)
    {
        Rectangle rectangle = new Rectangle(position.x - (size/2.0f),position.y-(size/2.0f),size,size);
        Vector2 difference = new Vector2(point.x - position.x,point.y - position.y);
        float distance = (float) Math.sqrt(difference.x * difference.x + difference.y * difference.y);
        float rotate = (float)Math.atan2(difference.x,difference.y);

        point.x = (float) ((Math.cos(rotate - (rotate * (3.14f/180))) * distance) + rectangle.x);
        point.y = (float) ((Math.cos(rotate - (rotate * (3.14f/180))) * distance) + rectangle.y);

        if(rectangle.contains(point))
        {
            return true;
        }

        return false;
    }

    public void Encode(ByteBuffer buffer) {
        ByteHelper.EncodeVector2(buffer,position);
        buffer.putFloat(size);
        buffer.putFloat(rotation);
        buffer.putInt(speed);
    }

    public void Decode(ByteBuffer data) {
        this.position = ByteHelper.DecodeVector2(data);
        this.size = data.getFloat();
        this.rotation = data.getFloat();
        this.speed = data.getInt();

    }
}