package Item;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author: Yujie 
 * @author: Viltene
 * The class responsible for item data storage, logic and representation.
 * It is used in the LevelSet class where it's initialised as an item array
*/
public class ItemJSFML
{
    /* Coordinates */
    private int x = 0;
    private int y = 0;

    private int type = 0;
    private int levelOfAppearence;
    private boolean notTaken = true; // if the item is still available
    
    /* Representation */
    private RectangleShape item;
    private Image life;
    private Image stopWatch;
    private Texture lifeText;
    private Texture stopWatchText;

    /**
     * Item constructor
     * @param lvl level the item appears on
     * @param t type
     * @param a x coordinate
     * @param b y coordinate
     */
    public ItemJSFML(int lvl, int t, int a, int b)
    {
        /* Takes values when initiallised */
        x = a;
        y = b;
        type = t;
        levelOfAppearence = lvl;

        /* Creating the represenattion of an item */
        Vector2f v = new Vector2f (30, 30); // size
        item = new RectangleShape (v);
        item.setPosition(a, b);
        life = new Image ();
        stopWatch = new Image();

        /* Loading Images and textures */
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

        setTexture();
    }

    /**
     * Setter of the texture of the item depending on its type
     */
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

    /**
     * Desactivates the item
     */
    public void remove ()
    {
        notTaken = false;
        item.setFillColor(Color.TRANSPARENT);
    }

    /**
     * Getter of the level of appearence
     * @return in which level the item appears
     */
    public int getLevelOfAppearence()
    {
        return levelOfAppearence;
    }

    /**
     * Getter of the represenation of the item
     * @return item
     */
    public RectangleShape getItem()
    {
        return item;
    }

    /**
     * Getter of the colour of the item
     * @return colour
     */
    public Color getColor()
    {
        return item.getFillColor();
    }

   /**
    * Getter of the x coordinate of the item
    * @return x coordinate of the item
    */
    public int getX()
    {
        return x;
    }

    /**
    * Getter of the y coordinate of the item
    * @return y coordinate of the item
    */
    public int getY()
    {
        return y;
    }

    /**
    * Getter of the type of the item
    * @return type of the item (0 - life || 1 - stopwatch)
    */
    public  int getType()
    {
        return type;
    }

    /**
     * Getter of the state of availability of the item
     * @return true = available || false = already taken
     */
    public boolean checkIfAvailable()
    {
        return notTaken;
    }

}
