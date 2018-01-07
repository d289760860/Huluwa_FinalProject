package nju.java.file;

import nju.java.FightField;
import nju.java.creature.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Save
{
    private File file = null;
    //private FileWriter fileWriter = null;
    private FightField ff;

    public Save(FightField ff)
    {
        this.ff = ff;
        initSaveFile();
    }

    private synchronized void initSaveFile()
    {
        long time = System.currentTimeMillis();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
        java.util.Date date = new Date(time);
        String dateStr = sdf.format(date);

        File dir = new File("./archive");
        if( ! dir.exists() ){
            dir.mkdir();
        }
        String str = "./archive/"+ dateStr + ".archive";
        file = new File(str);
    }

    public synchronized void writeFile()
    {
        ArrayList<Hero> heroes = this.ff.getHeroes();
        ArrayList<Villain> villains = this.ff.getVillains();
        if(file==null)
            return;
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedwriter = new BufferedWriter(fileWriter);
            for (Hero h : heroes)
            {
                String herostr = h.getPicname() + " " + h.getPosition().getX() + " " + h.getPosition().getY();
                bufferedwriter.write(herostr);
                bufferedwriter.newLine();
                bufferedwriter.flush();
            }
            for (Villain v : villains)
            {
                String villainstr = v.getPicname() + " " + v.getPosition().getX() + " " + v.getPosition().getY();
                bufferedwriter.write(villainstr);
                bufferedwriter.newLine();
                bufferedwriter.flush();
            }
            bufferedwriter.close();
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
