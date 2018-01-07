package nju.java.creature;

import nju.java.FightField;
import nju.java.Position;

import javax.swing.*;
import java.awt.*;

public abstract class Creature implements Runnable
{
    FightField ff;
    protected Image image;
    protected Position pos;
    protected boolean alive = true;
    protected Thread t;
    protected int power;
    protected String picname;
    protected String name;

    Creature(String name)
    {
        this.name = name;
        this.picname = name;
        ImageIcon iia = new ImageIcon("./src/main/resources/"+name+".png");
        this.setImage(iia.getImage());
        this.pos = null;
        this.t = null;
    }

    public abstract void run();

    public final Image getImage() {
        return this.image;
    }

    public final void setImage(Image img) {
        image = img;
    }

    public final boolean isAlive() {return alive;}

    public final void die()
    {
        alive = false;
        this.picname = "ghost";
        ImageIcon iia = new ImageIcon("./src/main/resources/ghost.png");
        this.setImage(iia.getImage());
    }

    public final void setPosition(Position pos)
    {
        this.pos = pos;
    }

    public final Position getPosition()
    {
        return this.pos;
    }

    public final Thread getT(){return t;}

    public final void setT(Thread t){this.t = t;}

    public final void setFf(FightField ff) {this.ff = ff;}

    public final int getPower(){return this.power;}

    @Override
    public String toString()
    {
        return this.name;
    }

    public final String getPicname()
    {
        return this.picname;
    }

}