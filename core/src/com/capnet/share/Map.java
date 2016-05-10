package com.capnet.share;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.capnet.share.Entities.Player;
import com.capnet.share.packets.ByteHelper;
import com.capnet.share.packets.GameState;
import com.capnet.share.packets.IPacket;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by michaelpollind on 4/25/16.
 */
public class Map implements IPacket{
    public static final int NUM_SQUARES = 300;
    public static final  int MIN_SQUARE_SIZE = 80;
    public static final  int MAX_SQUARE_SIZE = 100;

    public static final  int MAP_WIDTH =3000;
    public static final  int MAP_HEIGHT = 500;

    public static final int FINISH_SIZE = 50;

    protected ArrayList<MySquare> squares;
    private Rectangle ending;
    private Rectangle starting;

    protected Random myRand;
    private  int gameState = 0;

    public  void  SetGamestate(int state)
    {
        this.gameState = state;
    }

    public int GetGamestate()
    {
        return this.gameState;
    }

    //makes a random level with a random color out of the 6 prim/secondary colors
    public Map()
    {
        squares = new ArrayList(NUM_SQUARES );
        myRand = new Random();
        ending = new Rectangle(MAP_WIDTH,MAP_HEIGHT/2.0f - 15f/2.0f,15,15);
        starting = new Rectangle(0,0,200,MAP_HEIGHT);
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

        for(int i = 0; i < NUM_SQUARES-1; ++i)
        {
            //make rand position and bounds
            int rX = myRand.nextInt(MAP_WIDTH);
            int rY = myRand.nextInt(MAP_HEIGHT);
            int size = myRand.nextInt( MAX_SQUARE_SIZE - MIN_SQUARE_SIZE) + MAX_SQUARE_SIZE;

            //create square and add to arraylist
            MySquare s = new MySquare(rX, rY, size, myRand.nextInt(360), myRand.nextInt(3)+1);
            squares.add(s);
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

    public  void  UpdatePlayer(Player player,float delta)
    {

        if(player.isInPlay) {
            if (gameState == GameState.WAITING) {
                if (player.Location.x > starting.x + starting.width) {
                    player.Location.x = starting.x + starting.width;
                }

                if (player.Location.y > starting.y + starting.height) {
                    player.Location.y = starting.y + starting.height;
                }
                if (player.Location.x < 0) {
                    player.Location.x = 0;
                }
                if (player.Location.y < 0) {
                    player.Location.y = 0;
                }

            }

            float multiplier = 1;
            int speed = this.GetSpeed(player.Location);
            if (starting.contains(player.Location.x, player.Location.y)) {
                multiplier = MySquare.FAST;
            } else {
                switch (speed) {
                    case MySquare.SLOW:
                        multiplier = .9f;
                        break;
                    case MySquare.MEDIUM:
                        multiplier = 1.2f;
                        break;
                    case MySquare.FAST:
                        multiplier = 3f;
                        break;
                    case MySquare.SUPER_SLOW:
                        multiplier = .3f;
                        break;
                }
            }

            Vector2 velocity = new Vector2(player.Velocity.x * delta * multiplier, player.Velocity.y * delta * multiplier);
            player.Location = player.Location.add(velocity);
        }
    }

    public  boolean HitEnd(Player p)
    {
        return ending.contains(p.Location.x,p.Location.y);
    }

    public void draw(ShapeRenderer shape)
    {
        //System.out.println("SQUARES: " + squares.size());
        //draw every square in the arraylist
        for(int i =0; i < squares.size(); ++i)
        {
            squares.get(i).draw(shape);
        }

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.identity();
        shape.setColor(Color.GREEN);
        shape.rect(ending.x, ending.y, ending.width / 2.0f, ending.height / 2.0f, ending.width, ending.height, 1, 1, 0.0f);
        shape.end();

        if(gameState == GameState.WAITING) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.identity();
            shape.setColor(Color.WHITE);
            shape.rect(starting.x, starting.y, starting.width, starting.height);
            shape.end();
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
