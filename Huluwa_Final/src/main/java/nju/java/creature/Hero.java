package nju.java.creature;

import nju.java.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Hero extends Creature
{
    public ArrayList<Villain> getEnemies()
    {
        return this.ff.getVillains();
    }

    Hero(String name)
    {
        super(name);
    }
    @Override
    public void run()
    {
        while (!Thread.interrupted()) {
            if (FightField.getGs() == GameState.GOINGON) {
                try {
                    if (!this.isAlive() || FightField.getGs() != GameState.GOINGON) {

                        Thread.sleep(Constants.TIME_CLOCK);
                        continue;
                    }

                    ArrayList<Villain> vs = getEnemies();
                    Villain found = null;
                    if (!vs.isEmpty()) {
                        for (Villain villain : vs) {
                            if (villain.isAlive() && villain.getPosition().calcDistance(this.pos) < Constants.ATTACK_DIS
                                    && (found == null || found.getPower() < villain.getPower())) {
                                found = villain;
                            }
                        }
                    }
                    if (found != null) {
                        this.ff.CreatureAttack(this, found);
                    } else//walk
                    {
                        int distance = Constants.SCREEN_HEIGHT + Constants.SCREEN_WIDTH;
                        for (Villain villain : vs) {
                            int newdis = villain.getPosition().calcDistance(this.pos);
                            if (newdis < distance) {
                                distance = newdis;
                                found = villain;
                            }
                        }
                        if (found == null) {
                            this.ff.CreatureWalk(this, 1, 0);
                        }
                        else
                        {
                            Random random = new Random();
                            boolean rand = random.nextBoolean();
                            if(rand)
                            {
                                int dis = random.nextInt()%3-1;
                                if(random.nextBoolean())
                                    this.ff.CreatureWalk(this, dis, 0);
                                else
                                    this.ff.CreatureWalk(this,0,dis);
                            }
                            else
                            {
                                int dx = found.getPosition().getX() - this.pos.getX();
                                int dy = found.getPosition().getX() - this.pos.getY();
                                if (Math.abs(dx) > Math.abs(dy)) {
                                    if (dx > 0)
                                        this.ff.CreatureWalk(this, 1, 0);
                                    else
                                        this.ff.CreatureWalk(this, -1, 0);
                                } else {
                                    if (dy > 0)
                                        this.ff.CreatureWalk(this, 0, 1);
                                    else
                                        this.ff.CreatureWalk(this, 0, -1);
                                }
                            }
                        }
                    }
                    Thread.sleep(Constants.TIME_CLOCK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (FightField.getGs() == GameState.REPLAY) {
                try {
                    Thread.sleep(0, Constants.REPLAY_CLOCK);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
