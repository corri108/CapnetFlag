package com.capnet.share.Entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.capnet.share.packets.ByteHelper;
import com.capnet.share.packets.IPacket;

import java.nio.ByteBuffer;

/**
 * Created by michaelpollind on 4/21/16.
 */
//player shouldn't be it's own packet?
// will introduce lots of unnecessary GC
public class Player
{
    public Vector2 Location= new Vector2();
    public Vector2 Velocity = new Vector2();
    public Color color = Color.WHITE;
    public int Wins = 0;
    private int id = -1; //used for the server to communicate with players
    private String name = "hello this is  a test";
    public boolean isReady = false;
    public boolean isInPlay = true;

    public Player(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Player()
    {

    }

    public void SetName(String name)
    {
        this.name = name;
    }

    public  void  SetId(int id)
    {
        this.id = id;
    }

    public  int GetPlayerId() {
        return id;
    }

    public String GetName() {
        return  name;
    }



    public void Draw(ShapeRenderer shape)
    {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(color);
        shape.circle(Location.x, Location.y, 5);
        shape.end();
    }
}
