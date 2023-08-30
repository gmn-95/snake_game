package com.gmn.view;


import java.awt.*;
import java.util.*;

public class Snake {

    private int x = 10;
    private int y = 10;
    private int width = 8;
    private int height = 8;
    private LinkedList<Point> corpo = new LinkedList<>();

    public Snake() {
        initCorpo();
    }

    public void initCorpo(){
        corpo.add(new Point(x, y));
        corpo.add(new Point(x * 2, y));
        corpo.add(new Point(x * 4, y));
    }

    public void draw(Graphics g){

        g.setColor(Color.WHITE);
        int i = 0;
        for (Point p: corpo){
            if(i == 0){
               g.setColor(Color.BLUE);
               i++;
            }
            else {
                g.setColor(Color.GREEN);
            }
            g.fillRect(p.x, p.y, width, height);
        }
    }

    public LinkedList<Point> getCorpo() {
        return corpo;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
