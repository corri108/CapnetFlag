package com.capnet.share;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.capnet.share.packets.ByteHelper;
import com.capnet.share.packets.IPacket;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by michaelpollind on 4/25/16.
 */
public class Map implements IPacket{
    public static final int NUM_SQUARES = 10;
    public static final  int MIN_WIDTH = 20;
    public static final  int MIN_HEIGHT = 20;
    public static final  int MAX_WIDTH = 200;
    public static final  int MAX_HEIGHT = 200;
    protected float shaderThresh = 0.35f;
    protected Color baseColor = Color.GREEN;
    protected ArrayList<MySquare> squares;
    protected Random myRand;


    //makes a random level with a random color out of the 6 prim/secondary colors
    public Map()
    {
        squares = new ArrayList(NUM_SQUARES + 1);
        myRand = new Random();
    }

    public void GenerateMap()
    {

        this.baseColor = getRandColor();
        randomize();

    }

    private Color getRandColor()
    {
//        int r = myRand.nextInt(6);
//
//        if(r == 0)
//        {
//            return Color.RED;
//        }
//        else if(r == 1)
//        {
//            return Color.GREEN;
//        }
//        else if(r == 2)
//        {
//            return Color.BLUE;
//        }
//        else if(r == 3)
//        {
//            shaderThresh *= .5f;
//            return Color.YELLOW;
//        }
//        else if(r == 4)
//        {
//            shaderThresh *= .5f;
//            return Color.ORANGE;
//        }
//        else if(r == 5)
//        {
//            shaderThresh *= .5f;
//            return Color.PURPLE;
//        }

        return Color.ORANGE;
    }

    private void randomize()
    {
        //first the background color square
        //MySquare bg = new MySquare(-20, -20, 800, 600, Color.BLACK);
        //squares.add(bg);

        for(int i = 0; i < NUM_SQUARES; ++i)
        {
            //make rand position and bounds
            int rX = myRand.nextInt(621) - 20;
            int rY = myRand.nextInt(501) - 20;
            int size = myRand.nextInt(MAX_WIDTH - MIN_WIDTH) + MIN_WIDTH;
            boolean darker = true;// myRand.nextBoolean();

            //set random color newC for use
            Color color = new Color();

            //create square and add to arraylist
            MySquare s = new MySquare(rX, rY, size, myRand.nextInt(360), myRand.nextInt(3));
            squares.add(s);
        }

    }

    public void draw(Batch batch, float alpha)
    {
        //System.out.println("SQUARES: " + squares.size());
        //draw every square in the arraylist
        for(int i =0; i < squares.size(); ++i)
        {
            squares.get(i).draw(batch, alpha);
        }
    }


    @Override
    public ByteBuffer Encode() {
        int size = 0;
        for (int x = 0; x < squares.size(); x++)
            size += squares.get(x).size();

        ByteBuffer buffer = ByteBuffer.allocate(ByteHelper.INT+size);
        buffer.putInt(squares.size());
        for (int x = 0; x < squares.size(); x++)
            squares.get(x).Encode(buffer);

        return buffer;
    }

    @Override
    public void Decode(ByteBuffer data) {
        squares.clear();

        int size = data.getInt();
        for(int x = 0; x < size; x++)
        {
            MySquare square = new MySquare();
            square.Decode(data);
            squares.add(square);
        }

    }
}
