package Boss;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

public abstract class BulletP {
    private RectangleShape bullet;

    public RectangleShape createBullet(int a, int b, float x, float y) {
        Vector2f v = new Vector2f(a, b);
        bullet = new RectangleShape(v);
        bullet.setPosition(x, y);
        bullet.setFillColor(Color.RED);
        return bullet;
    }

    public void redraw(RenderWindow window) {
        window.draw(bullet);
    }

}
