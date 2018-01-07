package nju.java;

import nju.java.creature.Creature;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean equals(Position p)
    {
        return this.x==p.x&&this.y==p.y;
    }
    public boolean equals(int x,int y)
    {
        return this.x==x&&this.y==y;
    }

    public boolean valid()
    {
        return x>=0&& x<Constants.NUMCELLX && y>=0&&y<Constants.NUMCELLY;
    }

    public int calcDistance(Position p)
    {
        return Math.abs(p.x-this.x) + Math.abs(p.y-this.y);
    }
    public int calcDistance(int x,int y)
    {
        return Math.abs(x-this.x) + Math.abs(y-this.y);
    }
}
