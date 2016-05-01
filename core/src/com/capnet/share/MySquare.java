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
public class MySquare
{
    public static final  int SUPER_SLOW = 0;
    public static final int SLOW = 1;
    public static final int MEDIUM= 2;
    public static final int FAST= 3;

    public static  final  Color FAST_COLOR= new Color(246/255f,251/255f,150/255f,255/255f);
    public static  final  Color MEDIUM_COLOR = new Color(155/255f,158/255f,104/255f,255/255f);
    public static  final  Color SLOW_COLOR = new Color(42/255f,43/255f,31/255f,255/255f);

    private Vector2 position = new Vector2();
    private float size = 50;
    private int speed;
    private  float rotation = 0;

    public MySquare()
    {

    }

    public MySquare(float x, float y, float size,float rotation, int speed)
    {
        if(speed <= 0 || speed >4)
        {
            speed = 1;
        }
        this.position = new Vector2(x,y);
        this.speed = speed;
        this.size = size;
        this.rotation = rotation;

    }

    public void draw(ShapeRenderer shapeRenderer)
    {
       shapeRenderer.begin(ShapeType.Filled);
        switch (speed)
        {
            case SLOW:
                shapeRenderer.setColor(SLOW_COLOR);
                break;
            case MEDIUM:
                shapeRenderer.setColor(MEDIUM_COLOR);
                break;
            case FAST:
                shapeRenderer.setColor(FAST_COLOR);
                break;
        }

        shapeRenderer.identity();
        shapeRenderer.rect(position.x, position.y, size/2.0f, size/2.0f,size,size,1,1,rotation);
        shapeRenderer.end();

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
        float point_rotate = (float)Math.atan2(difference.x,difference.y);

        Vector2 hit = new Vector2();
        hit.x = (float) ((Math.cos(point_rotate - (rotation * (3.14f/180f))) * distance) + rectangle.x);
        hit.y = (float) ((Math.sin(point_rotate - (rotation * (3.14f/180f))) * distance) + rectangle.y);

        if(rectangle.contains(hit))
        {
            return true;
        }

        return false;
    }

    public  int GetSpeed()
    {
        return  speed;
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