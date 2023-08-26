package Boss;

import org.jsfml.graphics.RectangleShape;

import java.util.Random;

/**
 * @author: Yujie Chen
 * @edited: Viltene Cibirkaite
 * @date: 21-02-12
 * @version: 1.2.0
 */
public class BossTwo extends Boss {
    private int BossOneLife = 4;
    private float BossOneDamage = 100;
    private float BossOnePositionX = 850;
    private float BossOnePositionY = 360;
    private boolean boundary = false;
    private int decide = 0;
    Random random = new Random();
    private RectangleShape boss;
    // private RectangleShape bullet;
    // private Player player;

    public BossTwo(float bossOneLife, float bossOneDamage, float bossOnePositionX, float bossOnePositionY) {
        BossOneLife = (int) bossOneLife;
        BossOneDamage = bossOneDamage;
        BossOnePositionX = bossOnePositionX;
        BossOnePositionY = bossOnePositionY;
        boss = createABoss(100, 100, bossOnePositionX, bossOnePositionY);
    }

    public void move() {
        int r = random.nextInt(3);
        if (r == 0 || r == 2) {
            if (decide == 0) {
                if (BossOnePositionX < 480) {
                    BossOnePositionX += 10;
                    returnedBlood();
                } else if (BossOnePositionX >= 480 && BossOnePositionX <= 1150) {
                    BossOnePositionX += 10;
                } else if (BossOnePositionX > 1150) {
                    BossOnePositionX -= 10;
                    returnedBlood();
                    decide = 1;
                }
            } else if (decide == 1) {
                if (BossOnePositionX < 480) {
                    BossOnePositionX += 10;
                    returnedBlood();
                    decide = 0;
                } else if (BossOnePositionX >= 480 && BossOnePositionX <= 1150) {
                    BossOnePositionX -= 10;
                }
            }
        } else if (r == 1) {
            if (decide == 0) {
                if (BossOnePositionY < 60) {
                    BossOnePositionY += 10;
                    returnedBlood();
                } else if (BossOnePositionY > 660) {
                    BossOnePositionY -= 10;
                    returnedBlood();
                    decide = 1;
                } else if (BossOnePositionY >= 60 && BossOnePositionY <= 660) {
                    BossOnePositionY += 10;
                }
            } else if (decide == 1) {
                if (BossOnePositionY < 60) {
                    BossOnePositionY += 10;
                    returnedBlood();
                    decide = 0;
                } else if (BossOnePositionY > 660) {
                    BossOnePositionY -= 10;
                    returnedBlood();
                } else if (BossOnePositionY >= 60 && BossOnePositionY <= 660) {
                    BossOnePositionY -= 10;
                }
            }
            shoot(BossOnePositionX, BossOnePositionY);

        }
        boss.setPosition(BossOnePositionX, BossOnePositionY);

    }

    public void shoot(float x, float y) {

    }

    public void returnedBlood() {
        if (BossOneLife < 3) {
            BossOneLife += 1;
        }
    }

    public void setBossTwoLife(float bossOneLife) {
        BossOneLife = (int) bossOneLife;
    }

    public void setBossTwoDamage(double bossOneDamage) {
        BossOneDamage = (int) bossOneDamage;
    }

    public void setBossTwoPositionX(float bossOnePositionX) {
        BossOnePositionX = bossOnePositionX;
    }

    public void setBossTwoPositionY(float bossOnePositionY) {
        BossOnePositionY = bossOnePositionY;
    }

    public double getBossTwoLife() {
        return BossOneLife;
    }

    public double getBossTwoDamage() {
        return BossOneDamage;
    }

    public float getBossTwoPositionX() {
        return BossOnePositionX;
    }

    public float getBossTwoPositionY() {
        return BossOnePositionY;
    }

    @Override
    public void move(int playerX, int playerY) {
        // TODO Auto-generated method stub

    }
}
