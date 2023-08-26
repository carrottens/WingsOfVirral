package Boss;

import org.jsfml.graphics.Image;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author: Yujie
 * @author: Viltene
 * @version: 21-01-09, last updated on 23-08-21
 * This is a class that describes basic functionality of a boss.
 * This is further extended in classes BossOne and BossTwo 
 */
public class Bullet
{
    /* Basic initialisation */
    private RectangleShape bullet;
    private Image bulletImage;
    private Texture bulletTexture;
    private int height;
    private int width;
    private int BulletPositionX;
    private int BulletPositionY;
    private int bspeed = 0;
    private int previousSpeed = bspeed;
    private boolean active = false;

    /**
     * Constructor
     * @param bulletPositionX x coordinate
     * @param bulletPositionY y coordinate
     * @param c 0 = mannor, 1 = laboratory
     */
    public Bullet(int bulletPositionX, int bulletPositionY, int c) 
    {
        /* set x and y */
        BulletPositionX = bulletPositionX;
        BulletPositionY = bulletPositionY;

        if(c == 1)
        {
            height = 40;
            width = 30; 
        }
        else
        {
            height = 40;
            width = 40;
        }
        bullet = createBullet(height, width, bulletPositionX, bulletPositionY, c);
    }

    /**
     * Creates a bullet representation
     * @param a length
     * @param b width
     * @param x x coordinate
     * @param y y coordinate
     * @param choice level choice from GUI
     * @return bullet representation
     */
    public RectangleShape createBullet(int a, int b, int x, int y, int choice) 
    {
        Vector2f v = new Vector2f(a, b);
        bullet = new RectangleShape(v);
        bullet.setPosition(x, y);

        bulletImage = new Image();
        bulletTexture = new Texture();

        if(choice == 1)
        {
            try {
                bulletImage.loadFromFile(Paths.get("ArtAssets/Swordsman/Explosion_two_colors1.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                bulletTexture.loadFromImage(bulletImage);
            }catch (TextureCreationException tce){
                tce.printStackTrace();}
            bullet.setTexture(bulletTexture);
        }
        else
        {
            try {
                bulletImage.loadFromFile(Paths.get("ArtAssets/Countess_Vampire/Blood_Charge_4.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                bulletTexture.loadFromImage(bulletImage, new IntRect(0, 0, 48, 48));
            }catch (TextureCreationException tce){
                tce.printStackTrace();}
            bullet.setTexture(bulletTexture);
        }
        return bullet;
    }

    /**
     * Redraw the bullet in the given window
     * @param window window of the game (from GUI)
     */
    public void redraw(RenderWindow window) 
    {
        window.draw(bullet);
    }

    /**
     * Shooting mechanic
     * @param x next x position
     */
    public void shoot(int x) {
        x -= 30;
        x += bspeed;
        if (bspeed == 30) {
            bspeed = 0;
        } else {
            bspeed++;
        }
        BulletPositionX = x;
        bullet.setPosition(x, BulletPositionY);
    }

    /**
     * Getter of bullet X coordinate
     * @return x coordinate of the bullet
     */
    public int getBulletPositionX() {
        return BulletPositionX;
    }

    /**
     * Getter of bullet Y position
     * @return y coordinate of the bullet
     */
    public int getBulletPositionY() {
        return BulletPositionY;
    }

    /**
     * Getter for bullet sprite right-most x coordinate
     * @return right-most x coordinate
     */
    public int getGlobalX()
    {
        return BulletPositionX + width;
    }

    /**
     * Getter for bullet sprite top-most y coordinate
     * @return top-most y coordinate
     */
    public int getGlobalY()
    {
        return BulletPositionY + height;
    }

    /**
     * Setter for the x position of the bullet
     * @param bulletPositionX new x coordinate
     */
    public void setBulletPositionX(int bulletPositionX) {
        BulletPositionX = bulletPositionX;
    }

    /**
     * Setter for the y position of the bullet
     * @param bulletPositionY new y coordinate
     */
    public void setBulletPositionY(int bulletPositionY) {
        BulletPositionY = bulletPositionY;
    }

    /**
     * Getter of a boolean if the bullet is still active
     * @return true = bullet is still active || false = not active
     */
    public boolean getActive()
    {
        return active;
    }

    /**
     * Setter for the bullet state
     * @param tf true = active || false = not active
     */
    public void setActive(boolean tf)
    {
        active = tf;
    }

    /**
     * Halves bullet speed to implement time slow
     */
    public void timeSlow()
    {
        previousSpeed = bspeed;
        bspeed = bspeed / 2;
    }

    /**
     * Gets the bullet speed to the speed it had before time slow
     */
    public void resetSpeed()
    {
        bspeed = previousSpeed;
    }
}