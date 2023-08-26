package Player;

import org.jsfml.graphics.Image;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author: Viltene
 * This class is responsible for animation of walking.
 * Implemented in the Player class
*/
public class PlayerWalkingAnimation
{
    /* Images */
    private Image walkRightCycle = new Image();
    private Image walkLeftCycle = new Image();

    /* Textures */
    private Texture walkingRight = new Texture();
    private Texture walkingLeft = new Texture();

    /* Sizes of the frame of the animation in the sprite sheet
     * it was made by a group member and the width of the sprite 
     * is varying
     */
    private IntRect sizeOfSpriteSheetFrame = new IntRect(0, 0, 40, 51);
    private int walkingRx [] = new int[] {3, 41, 77, 116, 158, 200, 242};
    private int walkingRw [] = new int[] {37, 35, 38, 41, 41, 41, 41};
    private int walkingLw [] = new int[] {40, 42, 41, 41, 38, 35, 37};
    private int walkingLx [] = new int[] {7, 49, 91, 133, 175, 214, 250};

    /* Trackers and pacers of the animation frames */
    private int trackerR = -1;
    private int trackerL = 7;
    private int pacer = 0;
    private int pacerForWalking = 2;

    public PlayerWalkingAnimation()
    {
        /* Loading images and textures */
        try{
            walkRightCycle.loadFromFile(Paths.get("ArtAssets/walkingR.png"));
            walkingRight.loadFromImage(walkRightCycle);
            walkLeftCycle.loadFromFile(Paths.get("ArtAssets/walkingL.png"));
            walkingLeft.loadFromImage(walkLeftCycle);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TextureCreationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter of the walking to the right texture
     * @return texture of walking to the right
     */
    public Texture getWalkingRightTexture()
    {
        return walkingRight;
    }

    /**
     * Getter of the appropriate walking to the right animation frame  
     * @return appropriate animation frame  of walking to the right
     */
    public IntRect getCurrentFrameWalkingRight()
    {
        trackerL = 7;
        pacer++;
        if(pacer >= pacerForWalking){
            pacer = 0;
        if(trackerR == 6)
        {
            trackerR = 0;
        }
        else{
            trackerR++;

        }
        sizeOfSpriteSheetFrame = new IntRect(walkingRx[trackerR], 0, walkingRw[trackerR], 51);}
        return sizeOfSpriteSheetFrame;
    }

    /**
     * Getter of the walking to the left texture
     * @return texture of walking to the left
     */
    public Texture getWalkingLeftTexture()
    {
    return walkingLeft;
    }

    /**
     * Getter of the appropriate walking to the left animation frame
     * @return appropriate animation frame of walking to the left
     */
    public IntRect getCurrentFrameWalkingLeft()
    {
        trackerR = -1;
        pacer++;
        if(pacer >= pacerForWalking){
            pacer = 0;
        if(trackerL == 0)
        {
            trackerL = 6;
        }
        else{
            trackerL = trackerL - 1;

        }
        sizeOfSpriteSheetFrame = new IntRect(walkingLx[trackerL], 0, walkingLw[trackerL], 51);}
        return sizeOfSpriteSheetFrame;
    }

    /**
     * Increases pacer by 2
     * Used to implement slow motion
     */
    public void increasePacer()
    {
        pacerForWalking = pacerForWalking * 2;
    }

    /**
     * Decreases pacer by 2
     * Used to implement slow motion
     */
    public void decreasePacer()
    {
        pacerForWalking = pacerForWalking / 2;
        if(pacer > pacerForWalking)
        {
            pacer = 0;
        }
    }

}
