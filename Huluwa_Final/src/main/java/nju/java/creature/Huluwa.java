package nju.java.creature;

import nju.java.Position;

public class Huluwa extends Hero
{

    private Position pos;
    private int rank;


    public int GetRank() {return rank;}

    public Huluwa(String name,int rank)
    {
        super(name);
        this.rank = rank;
        this.power = rank*10;
    }
}
