package com.capnet.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.capnet.share.MySquare;
import com.badlogic.gdx.graphics.g2d.Batch;
import java.util.Random;

/**
 * created by willcorrin on 4/27/16
 */

public class MySquareGenerator
{
    public int numSquares = 10;
    public int minWidth = 20;
    public int minHeight = 20;
    public int maxWidth = 200;
    public int maxHeight = 200;
    public float shadeThresh = 0.35f;
    public Color baseColor = Color.GREEN;
    public ArrayList<MySquare> squares;
    private Random myRand;

    //makes a random level with a color specified
    public MySquareGenerator(int numSquares, int minWidth, int minHeight, int maxWidth, int maxHeight,
                             float shadeThresh, Color base)
    {
        myRand = new Random();
        this.shadeThresh = shadeThresh;
        this.baseColor = base;
        this.minHeight = minHeight;
        this.minWidth = minWidth;
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.numSquares = numSquares;
        squares = new ArrayList(numSquares + 1);
        randomize();
    }

    //makes a random level with a random color out of the 6 prim/secondary colors
    public MySquareGenerator(int numSquares, int minWidth, int minHeight, int maxWidth, int maxHeight,
                             float shadeThresh)
    {
        myRand = new Random();
        this.shadeThresh = shadeThresh;
        this.minHeight = minHeight;
        this.minWidth = minWidth;
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.numSquares = numSquares;
        squares = new ArrayList(numSquares + 1);
        this.baseColor = getRandColor();
        randomize();
    }

    private Color getRandColor()
    {
        int r = myRand.nextInt(6);

        if(r == 0)
        {
            return Color.RED;
        }
        else if(r == 1)
        {
            return Color.GREEN;
        }
        else if(r == 2)
        {
            return Color.BLUE;
        }
        else if(r == 3)
        {
            shadeThresh *= .5f;
            return Color.YELLOW;
        }
        else if(r == 4)
        {
            shadeThresh *= .5f;
            return Color.ORANGE;
        }
        else if(r == 5)
        {
            shadeThresh *= .5f;
            return Color.PURPLE;
        }

        return Color.WHITE;
    }

    private void randomize()
    {
        //first the background color square
        MySquare bg = new MySquare(-20, -20, 800, 600, baseColor);
        squares.add(bg);

        for(int i = 0; i < numSquares; ++i)
        {
            //make rand position and bounds
            int rX = myRand.nextInt(621) - 20;
            int rY = myRand.nextInt(501) - 20;
            int rW = myRand.nextInt(maxWidth - minWidth) + minWidth;
            int rH = myRand.nextInt(maxHeight - minHeight) + minHeight;
            boolean darker = true;// myRand.nextBoolean();

            //set random color newC for use
            Color newC = new Color(baseColor);

            if(darker)
            {
                //make a darker shade of color
                if(baseColor == Color.GREEN)
                    newC.g -= myRand.nextFloat() * shadeThresh;
                else if(baseColor == Color.RED)
                    newC.r -= myRand.nextFloat() * shadeThresh;
                else if(baseColor == Color.BLUE)
                    newC.b -= myRand.nextFloat() * shadeThresh;

                else if(baseColor == Color.YELLOW) {
                    newC.r -= myRand.nextFloat() * shadeThresh;
                    newC.g -= myRand.nextFloat() * shadeThresh;
                }
                else if(baseColor == Color.PURPLE) {
                    newC.r -= myRand.nextFloat() * shadeThresh;
                    newC.b -= myRand.nextFloat() * shadeThresh;
                }
                else if(baseColor == Color.ORANGE) {
                    newC.r -= myRand.nextFloat() * shadeThresh;
                    newC.g -= myRand.nextFloat() * shadeThresh;
                }
            }
            else
            {
                //make a lighter shade of color
                float rO = myRand.nextFloat() * shadeThresh;
                if(baseColor == Color.GREEN) {
                    newC.r += rO;
                    newC.b += rO;
                }
                else if(baseColor == Color.RED) {
                    newC.g += rO;
                    newC.b += rO;
                }
                else if(baseColor == Color.BLUE) {
                    newC.r += rO;
                    newC.g += rO;
                }
                else if(baseColor == Color.YELLOW)
                    newC.b -= myRand.nextFloat() * shadeThresh;
                else if(baseColor == Color.PURPLE)
                    newC.g -= myRand.nextFloat() * shadeThresh;
                else if(baseColor == Color.ORANGE)
                    newC.b -= myRand.nextFloat() * shadeThresh;
            }

            //create square and add to arraylist
            MySquare s = new MySquare(rX, rY, rW, rH, newC);
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
}