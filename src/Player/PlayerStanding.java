package Player;

import org.jsfml.graphics.Image;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author: Viltene
 * This class is responsible for animation of standing (idling).
 * Implemented in the Player class
*/
public class PlayerStanding
{
    /* Images */
    private Image standingRightCycle = new Image();
    private Image standingLeftCycle = new Image();

    /* Textures */
    private Texture standingRight = new Texture();
    private Texture standingLeft = new Texture();

    /* Sizes of the frame of the animation in the sprite sheet
     * it was made by a group member and the width of the sprite 
     * is varying
     */
    private IntRect sizeOfSpriteSheetFrame = new IntRect(0, 0, 32, 51);
    private int standingRW [] = new int[] {32, 31, 33, 33, 33, 33};
    private int standingRX [] = new int[] {0, 34, 66, 100, 134, 167};
    private int standingLX [] = new int[] {0, 35, 69, 103, 137, 169};
    private int standingLW [] = new int[] {34, 34, 34, 33, 31, 33};
    
    /* Animation frame trackers */
    private int trackerR = 0;
    private int trackerL = 5;

    public PlayerStanding()
    {
        /* Load images and textures */
        try{
        standingRightCycle.loadFromFile(Paths.get("ArtAssets/standingR.png"));
        standingRight.loadFromImage(standingRightCycle);
        standingLeftCycle.loadFromFile(Paths.get("ArtAssets/standingL.png"));
        standingLeft.loadFromImage(standingLeftCycle);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TextureCreationException e) {
         e.printStackTrace();
        }
    }

    /**
     * Getter of the standing facing to the right texture
     * @return texture standing facing to the right
     */
    public Texture getStandingRightTexture()
    {
        return standingRight;
    }

    /**
     * Getter of the appropriate idle to the right animation frame 
     * @return appropriate animation frame of idling to the right
     */
    public IntRect getCurrentFrameStandingRight()
    {
        trackerL = 0;
        sizeOfSpriteSheetFrame = new IntRect(standingRX[trackerR], 0, standingRW[trackerR], 51);
        if (trackerR == 0)
        {
            trackerR = 5;
        }
        else
        {
            trackerR = trackerR - 1;
        }
        return sizeOfSpriteSheetFrame;
    }

    /**
     * Getter of the standing facing to the left texture
     * @return texture standing facing to the left
     */    
    public Texture getStandingLeftTexture()
    {
        return standingLeft;
    }

    /**
     * Getter of the appropriate idle to the left animation frame 
     * @return appropriate animation frame of idling to the left
     */
    public IntRect getCurrentFrameStandingLeft()
    {
        trackerR = 5;
        sizeOfSpriteSheetFrame = new IntRect(standingLX[trackerL], 0, standingLW[trackerL], 51);
        if (trackerL == 5)
        {
            trackerL = 0;
        }
        else
        {
            trackerL++;
        }
        return sizeOfSpriteSheetFrame;
    }
}
