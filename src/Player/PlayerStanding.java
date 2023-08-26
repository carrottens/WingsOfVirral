package Player;

import org.jsfml.graphics.Image;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;

import java.io.IOException;
import java.nio.file.Paths;

public class PlayerStanding
{
    Image standingRightCycle = new Image();
    Texture standingRight = new Texture();
    Image standingLeftCycle = new Image();
    Texture standingLeft = new Texture();
    IntRect sizeOfSpriteSheetFrame = new IntRect(0, 0, 32, 51);
    private int standingRW [] = new int[] {32, 31, 33, 33, 33, 33};
    private int standingRX [] = new int[] {0, 34, 66, 100, 134, 167};
    private int standingLX [] = new int[] {0, 35, 69, 103, 137, 169};
    private int standingLW [] = new int[] {34, 34, 34, 33, 31, 33};
    int trackerR = 0;
    int trackerL = 5;

    public PlayerStanding()
    {
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

    public Texture getStandingRightTexture()
    {
        return standingRight;
    }

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

    public Texture getStandingLeftTexture()
    {
        return standingLeft;
    }

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
