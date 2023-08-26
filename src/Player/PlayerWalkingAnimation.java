package Player;

import org.jsfml.graphics.Image;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;

import java.io.IOException;
import java.nio.file.Paths;

public class PlayerWalkingAnimation
{
    Image walkRightCycle = new Image();
    Texture walkingRight = new Texture();
    Image walkLeftCycle = new Image();
    Texture walkingLeft = new Texture();
    IntRect sizeOfSpriteSheetFrame = new IntRect(0, 0, 40, 51);
    int walkingRx [] = new int[] {3, 41, 77, 116, 158, 200, 242};
    int walkingRw [] = new int[] {37, 35, 38, 41, 41, 41, 41};
    int walkingLw [] = new int[] {40, 42, 41, 41, 38, 35, 37};
    int walkingLx [] = new int[] {7, 49, 91, 133, 175, 214, 250};
    int trackerR = -1;
    int trackerL = 7;
    int pacer = 0;
    int pacerForWalking = 2;
    public PlayerWalkingAnimation()
    {
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

    public Texture getWalkingRightTexture()
    {
        return walkingRight;
    }

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

    public Texture getWalkingLeftTexture()
{
    return walkingLeft;
}

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

    public void increasePacer()
    {
        pacerForWalking = pacerForWalking * 2;
    }

    public void decreasePacer()
    {
        pacerForWalking = pacerForWalking / 2;
        if(pacer > pacerForWalking)
        {
            pacer = 0;
        }
    }


}
