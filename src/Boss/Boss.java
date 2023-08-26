package Boss;

import java.util.Random;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

/**
 * @author: Yujie
 * @author: Viltene
 * @version: 21-02-12, last updated on 23-08-26
 * This is a class that describes basic functionality of a boss.
 * This is further extended in classes BossOne and BossTwo 
 */
public abstract class Boss {
    
    /* Basic boss parameters */
    protected int life = 3;
    protected int positionX = 850;
    protected int positionY = 320;
    protected int speed = 10;
    protected int widthOfSprite = 100;
    protected int heightOfSprite = 100;
    
    /* Representation */
    protected RectangleShape boss;    

    /* Random decision making variables */
    protected int decide = 0;
    protected Random random = new Random();

    /* State tracking variables */
    protected boolean isHurt = false;
    protected boolean right = false;
    protected boolean shooting = false;
    protected boolean hurting = false;
    protected boolean victory = false;

    /**
     * Creates a boss using given parameters
     * @param a width
     * @param b height
     * @param x x coordinate
     * @param y y coordinate
     * @return boss representation without any texture
     */
    public RectangleShape createABoss(int a, int b, int x, int y) {
        Vector2f v = new Vector2f(a, b);
        boss = new RectangleShape(v);
        boss.setPosition(x, y);
        return boss;
    }

    /**
     * Redraws the boss
     * @param window
     */
    public void redraw(RenderWindow window) {
        window.draw(boss);
    }

    /**
     * Abstract method for shooting.
     * Implemented in BossOne and BossTwo
     */
    public abstract void shoot();

    /**
     * Setter for x coordinate of the boss
     * @param positionX x coordinate of the boss
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    /**
     * Setter for the y coordinate of the boss 
     * @param positionY
    */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    /**
     * Getter of the x coordinate of the boss
     * @return x coordinate of the boss
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Getter of the y coordinate of the boss
     * @return y coordinate of the boss
     */
    public int getPositionY() {
        return positionY;
    }

    /**
     * Getter of the ending x coordinate of the boss sprite
     * @return the right-most x coordinate of the boss sprite
    */
    public int getGlobalX()
    {
        return positionX+widthOfSprite;
    }

    /**
     * Getter of the ending y coordinate of the boss sprite
     * @return the top-most y coordinate of the boss sprite
    */
    public int getGlobalY()
    {
        return positionY+heightOfSprite;
    }

    /**
     * Getter if the victory has been achieved
     * @return true = victory || false = game has not been won yet
     */
    public boolean checkIfWon()
    {
        return victory;
    }

    /**
     * Implementation of random behaviour of the boss
     */
    public void move() {
        shoot();
        /* If boss is not shooting or getting hurt it moves */
        if(!shooting && !hurting)
        {
            int r = random.nextInt(3);
            if (r == 0 || r == 2) {
                if (decide == 0) {
                    if (positionX < 300) {
                        positionX += speed;
                        right = true;
                    } else if (positionX >= 300 && positionX <= 1000) {
                        positionX += speed;
                        right = true;
                    } else if (positionX > 1000) {
                        positionX -= speed;
                        decide = 1;
                        right = false;
                    }
                } else if (decide == 1) {
                    if (positionX < 300) {
                        positionX += speed;
                        decide = 0;
                        right = true;
                    } else if (positionX >= 300 && positionX <= 1000) {
                        positionX -= speed;
                        right = false; 
                    }
                }
            } else if (r == 1) {
                if (decide == 0) {
                    if (positionY < 20) {
                        positionY += speed;
                    } else if (positionY > 600) {
                        positionY -= speed;
                        decide = 1;
                    } else if (positionY >= 20 && positionY <= 600) {
                        positionY += speed;
                    }
                } else if (decide == 1) {
                    if (positionY < 20) {
                        positionY += speed;
                        decide = 0;
                    } else if (positionY > 600) {
                        positionY -= speed;
                    } else if (positionY >= 20 && positionY <= 600) {
                        positionY -= speed;
                    }
                }
            }
            boss.setPosition(positionX, positionY);
            animation();
            }
        else
        {
            if(hurting)
            {
                hurtingAnimation();
            }
        }
    }

    /**
     * Animation for idle/moving state
     * Implemented in BossOne and BossTwo
     */
    public abstract void animation();

    /**
     * Animation when boss is getting hurt
     * Implemented in BossOne and BossTwo
     */
    public abstract void hurtingAnimation();

    /**
     * Logic when a boss loses a life
     */
    public void looseALife()
    {
        life--;
        hurting = true; //enable animation
        /* Rendomly set bosses position */
        setPositionX(random.nextInt(1000));
        setPositionY(random.nextInt(600));
        if(life==0)
        {
            victory = true; //win if boss runs out of lives
        }
    }

    /**
     * Halves boss' speed
     * This is used to implement slowmotion
     */
    public void halfSpeed()
    {
        speed = speed/2;
    }

    /**
     * Resets the boss' speed back to normal
     * Used when slowmotion is no longer in effect
     */
    public void resetSpeed()
    {
        speed = speed*2;
    }

    /**
     * Getter of lives of the boss
     * @return number of boss' lives
     */
    public int getNumberOfLives()
    {
        return life;
    }

    /**
     * Getter of the state hurting
     * @return true = boss is getting hurt || false = boss is not hurting
     */
    public boolean getHurt()
    {
        return hurting;
    }

    /**
     * Abstract getter of the shooting state
     * @return true = currently shooting || false = currently not shooting
     */
    public abstract boolean getShooting();
}