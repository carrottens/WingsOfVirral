package Levels;

import Item.ItemJSFML;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.RectangleShape;

import java.io.File;
import java.util.Scanner;

/**
 * @author: Viltene
 * It is called by GUI which passes down the number of levels, files for items and platforms, as well as textures
 * 
 * This part of the Level package is concerned with putting together multiple levels and items to make a levelset.
 * It does it by defining multiple levels.
 */
public class LevelSet
{
    // initialisation
    private Scanner s;
    private Level levels[];
    int currentLevel = 0;
    int charcterXPoint = 5;
    int maxLevels = 0;
    int numberOfItems;
    int startingPoint = 0;
    ItemJSFML items[];
    int currentItem = 0;

    /**
     * Constructor for LevelSet
     * @param numberOfStages number of levels. Comes from GUI
     * @param filePlat Platform file
     * @param fileItem Item File
     * @param im array of 8 of images that represent the different type of platforms 
     */
    public LevelSet (int numberOfStages, File filePlat, File fileItem, Image im[])
    {
        levels = new Level[numberOfStages];
        maxLevels = numberOfStages - 1;
        try {
            // initialises the Scanner for Level class
            s = new Scanner(filePlat);
            for (int i = 0; i < numberOfStages; i++)
            {
                levels[i] = new Level(s, im);
            }

            // Reads the item file and creates appropriate items
            s = new Scanner(fileItem);
            int lvlItem;
            int typeItem;
            int xItem;
            int yItem;
            numberOfItems = s.nextInt();
            items = new ItemJSFML[numberOfItems];
            for(int i = 0; i < numberOfItems; i++)
            {
                lvlItem = s.nextInt();
                typeItem = s.nextInt();
                xItem = s.nextInt();
                yItem = s.nextInt();
                items[i] = new ItemJSFML(lvlItem, typeItem, xItem, yItem);
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Getter for the platforms
     * @return platforms of the current level
     */
    public Platform[] getLevelPlatforms()
    {
        return levels[currentLevel].getPlatforms();
    }

    /**
     * Checks if it's the last level and returns boolean
     * @return true = last level || false = not last level
     */
    public boolean lastLevel()
    {
       if (currentLevel == maxLevels)
       {
           return true;
       }
        else
       {
           return false;
       }
    }

    /**
     * Changes the level
     * @param spritecordx player coordinate x
     * @return true = level change detected || false = no level change
     */
    public boolean changeLevel(int spritecordx)
    {
        boolean lvlchange = false;

        // if the player has crossed the boundaries then level has changed
        if(spritecordx >= 1280 && currentLevel != maxLevels)
        {
            currentLevel++;
            charcterXPoint = 5;
            lvlchange = true;
        }
        if(spritecordx <= 0 && currentLevel != 0)
        {
            currentLevel--;
            charcterXPoint = 1275;
            lvlchange = true;
        }

        return lvlchange;
    }

    /**
     * Gets the current level
     * @return current level
     */
    public int getCurrentLevel()
    {
        return currentLevel;
    }

    /**
     * Gets the player's X position
     * @return x position of the Player
     */
    public int getCharcterXPoint()
    {
        return charcterXPoint;
    }

    /**
     * Checks if there is an item
     * @return true = there is an item in the level || false = no item
     */
    public boolean checkIfThereIsItem()
    {
            for (int i = startingPoint; i < items.length; i++)
            {
                if (items[i].getLevelOfAppearence() == currentLevel && items[i].getColor() != Color.TRANSPARENT)
                {
                    currentItem = i;
                    return true;
                }
            }
            return false;
    }

    /**
     * Getter of the item
     * @return represenattion of the item of the current level
     */
    public RectangleShape getCurrentItem()
    {
        return items[currentItem].getItem();
    }

    /**
     * Get the Y position of the current item
     * @return y coordinate of the current item
     */
    public int getYOfCurrentItem()
    {
        return items[currentItem].getY();
    }

    /**
     * Get the X position of the current item
     * @return x coordinate of the current item
     */
    public int getXOfCurrentItem()
    {
        return items[currentItem].getX();
    }

    /**
     * returns the item type
     * @return 0 - extra life || 1 - stopwatch
     */
    public int getItemType()
    {
        return items[currentItem].getType();
    }

    /**
     * If item is available, removes it
     * @return true = item has been removed || false = no item
     */
    public boolean dealWithItem()
    {
        if(items[currentItem].checkIfAvailable())
        {
            items[currentItem].remove();
            return true;
        }
        return false;
    }
}
