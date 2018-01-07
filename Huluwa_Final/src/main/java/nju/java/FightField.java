package nju.java;
import nju.java.creature.*;
import nju.java.file.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class FightField extends JPanel {
    private static GameState gs = GameState.NOTRUNNING;
    private int length;
    private Huluwa[] huluwas;
    private Minion[] minions;
    private Grandpa grandpa;
    private SnakeWoman snakewoman;
    private Scorpion scorpion;

    private ArrayList <Hero>heroes = new ArrayList<Hero>();
    private ArrayList <Villain>villains = new ArrayList<Villain>();
    private ArrayList<Thread> threadPool = new ArrayList<Thread>();

    private ActionListener timerTask ;
    private Timer timer;
    private Save savefile=null;
    private Load loadfile = null;
    private Playground pg;

    public static GameState getGs() {
        return gs;
    }
    public static void setGs(GameState ngs){gs = ngs;}

    public FightField(Playground pg)
    {
        this.pg = pg;
        addKeyListener(new FightField.TAdapter(this));
        setFocusable(true);
        initCreatures();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        buildWorld(g);
    }

    class TAdapter extends KeyAdapter {
        private FightField ff;
        public TAdapter(FightField ff)
        {
            this.ff = ff;
        }
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE && FightField.getGs()!=GameState.FINISHED) {
                FightField.setGs(GameState.GOINGON);
                System.out.println("Now start fighting...");
                initThreads();
                initTimer(Constants.TIME_CLOCK);
                savefile = new Save(this.ff);
            }
            else if(key==KeyEvent.VK_L)
            {
                FightField.setGs(GameState.REPLAY);
                System.out.print("Now start replaying,please choose a file.");
                try {
                    FileDialog fdopen = new FileDialog(pg, "打开", FileDialog.LOAD);
                    fdopen.setVisible(true);
                    String filename = fdopen.getDirectory()+fdopen.getFile();
                    this.ff.loadfile  = new Load(this.ff,filename);
                } catch (Exception exc)
                {
                    exc.printStackTrace();
                }
                initTimer(Constants.TIME_CLOCK);
            }
            repaint();
        }
    }


    public void initThreads()
    {
        Thread temp;
        for (Villain villain : villains) {
            temp = new Thread(villain);
            threadPool.add(temp);
            villain.setT(temp);
        }
        for (Hero hero : heroes) {
            temp = new Thread(hero);
            threadPool.add(temp);
            hero.setT(temp);
        }
        for (Thread t :this.threadPool)
            t.start();

    }

    public void initTimer(int time){
        timerTask = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                everyTimeInterval();
                repaint();
            }
        };
        timer = new Timer(time,timerTask);
        timer.start();
    }

    public final void initCreatures()
    {
        this.grandpa = new Grandpa();
        this.grandpa.setPosition(new Position(3,4));
        this.grandpa.setFf(this);
        this.heroes.add(this.grandpa);

        this.huluwas = new Huluwa[7];
        this.huluwas[0] = new Huluwa("red",1);
        this.huluwas[0].setPosition(new Position(5,5));
        this.huluwas[1] = new Huluwa("orange",2);
        this.huluwas[1].setPosition(new Position(5,6));
        this.huluwas[2] = new Huluwa("yellow",3);
        this.huluwas[2].setPosition(new Position(5,7));
        this.huluwas[3] = new Huluwa("green",4);
        this.huluwas[3].setPosition(new Position(5,8));
        this.huluwas[4] = new Huluwa("cyan",5);
        this.huluwas[4].setPosition(new Position(5,9));
        this.huluwas[5] = new Huluwa("blue",6);
        this.huluwas[5].setPosition(new Position(5,10));
        this.huluwas[6] = new Huluwa("purple",7);
        this.huluwas[6].setPosition(new Position(5,11));
        for(int i = 0;i<7;i++)
        {
            this.heroes.add(this.huluwas[i]);
            this.huluwas[i].setFf(this);
        }

        this.minions = new Minion[6];
        for(int i = 0;i<6;i++)
        {
            this.minions[i] = new Minion();
            if(i<3)
                this.minions[i].setPosition(new Position(12-i,i+5));
            else
                this.minions[i].setPosition(new Position(7+i,i+6));
            villains.add(minions[i]);
            this.minions[i].setFf(this);
        }

        this.scorpion = new Scorpion();
        this.scorpion.setPosition(new Position(9,8));
        villains.add(this.scorpion);
        this.scorpion.setFf(this);

        this.snakewoman = new SnakeWoman();
        this.snakewoman.setPosition(new Position(11,0));
        villains.add(this.snakewoman);
        this.snakewoman.setFf(this);

        //System.out.print(this.heroes);
    }

    private void DrawCreature(Creature c,Graphics g)
    {
        g.drawImage(c.getImage(),Constants.CELLLEN*c.getPosition().getX(), Constants.CELLLEN*c.getPosition().getY(), Constants.CELLLEN,Constants.CELLLEN, this);
    }

    public synchronized ArrayList<Hero> getHeroes() {return this.heroes;}
    public synchronized ArrayList<Villain> getVillains() {return this.villains;}

    public void buildWorld(Graphics g) {

        g.setColor(new Color(201, 250, 128));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        for(int i = 0;i<7;i++)
        {
            Huluwa p = huluwas[i];
            DrawCreature(p,g);
        }
        for(int i = 0;i<6;i++)
        {
            Minion m = minions[i];
            DrawCreature(m,g);
        }
        DrawCreature(this.scorpion,g);
        DrawCreature(this.grandpa,g);
        DrawCreature(this.snakewoman,g);

    }

    public synchronized boolean CreatureAttack(Creature from,Creature to)
    {
        Random random = new Random();
        int prob = random.nextInt()%100;
        if(prob<=from.getPower())
        {
            to.die();
            return true;
        }
        return false;
    }

    public synchronized boolean CreatureWalk(Creature c,int dx,int dy)
    {
        Position to = new Position(c.getPosition().getX()+dx,c.getPosition().getY()+dy);
        if(!to.valid())
            return false;
        boolean succeed = true;
        for(Hero h :heroes)
        {
            if(h.getPosition().equals(to))
            {
                succeed=false;
                break;
            }
        }
        for(Villain v :villains)
        {
            if(v.getPosition().equals(to))
            {
                succeed=false;
                break;
            }
        }
        if(!succeed)
            return false;
        c.setPosition(to);
        return true;
    }

    @SuppressWarnings("deprecation")
    public synchronized void everyTimeInterval(){

        boolean heroesalive = false;
        boolean villainsalive = false;
        if( FightField.getGs() == GameState.GOINGON ) {
            Iterator<Hero> ith = heroes.iterator();
            while (ith.hasNext())
            {
                Hero hero = ith.next();
                if (hero.isAlive()) {
                    heroesalive = true;
                }
            }
            Iterator<Villain> itv = villains.iterator();
            while (itv.hasNext())
            {
                Villain villain = itv.next();
                if (villain.isAlive()) {
                    villainsalive=true;
                }
            }
            if(savefile!=null)
            {
                savefile.writeFile();
                System.out.print("File saved\n");
            }

            if( !heroesalive || !villainsalive ) {
                FightField.setGs(GameState.FINISHED);


                for (Thread t : threadPool)
                    t.suspend();

                return;
            }
        }


        if( FightField.getGs() == GameState.REPLAY ) {

            String str = null;
            boolean succeed=true;
            if (loadfile == null||!loadfile.isValid())
            {
                System.out.print("load file not initialize properly!\n");
                return;
            }
            try {
                succeed = this.loadfile.LoadAScene();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
            if(succeed==false)
            {
                FightField.setGs(GameState.FINISHED);
            }
        }

    }


}
