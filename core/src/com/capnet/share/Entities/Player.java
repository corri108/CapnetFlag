package com.capnet.share.Entities;

import com.badlogic.gdx.graphics.Color;
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
public class Player{
    public Vector2 Location= new Vector2();
    public Vector2 Velocity = new Vector2();


    // rendering will not work with this setup. packets have to be separated from the client
    private ShapeRenderer shape = null;

    private int id = 10; //used for the server to communicate with players
    private String name = "hello this is  a test";

    public Player(int id, String name)
    {
        this.id = id;
        this.name = name;

    }

    public Player()
    {

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



    public void Draw()
    {
        if(shape  == null)
            shape =new ShapeRenderer();

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.PINK);
        shape.circle(Location.x,Location.y,3);
        shape.end();

    }



}
