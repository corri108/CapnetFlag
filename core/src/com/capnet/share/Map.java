package com.capnet.share;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.capnet.share.packets.ByteHelper;
import com.capnet.share.packets.IPacket;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by michaelpollind on 4/25/16.
 */
public class Map implements IPacket{
    public static final int NUM_SQUARES = 50;
    public static final  int MIN_SQUARE_SIZE = 80;
    public static final  int MAX_SQUARE_SIZE = 300;

    public static final  int MAP_WIDTH = 1000;
    public static final  int MAP_HEIGHT = 500;

    public static final int FINISH_SIZE = 50;

    public static final int FINISH_X = 475;
    public static final int FINISH_Y = 500;




    public static final int

    protected ArrayList<MySquare> squares;
    protected Random myRand;


    //makes a random level with a random color out of the 6 prim/secondary colors
    public Map()
    {
        squares = new ArrayList(NUM_SQUARES );
        myRand = new Random();
    }

    public void GenerateMap()
    {

        randomize();

    }


    private void randomize()
    {
        //first the background color square
        //MySquare bg = new MySquare(-20, -20, 800, 600, Color.BLACK);
        //squares.add(bg);

        for(int i = 0; i < NUM_SQUARES; ++i)
        {
            //make rand position and bounds
            int rX = myRand.nextInt(MAP_WIDTH);
            int rY = myRand.nextInt(MAP_HEIGHT);
            int size = myRand.nextInt( MAX_SQUARE_SIZE - MIN_SQUARE_SIZE) + MAX_SQUARE_SIZE;
            boolean darker = true;// myRand.nextBoolean();

            //set random color newC for use
            Color color = new Color();

            //create square and add to arraylist
            MySquare s = new MySquare(rX, rY, size, myRand.nextInt(360), myRand.nextInt(3)+1);
            MySquare end = new MySquare(FINISH_X,FINISH_Y,FINISH_SIZE,4);
            squares.add(s);
            squares.add(end);
        }

    }

    public  int GetSpeed(Vector2 point)
    {
        for(int i = squares.size()-1;  i >= 0; --i) {
            if(squares.get(i).PointIntersects(point))
            {
                return squares.get(i).GetSpeed();
            }

        }
        return 0;
    }

    public void draw(ShapeRenderer shape)
    {
        //System.out.println("SQUARES: " + squares.size());
        //draw every square in the arraylist
        for(int i =0; i < squares.size(); ++i)
        {
            squares.get(i).draw(shape);
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
