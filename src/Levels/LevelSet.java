package Levels;

import Item.ItemJSFML;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.RectangleShape;

import java.io.File;
import java.util.Scanner;

public class LevelSet
{
    private Scanner s;
    private Level levels[];
    int currentLevel = 0;
    int charcterXPoint = 5;
    int maxLevels = 0;
    int numberOfItems;
    int startingPoint = 0;
    ItemJSFML items[];
    int currentItem = 0;

    public LevelSet (int numberOfStages, File filePlat, File fileItem, Image im[])
    {
        levels = new Level[numberOfStages];
        maxLevels = numberOfStages - 1;
        try {
            s = new Scanner(filePlat);
            for (int i = 0; i < numberOfStages; i++)
            {
                levels[i] = new Level(s, im);
            }
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

    public Platform[] getLevelPlatforms()
    {
        return levels[currentLevel].getPlatforms();
    }

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
    public boolean changeLevel(int spritecordx)
    {
        boolean lvlchange = false;

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

    public int getCurrentLevel()
    {
        return currentLevel;
    }

    public int getCharcterXPoint()
    {
        return charcterXPoint;
    }

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

    public RectangleShape getCurrentItem()
    {
        return items[currentItem].getItem();
    }

    public int getYOfCurrentItem()
    {
        return items[currentItem].getY();
    }

    public int getXOfCurrentItem()
    {
        return items[currentItem].getX();
    }

    public int getItemType()
    {
        return items[currentItem].getType();
    }

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
