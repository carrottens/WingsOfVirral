package Item;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

public class ItemJSFML
{
    int x = 0;
    int y = 0;
    int type = 0;
    int levelOfAppearence;
    boolean notTaken = true;
    private RectangleShape item;
    private Image life;
    private Image stopWatch;
    private Texture lifeText;
    private Texture stopWatchText;

    public ItemJSFML(int lvl, int t, int a, int b)
    {
        Vector2f v = new Vector2f (30, 30);
        item = new RectangleShape (v);
        item.setPosition(a, b);
        life = new Image ();
        stopWatch = new Image();
        try {
            life.loadFromFile(Paths.get("ArtAssets/life.png"));
            stopWatch.loadFromFile(Paths.get("ArtAssets/stopwatch.png"));
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        stopWatchText = new Texture();
        lifeText = new Texture();
        try{
            stopWatchText.loadFromImage(stopWatch);
            lifeText.loadFromImage(life);
        }catch (TextureCreationException tce)
        {
            tce.printStackTrace();
        }
        x = a;
        y = b;
        type = t;
        levelOfAppearence = lvl;
        setTexture();
    }

    private void setTexture()
    {
        if(type == 0)
        {
            item.setTexture(lifeText);
        }
        if(type == 1)
        {
            item.setTexture(stopWatchText);
        }
    }

    public void remove ()
    {
        notTaken = false;
        item.setFillColor(Color.TRANSPARENT);
    }

    public int getLevelOfAppearence()
    {
        return levelOfAppearence;
    }

    public RectangleShape getItem()
    {
        return item;
    }

    public Color getColor()
    {
        return item.getFillColor();
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public  int getType()
    {
        return type;
    }

    public boolean checkIfAvailable()
    {
        return notTaken;
    }

}
