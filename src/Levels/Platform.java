package Levels;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
* @author: Viltene
* This part of the Level package is concerned
* with the platform building
* It is called by Level which supplies all of the platfor parameters
* @see <a href= "structureOfTheLevelFiles.txt"> More information on types </a>  
*/
public class Platform
{
    //Safety initiallisation
    RectangleShape platformDepiction;
    private int x = 0;
    private int startingX = 0;
    private int endingX = 0;
    private int y = 0;
    private int length;
    private int height;
    private int startingY = 0;
    private int endingY = 0;
    private int movementPerLoop = 0;
    private int type = 0;
    private boolean isMovingUp = false;
    private boolean isMovingForward = false;
    private boolean isMoving;
    private int direction = 0;
    private int previousMovement = 0;

    /**
     * Constructor for Platform
     * @param h hieght
     * @param l length
     * @param a x coordinate
     * @param b y coordinate
     * @param t type
     * @param d direction/subtype
     * @param ism true = platform is moving || false = platform does not move
     * @param howMuchMovesX movement of platform on x axis
     * @param howMuchMovesY movement of platform on y axis
     * @param movementRate how much the platform moves per loop
     */
    public Platform (int h, int l, int a, int b, int t, int d, boolean ism, int howMuchMovesX, int howMuchMovesY, int movementRate)
    {
        // Initialise when called
        height = h;
        length = l;
        x = a;
        startingX = a;
        endingX = x + howMuchMovesX;
        y = b;
        startingY = b;
        endingY = y + howMuchMovesY;
        movementPerLoop = movementRate;
        previousMovement = movementRate;
        type = t;
        direction = d;
        isMoving = ism;
        setPlatform(l, h, x, y);
        if(startingX != endingX)
        {
            isMovingForward = true;
        }
        if(startingY != endingY)
        {
            isMovingUp = true;
        }
    }

    /**
     * Setter for a platform
     * @param a length of the platform
     * @param b height of the platform
     * @param c x coordinate of the platform
     * @param d y coordinate of the platform
     */
    private void setPlatform (int a, int b, int c, int d)
    {
        Vector2f v = new Vector2f (a, b);
        platformDepiction = new RectangleShape (v);
        platformDepiction.setPosition(c, d);
        platformDepiction.setFillColor(Color.WHITE);
    }

    /**
     * Setter for platform colour
     * @param c colour
     */
    public void setPlatformColor (Color c)
    {
        platformDepiction.setFillColor(c);
    }

    /**
     * Setter for platfor texture
     * @param t texture
     */
    public void setPlatformTexture (Texture t)
    {
        IntRect r = new IntRect(0, 0, length, height);
        t.setRepeated(true);
        platformDepiction.setTextureRect(r);
        platformDepiction.setTexture(t);
    }

    /**
     * Returns platform's type 
     * (important for the implemention of Player logic)
     * @return platform type
     * @see <a href= "structureOfTheLevelFiles.txt"> More information on types </a>  
     */
    public int getPlatformType ()
    {
        return type;
    }

    /**
     * Getter of platform subtype (direction)
     * @see <a href= "structureOfTheLevelFiles.txt"> More information on types </a>  
     * @return platform subtype (aka direction)
     */
    public int getDirection()
    {
        return direction;
    }

    /**
     * Getter of the platform represenations
     * @return platform depiction
     */
    public RectangleShape getPlatform()
    {
        return  platformDepiction;
    }

    /**
     * Moves the platform if it's a moving one
     */
    public void movePlatform ()
    {
        if(isMoving)
        {
            if (isMovingForward && x < endingX) {
                x = x + movementPerLoop;
            }
            if (x >= endingX) {
                isMovingForward = false;
            }
            if (!isMovingForward && x > startingX) {
                x = x - movementPerLoop;
            }
            if (x <= startingX)
            {
                isMovingForward = true;
            }

            if (isMovingUp && y < endingY) {
                y = y + movementPerLoop;
            }
            if (y >= endingY) {
                isMovingUp = false;
            }
            if (!isMovingUp && y > startingY) {
                y = y - movementPerLoop;
            }
            if (y <= startingY)
            {
                isMovingUp = true;
            }

            platformDepiction.setPosition(x, y);
        }

    }

    /**
     * Getter of the platform's x coordinate
     * @return x coordinate of the platform
     */
    public int getPosX()
    {
        return this.x;
    }
    
    /**
     * Getter of the platform's y coordiate
     * @return y coordinate of the platform
     */
    public int getPosY()
    {
        return this.y;
    }

    /**
     * Gets the x of the platform's end
     * @return x of the platform's end
     */
    public int getBoundX()
    {
        int GBX = this.x + this.length;
        return GBX;
    }

    /**
     * Gets the y of the platform's end
     * @return y of the platform's end
     */
    public int getBoundY()
    {
        int GBY = this.y + this.height;
        return GBY;
    }

    /**
     * Slows platform (half speed)  
     * Used for thw slow time mechanic
     */
    public void halfPlatformSpeed()
    {
        movementPerLoop = movementPerLoop / 2;
    }
    
    /**
     * Resets platform's speed
     * Used after the slow mechanic is no longer in effect
     */
    public void resetPlatformSpeed()
    {
        movementPerLoop = previousMovement;
    }
}
