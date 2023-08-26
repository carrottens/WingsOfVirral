package GUI;

import Boss.BossOne;
import Boss.BossTwo;
import Boss.Bullet;
import Levels.LevelSet;
import Levels.Platform;
import Player.Player;
import org.jsfml.audio.Music;
import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class GUI 
{
    private static int kr = 2;
    private static int krForTimeSlow = 2;
    private Image backgr = new Image();

    //public static void main(String[] args) {
    public GUI(int skin, JFrame mainMenu, Music menuBeat, Music levelMusic) {

        int min = 0;
        int s = 0;
        long startOfTimeSlow = 0;
        long finishOfTimeSlow = 0;
        long previousElapsed = 0;
        int livesTracker = 6;
        int pass = 0;
        float timeSlowInMiliSeconds = 3 * 1000;
        int ensureOnlyOne = 0;

        //Hope tries to shove in Levels. Nothing to see here
        boolean levelChangeDetection = false;
        int curstage = skin;

        File lvl1Plat = new File("src/Levels/resources/Level1P.txt");
        File lvl2Plat = new File("src/Levels/resources/Level1P.txt");
        File lvl1Item = new File("src/Levels/resources/Items1.txt");
        File lvl2Item = new File("src/Levels/resources/Items1.txt");
        Image lvl1Im[] = new Image[8];
        Image lvl2Im[] = new Image[8];
        for (int i = 0; i < 8; i++)
        {
            lvl1Im[i] = new Image();
            lvl2Im[i] = new Image();
        }
        try {
            lvl1Im[0].loadFromFile(Paths.get("ArtAssets/spikes up 1.png"));
            lvl1Im[1].loadFromFile(Paths.get("ArtAssets/spikes L 1.png"));
            lvl1Im[2].loadFromFile(Paths.get("ArtAssets/spikes D 1.png"));
            lvl1Im[3].loadFromFile(Paths.get("ArtAssets/spikes R 1.png"));
            lvl1Im[4].loadFromFile(Paths.get("ArtAssets/wood top 1.png"));
            lvl1Im[5].loadFromFile(Paths.get("ArtAssets/wood side R 1.png"));
            lvl1Im[6].loadFromFile(Paths.get("ArtAssets/wood side L 1.png"));
            lvl1Im[7].loadFromFile(Paths.get("ArtAssets/wood mid 1.png"));

            lvl2Im[0].loadFromFile(Paths.get("ArtAssets/spikes up 1.png"));
            lvl2Im[1].loadFromFile(Paths.get("ArtAssets/spikes L 1.png"));
            lvl2Im[2].loadFromFile(Paths.get("ArtAssets/spikes D 1.png"));
            lvl2Im[3].loadFromFile(Paths.get("ArtAssets/spikes R 1.png"));
            lvl2Im[4].loadFromFile(Paths.get("ArtAssets/snow top 1.png"));
            lvl2Im[5].loadFromFile(Paths.get("ArtAssets/snow side R 1.png"));
            lvl2Im[6].loadFromFile(Paths.get("ArtAssets/snow side L 1.png"));
            lvl2Im[7].loadFromFile(Paths.get("ArtAssets/snow mid 1.png"));
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        Texture back = new Texture();
        if(curstage == 1)
        {
            try {
                backgr.loadFromFile(Paths.get("ArtAssets/OurGame.png"));
            }catch(IOException ex) {
                ex.printStackTrace();
            }

        }
        if(curstage == 0)
        {
            try {
                backgr.loadFromFile(Paths.get("ArtAssets/silverwood house.png"));
            }catch(IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            back.loadFromImage(backgr);
        }catch (TextureCreationException tce)
        {
            tce.printStackTrace();
        }
        Sprite spriteBackground = new Sprite();
        spriteBackground.setTexture(back);
        spriteBackground.setPosition(0,0);


        LevelSet ls[] = new LevelSet[2];
        ls[0] = new LevelSet(3, lvl1Plat, lvl1Item, lvl1Im);
        ls[1] = new LevelSet(3, lvl2Plat, lvl2Item, lvl2Im);
        Platform p[] = ls[curstage].getLevelPlatforms();

        BossOne boss1 = new BossOne(3, 1, 850, 360);
        Bullet bullet = new Bullet(850, 360);
        BossTwo boss2 = new BossTwo(3, 1, 100, 100);


        RenderWindow window = new RenderWindow();

        levelMusic.play();
        window.create(new VideoMode(1280, 720), "Wings of Virral");
        // an icon will go here
        Image icon = new Image();
        try {
            icon.loadFromFile(Paths.get("ArtAssets/artTest.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        window.setIcon(icon);

        // Will depend on the mechanics
        window.setFramerateLimit(30);

        // Different fonts can be loaded
        Font justSans = new Font();

        // Load From File throws an IOException
        try {
            justSans.loadFromFile(Paths.get("src/GUI/resources/FreeSans.ttf"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

//        Text livesText = new Text("Lives:", justSans, 20);
//        livesText.setPosition(0, 0);
        Text slowTimeText = new Text("Time slow remaining: 3 s", justSans, 20);
        slowTimeText.setString("");
        slowTimeText.setPosition(0, 40);
        Text timerText = new Text("Timer: 0", justSans, 20);
        timerText.setPosition(900, 0);
        Text levelText = new Text("Level: " + ls[curstage].getCurrentLevel() + 1, justSans, 20);
        levelText.setPosition(900, 20);

        // May have to be tampered with when/if the Frame Limit changes
        // (the timer measures the runtime of the aplication)
        // + time freeze
        Clock timer = new Clock();

        // creating player for testing - Nathan
        Player mc = new Player(window);

        //creating lives animation
        LivesAnimation livesAnimation = new LivesAnimation(window);
        GameOver gameOver = new GameOver(window);

        while (window.isOpen()) {
            window.draw(spriteBackground);
            if (timer.getElapsedTime().asSeconds() < 59) {
                timerText.setString("Timer: " + Math.round(timer.getElapsedTime().asSeconds()) + "s");
            } else {
                min = (int) timer.getElapsedTime().asSeconds() / 59;
                s = Math.round(timer.getElapsedTime().asSeconds()) - min * 59;
            }
//            window.draw(livesText);
            livesAnimation.redraw();
            slowTimeText.setString("Time slow remaining: " + Math.round(timeSlowInMiliSeconds) + " ms");
            window.draw(slowTimeText);
            window.draw(timerText);
            levelText.setString("Level: " + (ls[curstage].getCurrentLevel() + 1));
            window.draw(levelText);
            for (int i = 0; i < p.length; i++)
            {
                window.draw(p[i].getPlatform());
                p[i].movePlatform();
            }

            if(ls[curstage].checkIfThereIsItem())
            {
                window.draw(ls[curstage].getCurrentItem());
                boolean gotAnItem = false;
                if(mc.onGround(ls[curstage].getXOfCurrentItem(), ls[curstage].getYOfCurrentItem(), ls[curstage].getXOfCurrentItem() + 30, ls[curstage].getYOfCurrentItem() + 30, 4))
                {
                    //if got a vial
                    if(ls[curstage].getItemType() == 0)
                    {
                        if(ls[curstage].dealWithItem())
                        {
                            livesAnimation.changeTextureGainedALife();
                            if(livesTracker != 6)
                            {
                                mc.getALife();
                            }
                            livesTracker = mc.checkOnLives();
                        }


                    }
                    //if got a stopwatch
                    if(ls[curstage].getItemType() == 1)
                    {
                        if(ls[curstage].dealWithItem())
                        {
                            timeSlowInMiliSeconds = 3 * 1000;
                        }
                    }
                }
            }

            boolean check = false;
            for (int i = 0; i < p.length; i++) {
                check = mc.onGround(p[i].getPosX(), p[i].getPosY(), p[i].getBoundX(), p[i].getBoundY(), p[i].getPlatformType());
                if (check) {

                    break;
                }
            }

            window.setKeyRepeatEnabled(false);

            if(mc.getWalking())
            {
                if(mc.getRight()) {
                    mc.walkRightAnimation();
                }
                if(mc.getLeft()){
                    mc.walkLeftAnimation();
                }
            }

            if(mc.getVelX() == 0 && mc.getVelY() == 0)
            {
                if(mc.getPreviousStateR())
                {
                    mc.standRightAnimation();
                }
                else {
                    mc.standLeftAnimation();
                }
            }

            if(!mc.getOnGround())
            {
                if(mc.getPreviousStateR())
                {
                    mc.jumpRightAnimation();
                }
                else{
                    mc.jumpLeftAnimation();
                }
            }

            if(mc.getPosY()>720)
            {
                mc.looseALife();
            }
            if(livesTracker > mc.checkOnLives())
            {
                livesAnimation.changeTextureLostALife();
                livesTracker = mc.checkOnLives();
            }
            if(mc.checkOnLives() == 0)
            {
                window.draw(gameOver.gameOverAnimation());
                pass++;
                if(pass == 3)
                {
                    try{
                        Thread.sleep(5000);
                    }catch(InterruptedException ie)
                    {
                        ie.printStackTrace();
                    }
                    mainMenu.setVisible(true);
                    levelMusic.stop();
                    menuBeat.play();
                    window.close();
                }
            }


            mc.redraw();

            window.display();

            for (Event event : window.pollEvents()) {
                switch (event.type) {
                    case CLOSED:
                        System.out.println("The user pressed the close button!");
                        levelMusic.stop();
                        window.close();

                    case KEY_PRESSED:
                        KeyEvent keyEvent = event.asKeyEvent();

                        //Added to test nixie clock animation

                        if (keyEvent.key == Key.A) {
                            mc.moveLeft(true);
                            mc.setPreviousStateR(false);
                            mc.setWalking(true);
                        }
                        if (keyEvent.key == Key.D) {
                            mc.moveRight(true);
                            mc.setPreviousStateR(true);
                            mc.setWalking(true);
                        }
                        if (keyEvent.key == Key.W) {
                            mc.jump();
                        }
                        //moved from SlowTime class mostly
                        if(keyEvent.key == Key.LSHIFT)
                        {
                            if(timeSlowInMiliSeconds > 0) {
                                if (ensureOnlyOne == 0) {
                                    ensureOnlyOne = 1;
                                    startOfTimeSlow = System.currentTimeMillis();
                                    mc.halfSpeed();
                                    for (int i = 0; i < p.length; i++) {
                                        p[i].halfPlatformSpeed();
                                    }
                                }
                            }
                        }

                    case KEY_RELEASED:
                        keyEvent = event.asKeyEvent();

                        if (keyEvent.key == Key.A) {

                            setKr();
                            if (getkr()) {

                                mc.moveLeft(false);
                                mc.setPreviousStateR(false);
                                mc.setWalking(false);
                            }

                        }
                        if (keyEvent.key == Key.D) {

                            setKr();
                            if (getkr()) {
                                mc.moveRight(false);
                                mc.setPreviousStateR(true);
                                mc.setWalking(false);
                            }

                        }
                        if(keyEvent.key == Key.LSHIFT)
                        {
                            setKrForTimeSlow();
                            finishOfTimeSlow = System.currentTimeMillis();
                            timeSlowInMiliSeconds = timeSlowInMiliSeconds - (finishOfTimeSlow - startOfTimeSlow) + previousElapsed;
                            if(timeSlowInMiliSeconds < 0)
                            {
                                timeSlowInMiliSeconds = 0;
                            }
                            if (getkrForTimeSlow()) {
                                if (ensureOnlyOne == 1) {
                                    ensureOnlyOne = 0;
                                    mc.resetSpeed();
                                    for (int i = 0; i < p.length; i++) {
                                        p[i].resetPlatformSpeed();
                                    }
                                }
                            }
                        }
                }

            }

            mc.setPositionOfPlayer();
            window.clear();

            if(mc.getPosX() + 30 >= 1280 || mc.getPosX() <= 30)
            {
                levelChangeDetection = ls[curstage].changeLevel(mc.getPosX());
//                if(levelChangeDetection && !boss1.isactive && !boss2.isactive)
                if(levelChangeDetection)
                {
                    p = ls[curstage].getLevelPlatforms();

                    mc.setPosX(ls[curstage].getCharcterXPoint());
                    levelChangeDetection = false;
                }
            }

            if (ls[curstage].lastLevel()) {
                if (curstage == 1) {
                    boss1.move();
                    boss1.redraw(window);
                    if (bullet.getBulletPositionX() <= 0) {
                        bullet.setBulletPositionX(boss1.getBossOnePositionX());
                        bullet.setBulletPositionY(boss1.getBossOnePositionY());
                    } else {
                        bullet.shoot(bullet.getBulletPositionX());
                        bullet.redraw(window);
                    }
                }

                 if (curstage == 0) {
                     boss2.move();
                     boss2.redraw(window);
                     if (bullet.getBulletPositionX() <= 0) {
                         bullet.setBulletPositionX(boss1.getBossOnePositionX());
                         bullet.setBulletPositionY(boss1.getBossOnePositionY());
                     } else {
                         bullet.shoot(bullet.getBulletPositionX());
                         bullet.redraw(window);
                     }
                 }
            }
        }
    }

    public static boolean getkr() {
        if (kr == 0) {
            kr = 2;
            return true;
        } else {
            return false;
        }
    }

    public static void setKr() {
        kr = kr - 1;
    }

    public static boolean getkrForTimeSlow() {
        if (krForTimeSlow == 0) {
            krForTimeSlow = 2;
            return true;
        } else {
            return false;
        }
    }

    public static void setKrForTimeSlow() {
        krForTimeSlow = krForTimeSlow - 1;
    }

}