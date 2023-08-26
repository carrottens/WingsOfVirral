package Levels;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;

import java.util.Scanner;

/**
 * @author: Viltene
 * It is called by LevelSet which passes down the scanner and appropriate art assets to create platforms.
 * 
 * This part of the Level package is concerned with putting together multiple platforms to make a level.
 * This is done by calling Platform class for every read data about platforms and 
 * applying textures to the returned represenattions.
 * 
 * There is a .txt explaining how levels are made in Level resources 
 * @see <a href= "structureOfTheLevelFiles.txt"> More information on types </a>  
 */
public class Level
{
    // Primary initialisation
    int numOfPlatforms = 0;
    public Platform platforms[];
    Texture textures[] = new Texture[8];
    Image image[] = new Image[8];
    /**
     * Constructor for level
     * @param s Scanner initialised in LevelSet
     * @param im array of 8 of images that represent the different type of platforms 
     */
    public Level(Scanner s, Image im[])
    {
        for(int i = 0; i < 8; i++)
        {
           image[i] = new Image();
           image[i] = im[i];
           //making textures out of images
           textures[i] = new Texture();
           try{
               textures[i].loadFromImage(image[i]);
               textures[i].setRepeated(true);
           }catch (TextureCreationException ex){
               ex.printStackTrace();
           }
        }
        //creates platforms
        declarePlatforms(s);
    }

    /**
     * Reads the platform file and establishes Platforms
     * @param scanner Scanner
     */
    private void declarePlatforms(Scanner scanner)
    {
        int l, h, x, y, t, d, movementx, movementy, mr;
        boolean isMoving;
            numOfPlatforms = scanner.nextInt();
            platforms = new Platform[numOfPlatforms];
            for (int i = 0; i < numOfPlatforms; i++) {
                l = scanner.nextInt();
                h = scanner.nextInt();
                x = scanner.nextInt();
                y = scanner.nextInt();
                t = scanner.nextInt();
                d = scanner.nextInt();
                isMoving = scanner.nextBoolean();
                movementx = scanner.nextInt();
                movementy = scanner.nextInt();
                mr = scanner.nextInt();
                platforms[i] = new Platform(l, h, x, y, t, d, isMoving, movementx, movementy, mr);
            }
            setPlatformColors();
    }

    /**
     * Getter of the platform set
     *  @return platforms - Platform type array
     */
    public Platform[] getPlatforms ()
    {
        return platforms;
    }

    /**
     * Sets appropriate textures depending on the type
     * @see <a href= "structureOfTheLevelFiles.txt"> More information on types </a>  
     */
    private void setPlatformColors ()
    {
        for(int i = 0; i < numOfPlatforms; i++)
        {
            // Type 0 - Spikes
            if(platforms[i].getPlatformType() == 0)
            {
                // 1 - up 
                if(platforms[i].getDirection() == 1)
                {
                    platforms[i].setPlatformTexture(textures[0]);
                }

                // 2 - left
                if(platforms[i].getDirection() == 2)
                {
                    platforms[i].setPlatformTexture(textures[1]);
                }

                // 3 - down
                if(platforms[i].getDirection() == 3)
                {
                    platforms[i].setPlatformTexture(textures[2]);
                }

                // 4 - right
                if(platforms[i].getDirection() == 4)
                {
                    platforms[i].setPlatformTexture(textures[3]);
                }
            }

            // Type 1 - platform to stand on 
            if(platforms[i].getPlatformType() == 1)
            {
                platforms[i].setPlatformTexture(textures[4]);
            }

            // Type 2 - platform to climb
            if(platforms[i].getPlatformType() == 2) {
                // 1 - right
                if (platforms[i].getDirection() == 1) {
                    platforms[i].setPlatformTexture(textures[5]);
                }
                // 2 - left
                if (platforms[i].getDirection() == 2) {
                    platforms[i].setPlatformTexture(textures[6]);

                }
            }

            // Type 3 - filler
            if(platforms[i].getPlatformType() == 3)
            {
                platforms[i].setPlatformTexture(textures[7]);
            }

            // if decorations ever were implemented
            if(platforms[i].getPlatformType() == 4)
            {
                platforms[i].setPlatformColor(Color.MAGENTA);
            }
        }
    }
}