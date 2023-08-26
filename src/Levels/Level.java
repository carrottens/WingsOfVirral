package Levels;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;

import java.util.Scanner;

public class Level
{
    int numOfPlatforms = 0;
    public Platform platforms[];
    Texture textures[] = new Texture[8];
    Image image[] = new Image[8];

    public Level(Scanner s, Image im[])
    {
        for(int i = 0; i < 8; i++)
        {
           image[i] = new Image();
           image[i] = im[i];

           textures[i] = new Texture();
           try{
               textures[i].loadFromImage(image[i]);
               textures[i].setRepeated(true);
           }catch (TextureCreationException ex){
               ex.printStackTrace();
           }
        }

        declarePlatforms(s);
    }

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
                System.out.print(l + " " + h + " " + x + " " + y + " " + t + " " + isMoving + " " + movementx + " " + movementy + " " + mr + "\n");
            }
            setPlatformColors();
    }

    public Platform[] getPlatforms ()
    {
        return platforms;
    }

    private void setPlatformColors ()
    {
        for(int i = 0; i < numOfPlatforms; i++)
        {

            if(platforms[i].getPlatformType() == 0)
            {
                if(platforms[i].getDirection() == 1)
                {
                    platforms[i].setPlatformTexture(textures[0]);
                }

                if(platforms[i].getDirection() == 2)
                {
                    platforms[i].setPlatformTexture(textures[1]);
                }

                if(platforms[i].getDirection() == 3)
                {
                    platforms[i].setPlatformTexture(textures[2]);
                }

                if(platforms[i].getDirection() == 4)
                {
                    platforms[i].setPlatformTexture(textures[3]);
                }
            }

            if(platforms[i].getPlatformType() == 1)
            {
//                try {
//                    textures[1].loadFromImage(image[1]);
//                    textures[1].setRepeated(true);
                    platforms[i].setPlatformTexture(textures[4]);
//                } catch (TextureCreationException ex){
//                    ex.printStackTrace();
//                }
            }

            if(platforms[i].getPlatformType() == 2) {
                if (platforms[i].getDirection() == 1) {
                    platforms[i].setPlatformTexture(textures[5]);
                }

                if (platforms[i].getDirection() == 2) {
                    platforms[i].setPlatformTexture(textures[6]);

                }
            }

            if(platforms[i].getPlatformType() == 3)
            {
//                try {
//                    textures[3].loadFromImage(image[3]);
//                    textures[3].setRepeated(true);
                    platforms[i].setPlatformTexture(textures[7]);
//                } catch (TextureCreationException ex){
//                    ex.printStackTrace();
//                }
            }

            if(platforms[i].getPlatformType() == 4)
            {
                platforms[i].setPlatformColor(Color.MAGENTA);
            }
        }
    }

}