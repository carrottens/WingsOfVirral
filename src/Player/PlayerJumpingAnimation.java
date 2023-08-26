package Player;

import org.jsfml.graphics.Image;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author: Viltene
 * This class is responsible for animation of jumping.
 * Implemented in the Player class
*/
public class PlayerJumpingAnimation
{
    /* Images */
    private Image jumpingRightCycle = new Image();
    private Image jumpingLeftCycle = new Image();

    /* Textures */
    private Texture jumpingRight = new Texture();
    private Texture jumpingLeft = new Texture();

    /* Sizes of the frame of the animation in the sprite sheet
     * it was made by a group member and the width of the sprite 
     * is varying
     */
    private IntRect sizeOfSpriteSheetFrame = new IntRect(0, 0, 34, 51);
    private int jumpRX [] = new int[] {0, 34, 68, 102, 136, 170, 204};
    private int jumpLX [] = new int[] {204, 170, 136, 102, 68, 34, 0};
    
    /* Frame trackers */
    private int trackerL = 0;
    private int trackerR = 0;

    public PlayerJumpingAnimation()
    {
        /* Loading Images and Textures */
        try{
            jumpingRightCycle.loadFromFile(Paths.get("ArtAssets/jump2R.png"));
            jumpingRight.loadFromImage(jumpingRightCycle);
            jumpingLeftCycle.loadFromFile(Paths.get("ArtAssets/jump2.png"));
            jumpingLeft.loadFromImage(jumpingLeftCycle);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TextureCreationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter of the jumping to the right texture
     * @return texture of jumping to the right
     */
    public Texture getJumpRightTexture()
    {
        return jumpingRight;
    }

    /**
     * Getter of the appropriate jumping to the right animation frame  
     * @return appropriate animation frame  of jumping to the right
     */
    public IntRect getCurrentFrameJumpingRight()
    {
        trackerL = 0;
        sizeOfSpriteSheetFrame = new IntRect(jumpRX[trackerR], 0, 34, 51);
        if (trackerR == 6)
        {
            trackerR = 0;
        }
        else
        {
            trackerR++;
        }
        return sizeOfSpriteSheetFrame;
    }

    /**
     * Getter of the jumping to the left texture
     * @return texture of jumping to the left
     */
    public Texture getJumpLeftTexture()
    {
        return jumpingLeft;
    }

    /**
     * Getter of the appropriate jumping to the left animation frame 
     * @return appropriate animation frame of jumping to the left
     */
    public IntRect getCurrentFrameJumpingLeft()
    {
        trackerR = 0;
        sizeOfSpriteSheetFrame = new IntRect(jumpLX[trackerL], 0, 34, 51);
        if (trackerL == 6)
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
