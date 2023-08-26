package Boss;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

/**
 * @author: Yujie Chen
 * @date: 21-01-9
 * @version: 1.0.0
 */
public class Bullet extends BulletP {
    private float BulletPositionX;
    private float BulletPositionY;
    private RectangleShape bullet;
    private static int bspeed = 0;

    public Bullet(float bulletPositionX, float bulletPositionY) {
        BulletPositionX = bulletPositionX;
        BulletPositionY = bulletPositionY;
        bullet = createBullet(20, 20, bulletPositionX, bulletPositionY);
    }

    public void shoot(float x) {
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

    public float getBulletPositionX() {
        return BulletPositionX;
    }

    public float getBulletPositionY() {
        return BulletPositionY;
    }

    public void setBulletPositionX(float bulletPositionX) {
        BulletPositionX = bulletPositionX;
    }

    public void setBulletPositionY(float bulletPositionY) {
        BulletPositionY = bulletPositionY;
    }
}