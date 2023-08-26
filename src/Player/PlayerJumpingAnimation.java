package Player;

import org.jsfml.graphics.Image;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;

import java.io.IOException;
import java.nio.file.Paths;

public class PlayerJumpingAnimation
{
    Image jumpingRightCycle = new Image();
    Texture jumpingRight = new Texture();
    Image jumpingLeftCycle = new Image();
    Texture jumpingLeft = new Texture();
    IntRect sizeOfSpriteSheetFrame = new IntRect(0, 0, 34, 51);
    private int jumpRX [] = new int[] {0, 34, 68, 102, 136, 170, 204};
    private int jumpLX [] = new int[] {204, 170, 136, 102, 68, 34, 0};
    int trackerL = 0;
    int trackerR = 0;

    public PlayerJumpingAnimation()
    {
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

    public Texture getJumpRightTexture()
    {
        return jumpingRight;
    }

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

    public Texture getJumpLeftTexture()
    {
        return jumpingLeft;
    }

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
