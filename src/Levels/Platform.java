/*This part of the Level programming is concerned
with the platform looks setting:
it
 */
package Levels;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public class Platform
{
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

    public Platform (int h, int l, int a, int b, int t, int d, boolean ism, int howMuchMovesX, int howMuchMovesY, int movementRate)
    {
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

    private void setPlatform (int a, int b, int c, int d)
    {
        Vector2f v = new Vector2f (a, b);
        platformDepiction = new RectangleShape (v);
        platformDepiction.setPosition(c, d);
        platformDepiction.setFillColor(Color.WHITE);
    }


    public void setPlatformColor (Color c)
    {
        platformDepiction.setFillColor(c);
    }

    public void setPlatformTexture (Texture t)
    {
        IntRect r = new IntRect(0, 0, length, height);
        t.setRepeated(true);
        platformDepiction.setTextureRect(r);
        platformDepiction.setTexture(t);
    }

    public int getPlatformType ()
    {
        return type;
    }

    public int getDirection()
    {
        return direction;
    }

    public RectangleShape getPlatform()
    {
        return  platformDepiction;
    }

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

    public int getPosX()
    {
        return this.x;
    }
    public int getPosY()
    {
        return this.y;
    }

    public int getBoundX()
    {
        int GBX = this.x + this.length;
        return GBX;
    }

    public int getBoundY()
    {
        int GBY = this.y + this.height;
        return GBY;
    }

    public void halfPlatformSpeed()
    {
        movementPerLoop = movementPerLoop / 2;
    }

    public void resetPlatformSpeed()
    {
        movementPerLoop = previousMovement;
    }



}
