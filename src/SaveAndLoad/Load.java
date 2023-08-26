package SaveAndLoad;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Load
{
    private int stage = 0;
    private int level = 0;
    private int playerX = 0;
    private int playerY = 0;
    private File loadFile = new File("src/SaveAndLoad/resources/saveFile.txt");

    public Load ()
    {
        loadSavedLevel();
    }

    public void loadSavedLevel()
    {
        Scanner s;
        try{
            s = new Scanner(loadFile);
            stage = s.nextInt();
            level = s.nextInt();
            playerX = s.nextInt();
            playerY = s.nextInt();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getStage()
    {
        return stage;
    }

    public int getLevel()
    {
        return level;
    }

    public  int getPlayerX()
    {
        return playerX;
    }

    public int getPlayerY()
    {
        return playerY;
    }
}
