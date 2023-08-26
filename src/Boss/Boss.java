package Boss;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

/**
 * @author: Yujie Chen
 * @edited: Viltene Cibirkaite
 * @date: 21-02-12
 * @version: 1.2.0
 */
public abstract class Boss {
    private double life;
    private double damage;
    private double positionX;
    private double positionY;
    private boolean isHurt = false;
    private RectangleShape boss;
    private RectangleShape bullet;

    public RectangleShape createABoss(int a, int b, float x, float y) {
        Vector2f v = new Vector2f(a, b);
        boss = new RectangleShape(v);
        boss.setPosition(x, y);
        boss.setFillColor(Color.RED);
        return boss;
    }

    public RectangleShape createBullet(int a, int b, float x, float y) {
        Vector2f v = new Vector2f(a, b);
        bullet = new RectangleShape(v);
        bullet.setPosition(x, y);
        bullet.setFillColor(Color.RED);
        return bullet;
    }

    public void redraw(RenderWindow window) {
        window.draw(boss);
        // window.draw(bullet);
    }

    public abstract void shoot(float x, float y);

    public abstract void move(int playerX, int playerY);

    public abstract void returnedBlood();

    public void setIsHurt(boolean hurt) {
        isHurt = hurt;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public void setLife(double life) {
        this.life = life;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public boolean getIsHurt() {
        return isHurt;
    }

    public double getLife() {
        return life;
    }

    public double getDamage() {
        return damage;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }
}