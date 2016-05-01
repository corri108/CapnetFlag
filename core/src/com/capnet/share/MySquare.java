package com.capnet.share;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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

    //private  Vector2 asdf = Vector2.Zero;

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


        //debug CODE
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(1, 1, 0, 1);
//        shapeRenderer.circle(asdf.x, asdf.y, 5);
//        shapeRenderer.end();
//
//        Rectangle rectangle = new Rectangle(position.x,position.y,size,size);
//
//        shapeRenderer.begin(ShapeType.Line);
//        shapeRenderer.identity();
//        shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height,size,size,1,1,0);
//        shapeRenderer.end();


    }

    public  int size()
    {
        return ByteHelper.VECTOR2 + ByteHelper.FLOAT + ByteHelper.FLOAT  + ByteHelper.INT;
    }

    public boolean PointIntersects(Vector2 point)
    {
        Rectangle rectangle = new Rectangle(position.x ,position.y,size,size);
        Vector2 center = new Vector2((position.x+(size/2.0f)),(position.y+(size/2.0f)));

        Vector2 difference = new Vector2(point.x - center.x,point.y - center.y);
        float distance = (float) Math.sqrt(difference.x * difference.x + difference.y * difference.y);
        float point_rotate = (float)Math.atan2(difference.y,difference.x);

        Vector2 hit = new Vector2();
        hit.x = (float) ((Math.cos(point_rotate - (rotation * (3.14f/180f))) * distance) + center.x);
        hit.y = (float) ((Math.sin(point_rotate - (rotation * (3.14f/180f))) * distance) + center.y);

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