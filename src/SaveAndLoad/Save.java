package SaveAndLoad;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Save {

    int stage = 0;
    int level = 0;
    int playerX = 50;
    int playerY = 50;
    File saveFile = new File("src/SaveAndLoad/resources/saveFile.txt");

    public Save(int s, int lvl, int pX, int pY)
    {
        stage = s;
        level = lvl;
        playerX = pX;
        playerY = pY;
        save();
    }

    public void save()
    {
        try {
            Writer w = new FileWriter(saveFile);
            w.write(stage + " " + level + " " + playerX + " " + playerY);
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
