package com.capnet.share;

import javafx.scene.shape.Rectangle;

/**
 * Created by michaelpollind on 4/21/16.
 */
public class Map {
    private Rectangle[] _rectangle;

    public  Map(int width, int height, int number) {
        _rectangle = new Rectangle[number];
        for (int x = 0; x < number; x++) {
            Rectangle temp = new Rectangle();
            temp.setX(Math.random() * width);
            temp.setY(Math.random() * height);
            temp.setRotate(Math.PI * Math.random());
            _rectangle[x] = temp;
        }

    }

    public Rectangle[] GetRectangles() {
        return  _rectangle;
    }

}
