package nju.java;

import nju.java.creature.*;

import javax.swing.*;

public class Playground extends JFrame
{
    public Playground() {
        InitUI();
    }

    public void InitUI() {
        FightField ff = new FightField(this);
        add(ff);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Huluwa vs Demon");
    }

}
