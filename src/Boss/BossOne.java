package Boss;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.CircleShape;

import java.util.Random;

/**
 * @author: Yujie Chen
 * @edited: Viltene Cibirkaite
 * @date: 21-02-12
 * @version: 1.2.0
 */
public class BossOne extends Boss {
    private int BossOneLife = 3;
    private float BossOneDamage = 100;
    private float BossOnePositionX = 850;
    private float BossOnePositionY = 360;
    private boolean boundary = false;
    private int decide = 0;
    Random random = new Random();
    private RectangleShape boss;
    // private RectangleShape bullet;
    // private Player player;

    public BossOne(float bossOneLife, float bossOneDamage, float bossOnePositionX, float bossOnePositionY) {
        BossOneLife = (int) bossOneLife;
        BossOneDamage = bossOneDamage;
        BossOnePositionX = bossOnePositionX;
        BossOnePositionY = bossOnePositionY;
        boss = createABoss(100, 100, bossOnePositionX, bossOnePositionY);
        // bullet = createBullet(20, 20, bossOnePositionX - 65, bossOnePositionY - 40);
    }

    public void move() {
        int r = random.nextInt(3);
        if (r == 0 || r == 2) {
            if (decide == 0) {
                if (BossOnePositionX < 480) {
                    BossOnePositionX += 5;
                    returnedBlood();
                } else if (BossOnePositionX >= 480 && BossOnePositionX <= 1150) {
                    BossOnePositionX += 5;
                } else if (BossOnePositionX > 1150) {
                    BossOnePositionX -= 5;
                    returnedBlood();
                    decide = 1;
                }
            } else if (decide == 1) {
                if (BossOnePositionX < 480) {
                    BossOnePositionX += 5;
                    returnedBlood();
                    decide = 0;
                } else if (BossOnePositionX >= 480 && BossOnePositionX <= 1150) {
                    BossOnePositionX -= 5;
                }
            }
        } else if (r == 1) {
            if (decide == 0) {
                if (BossOnePositionY < 60) {
                    BossOnePositionY += 5;
                    returnedBlood();
                } else if (BossOnePositionY > 660) {
                    BossOnePositionY -= 5;
                    returnedBlood();
                    decide = 1;
                } else if (BossOnePositionY >= 60 && BossOnePositionY <= 660) {
                    BossOnePositionY += 5;
                }
            } else if (decide == 1) {
                if (BossOnePositionY < 60) {
                    BossOnePositionY += 5;
                    returnedBlood();
                    decide = 0;
                } else if (BossOnePositionY > 660) {
                    BossOnePositionY -= 5;
                    returnedBlood();
                } else if (BossOnePositionY >= 60 && BossOnePositionY <= 660) {
                    BossOnePositionY -= 5;
                }
            }
            // while (boundary == false) {
            shoot(BossOnePositionX, BossOnePositionY);
            // }

        }
        // bullet.setPosition(BossOnePositionX - 65, BossOnePositionY - 40);
        boss.setPosition(BossOnePositionX, BossOnePositionY);

    }

    public void shoot(float x, float y) {
        for (int i = 10; i > 0; i--) {
            x = x - 520;
            // bullet.setPosition(x, y);
            if (x <= 0) {
                // boundary = true;
                break;
            }

        }

    }

    public void returnedBlood() {
        if (BossOneLife < 3) {
            BossOneLife += 1;
        }
    }

    public void setBossOneLife(float bossOneLife) {
        BossOneLife = (int) bossOneLife;
    }

    public void setBossOneDamage(double bossOneDamage) {
        BossOneDamage = (int) bossOneDamage;
    }

    public void setBossOnePositionX(float bossOnePositionX) {
        BossOnePositionX = bossOnePositionX;
    }

    public void setBossOnePositionY(float bossOnePositionY) {
        BossOnePositionY = bossOnePositionY;
    }

    public double getBossOneLife() {
        return BossOneLife;
    }

    public double getBossOneDamage() {
        return BossOneDamage;
    }

    public float getBossOnePositionX() {
        return BossOnePositionX;
    }

    public float getBossOnePositionY() {
        return BossOnePositionY;
    }

    @Override
    public void move(int playerX, int playerY) {
        // TODO Auto-generated method stub

    }
}
