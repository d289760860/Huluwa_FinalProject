package nju.java.file;

import nju.java.FightField;
import nju.java.Position;
import nju.java.creature.Hero;
import nju.java.creature.Villain;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Load
{
    private boolean finished = false;
    private File file = null;
    private FileReader fileReader = null;
    BufferedReader bufferedReader=null;
    private FightField ff;
    //private FileReader fileReader = null;

    public Load(FightField ff,String filename)
    {
        this.ff = ff;
        try {
            initLoadFile(filename);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private synchronized void initLoadFile(String filename) throws FileNotFoundException {
        file = new File(filename);
        fileReader = new FileReader(file);
        bufferedReader = new BufferedReader(fileReader);
    }

    public synchronized boolean LoadAScene() throws IOException
    {
        if(finished ==true)
            return false;
        String str = null;
        ArrayList<Hero> heroes = this.ff.getHeroes();
        ArrayList<Villain> villains = this.ff.getVillains();
        int x;
        int y;
        try {
            for (Hero h : heroes) {
                str = bufferedReader.readLine();
                if (str == null) {
                    bufferedReader.close();
                    fileReader.close();
                    finished = true;
                    return false;
                }
                String[] sourceStrArray = str.split(" ");
                String picname = sourceStrArray[0];
                ImageIcon iia = new ImageIcon("./src/main/resources/"+picname+".png");
                h.setImage(iia.getImage());
                x = Integer.parseInt(sourceStrArray[1]);
                y = Integer.parseInt(sourceStrArray[2]);
                h.setPosition(new Position(x,y));
            }
            for (Villain v : villains) {
                str = bufferedReader.readLine();
                if (str == null) {
                    bufferedReader.close();
                    fileReader.close();
                    finished = true;
                    return false;
                }
                String[] sourceStrArray = str.split(" ");
                String picname = sourceStrArray[0];
                ImageIcon iia = new ImageIcon("./src/main/resources/"+picname+".png");
                v.setImage(iia.getImage());
                x = Integer.parseInt(sourceStrArray[1]);
                y = Integer.parseInt(sourceStrArray[2]);
                v.setPosition(new Position(x,y));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    public boolean isValid()
    {
        return (this.file !=null && this.fileReader!=null && this.bufferedReader!=null);
    }

}
