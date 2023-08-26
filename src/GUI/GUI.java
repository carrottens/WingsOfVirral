package GUI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.swing.JFrame;

import org.jsfml.audio.Music;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;
import org.jsfml.system.Clock;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import Boss.Boss;
import Boss.BossOne;
import Boss.BossTwo;
import Boss.Bullet;
import Levels.LevelSet;
import Levels.Platform;
import Player.Player;

/**
 * @author: Viltene 
 * @author: Nathan
 * @version: last reviewed on 26/08/23
 * 
 * GUI mostly uses jsfml library.
 * It is responsible for putting together (ducktaping together) the level, game and player logic
 * and displaying different animations
 */
public class GUI 
{
    private static int noS = 4; // number of stages
    private static int kr = 2; // pacer for player movement
    private static int krForTimeSlow = 2; // pacer for player movement
    
    private Image backgr = new Image();

    public GUI(int skin, JFrame mainMenu, Music menuBeat, Music levelMusic) {

        // Primary initialisation
        int min = 0;
        int s = 0;
        long startOfTimeSlow = 0;
        long finishOfTimeSlow = 0;
        long previousElapsed = 0;
        int livesTracker = 6; // tracks how many lives are left for the player
        int pass = 0; // pacer for game over graphics
        float timeSlowInMiliSeconds = 3 * 1000;
        int ensureOnlyOne = 0; // makes sure that only one time slow is registered at the time
        boolean levelChangeDetection = false;
        
        int curstage = skin; // holds the player's choice of aesthetics

        // Files for reading data on items and platforms
        File lvl1Plat = new File("src/Levels/resources/Level1P.txt");
        File lvl1Item = new File("src/Levels/resources/Items1.txt");
       
        // There are 8 different art assets in the level set
        Image lvl1Im[] = new Image[8];
        for (int i = 0; i < 8; i++)
        {
            lvl1Im[i] = new Image();
        }
        try {
            lvl1Im[0].loadFromFile(Paths.get("ArtAssets/spikes up 1.png"));
            lvl1Im[1].loadFromFile(Paths.get("ArtAssets/spikes L 1.png"));
            lvl1Im[2].loadFromFile(Paths.get("ArtAssets/spikes D 1.png"));
            lvl1Im[3].loadFromFile(Paths.get("ArtAssets/spikes R 1.png"));
        }catch(IOException ex) {
            ex.printStackTrace();
        }

        // Set background and remaining textures depending on the player's choice
        Texture back = new Texture();
        if(curstage == 1)
        {
            try {
                backgr.loadFromFile(Paths.get("ArtAssets/background_lab.jpg"));
                lvl1Im[4].loadFromFile(Paths.get("ArtAssets/snow top 1.png"));
                lvl1Im[5].loadFromFile(Paths.get("ArtAssets/snow side R 1.png"));
                lvl1Im[6].loadFromFile(Paths.get("ArtAssets/snow side L 1.png"));
                lvl1Im[7].loadFromFile(Paths.get("ArtAssets/snow mid 1.png"));
            }catch(IOException ex) {
                ex.printStackTrace();
            }
        }
        if(curstage == 0)
        {
            try {
                backgr.loadFromFile(Paths.get("ArtAssets/mannor_background.jpg"));
                lvl1Im[4].loadFromFile(Paths.get("ArtAssets/wood top 1.png"));
                lvl1Im[5].loadFromFile(Paths.get("ArtAssets/wood side R 1.png"));
                lvl1Im[6].loadFromFile(Paths.get("ArtAssets/wood side L 1.png"));
                lvl1Im[7].loadFromFile(Paths.get("ArtAssets/wood mid 1.png"));
                
            }catch(IOException ex) {
                ex.printStackTrace();
            }
        }
        // load the texture for background
        try {
            back.loadFromImage(backgr);
        }catch (TextureCreationException tce)
        { 
            tce.printStackTrace();
        }
        /* Create the background */
        Sprite spriteBackground = new Sprite();
        spriteBackground.setTexture(back);
        spriteBackground.setPosition(0,0);
        
        /* Music settings */
        levelMusic.setVolume(30);
        levelMusic.setLoop(true);
        levelMusic.play();

        // Define the level set
        LevelSet ls = new LevelSet(noS, lvl1Plat, lvl1Item, lvl1Im);
        // Create platforms
        Platform p[] = ls.getLevelPlatforms();

        // Start the window
        RenderWindow window = new RenderWindow();
        /* window settings */
        window.create(new VideoMode(1280, 720), "Wings of Virral");
        Image icon = new Image();
        try {
            icon.loadFromFile(Paths.get("ArtAssets/artTest.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.setIcon(icon);
        window.setFramerateLimit(30);
        window.setKeyRepeatEnabled(false);

        /* Create the font */
        Font baskerville = new Font();
        try {
            baskerville.loadFromFile(Paths.get("src/GUI/resources/Baskervville-Regular.ttf"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        /* Basic information for the player */
        Text livesText = new Text("Lives:", baskerville, 20);
        livesText.setPosition(0, 0);
        Text slowTimeText = new Text("Time slow remaining: 3 s", baskerville, 20);
        slowTimeText.setString("");
        slowTimeText.setPosition(0, 40);
        Text timerText = new Text("Timer: 0", baskerville, 20);
        timerText.setPosition(900, 0);
        Text levelText = new Text("Level: " + ls.getCurrentLevel() + 1, baskerville, 20);
        levelText.setPosition(900, 20);
        Text bossLivesText = new Text("Boss lives: 3", baskerville, 20);
        bossLivesText.setPosition(600, 100);
        Clock timer = new Clock(); // The timer measures the runtime of the aplication  + time freeze

        // creating the main character
        Player mc = new Player(window);

        // Creating lives animation
        LivesAnimation livesAnimation = new LivesAnimation(window);

        // Game Over animation
        GameOver gameOver = new GameOver();
        VictoryAnimations victoryA = new VictoryAnimations(curstage);

        // Boss creation
        Boss boss;
        if(curstage == 1)
        {
            boss = new BossOne(); 
        }
        else
        {
            boss = new BossTwo();
        }
        
        Bullet bullet[] = new Bullet[4];
        int activeBullets = 0;
        for (int i = 0; i < 4; i++)
        {
            bullet[i] = new Bullet(850, 360, curstage);
        }

        /* The main game loop*/
        while (!boss.checkIfWon() && window.isOpen()) 
        {
            window.draw(spriteBackground); //redraws background

            /* Draw level platforms*/
            for (int i = 0; i < p.length; i++)
            {
                window.draw(p[i].getPlatform());
                p[i].movePlatform();
            }
            
            /* Time tracking and display */
            if (timer.getElapsedTime().asSeconds() < 59) 
            {
                timerText.setString("Timer: " + Math.round(timer.getElapsedTime().asSeconds()) + "s");
            } 
            else 
            {
                min = (int) timer.getElapsedTime().asSeconds() / 59;
                s = Math.round(timer.getElapsedTime().asSeconds()) - min * 59;
            }
            slowTimeText.setString("Time slow remaining: " + Math.round(timeSlowInMiliSeconds) + " ms");
            window.draw(slowTimeText);
            window.draw(timerText);
            
            /* Redraw lives tracking */
            window.draw(livesText);
            livesAnimation.redraw();

            /* Display current level */
            levelText.setString("Level: " + (ls.getCurrentLevel() + 1));
            window.draw(levelText);
            
            /* Game logic concerning items */
            if(ls.checkIfThereIsItem())
            {
                window.draw(ls.getCurrentItem());
                /* Collision detection */
                if(mc.onGround(ls.getXOfCurrentItem(), ls.getYOfCurrentItem(), ls.getXOfCurrentItem() + 30, ls.getYOfCurrentItem() + 30, 4, 0))
                {
                    /* if collided with a vial */
                    if(ls.getItemType() == 0)
                    {
                        if(ls.dealWithItem()) // no longer display
                        {
                            livesAnimation.changeTextureGainedALife(); // refresh image representation
                            if(livesTracker != 6)
                            {
                                mc.getALife(); // add an extra life to the life tracker
                            }
                            livesTracker = mc.checkOnLives(); // update the local variable
                        }
                    }
                    /* if got a stopwatch */
                    if(ls.getItemType() == 1)
                    {
                        if(ls.dealWithItem()) // no longer diplay
                        {
                            timeSlowInMiliSeconds = 3 * 1000; // fill the time slow meter
                        }
                    }
                }
            }

            /* Search for a player collision with a platform */
            boolean check = false;
            for (int i = 0; i < p.length; i++) {
                check = mc.onGround(p[i].getPosX(), p[i].getPosY(), p[i].getBoundX(), p[i].getBoundY(), p[i].getPlatformType(), p[i].getDirection());
                if (check) 
                {
                    break; // collision found -> break (it is dealt with internally in the Player class)
                }
            }
            
            /* checks if the player is walking and takes care of the animation */
            if(mc.getWalking())
            {
                if(mc.getRight()) 
                {
                    mc.walkRightAnimation();
                }
                if(mc.getLeft())
                {
                    mc.walkLeftAnimation();
                }
            }

            /* checks if the player is standing and takes care of the animation */
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

            /* Takes care of the jumping animation */
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

            /* if falls lower than the screen resolution, then player looses a life*/
            if(mc.getPosY()>720)
            {
                mc.looseALife();
            }
            if(livesTracker > mc.checkOnLives()) // takes care of the representation accordingly
            {
                livesAnimation.changeTextureLostALife();
                livesTracker = mc.checkOnLives();
            }
            /* if player runs out of lives -> game over */
            if(mc.checkOnLives() == 0) // if runs out of lifes
            {
                window.draw(gameOver.gameOverAnimation()); // play game over
                pass++;
                if(pass == 3)
                {
                    try{
                        Thread.sleep(5000);
                    }catch(InterruptedException ie)
                    {
                        ie.printStackTrace();
                    }
                    mainMenu.setVisible(true); // return to main menu
                    levelMusic.stop();
                    menuBeat.play();
                    window.close();
                }
            }
            
            // redraw the player
            mc.redraw();

            /* Boss part of the game */
            if (ls.lastLevel()) 
            {
                window.draw(bossLivesText);
                /* Collision detection with the boss */
                if(mc.getPosX() <= boss.getGlobalX() && mc.getGlobalX() >= boss.getPositionX() && mc.getPosY() <= boss.getGlobalY() && mc.getGlobalY() >= boss.getGlobalY() && !boss.getHurt())
                {
                    boss.looseALife();
                    bossLivesText = new Text("Boss lives: " + boss.getNumberOfLives(), baskerville, 20);
                    bossLivesText.setPosition(600, 100);
                }
                boss.move();
                boss.redraw(window);
                /* If the boss decided to shoot then try to activate a bullet */
                if (boss.getShooting()) 
                {
                    if(activeBullets < 4) // if not at the limit
                    {
                        for(int i = 0; i < 4; i++)
                        {
                            if(!bullet[i].getActive()) // if found not an active bullet
                            {
                                bullet[i].setActive(true);
                                activeBullets++;
                                bullet[i].setBulletPositionX(boss.getPositionX()); // makes it look like they come from the boss
                                bullet[i].setBulletPositionY(boss.getPositionY()+50); // makes it look like they come from the bos
                                break;
                            }
                        }
                    }
                }
                    
                /* This block is responsible for bullet logic */
                for(int i = 0; i<4; i++)
                {
                    /* Desactivate a bullet if it goes off screen */
                    if(bullet[i].getBulletPositionX() <= 0)
                    {
                        bullet[i].setActive(false);
                        activeBullets--;
                    }
                    /* For every active bullet check if it has collided with the player */
                    if(bullet[i].getActive())
                    {
                        if(mc.getPosX() <= bullet[i].getBulletPositionX()+40 && mc.getGlobalX() >= bullet[i].getBulletPositionX() && mc.getPosY() <= bullet[i].getBulletPositionY()+30 && mc.getGlobalY() >= bullet[i].getBulletPositionY())
                        {
                            mc.looseALife();
                            bullet[i].setActive(false);
                        }
                        bullet[i].shoot(bullet[i].getBulletPositionX());
                        bullet[i].redraw(window);
                    }
                }
            }

            window.display();

            // Respnsible for events
            for (Event event : window.pollEvents()) 
            {
                // window is closed
                switch (event.type) 
                {
                    case CLOSED:
                        levelMusic.stop();
                        window.close();
                    // key is pressed
                    case KEY_PRESSED:
                        KeyEvent keyEvent = event.asKeyEvent();
                        //ESCAPE
                        if (keyEvent.key == Key.ESCAPE) {
                            levelMusic.stop();
                            window.close();
                        }
                        // A
                        if (keyEvent.key == Key.A) {
                            mc.moveLeft(true);
                            mc.setPreviousStateR(false);
                            mc.setWalking(true);
                        }
                        // D
                        if (keyEvent.key == Key.D) {
                            mc.moveRight(true);
                            mc.setPreviousStateR(true);
                            mc.setWalking(true);
                        }
                        // W
                        if (keyEvent.key == Key.W) {
                            mc.jump();
                        }
                        // Shift
                        if(keyEvent.key == Key.LSHIFT)
                        {
                            /* Time slow activation */
                            if(timeSlowInMiliSeconds > 0) {
                                if (ensureOnlyOne == 0) {
                                    ensureOnlyOne = 1;
                                    startOfTimeSlow = System.currentTimeMillis();
                                    mc.halfSpeed();
                                    boss.halfSpeed();
                                    for (int i = 0; i < p.length; i++) 
                                    {
                                        p[i].halfPlatformSpeed();
                                    }
                                    for (int i = 0; i < 4; i++)
                                    {
                                        bullet[i].timeSlow();
                                    }
                                }
                            }
                        }

                    // if key is released
                    case KEY_RELEASED:
                        keyEvent = event.asKeyEvent();
                        // A
                        if (keyEvent.key == Key.A) {
                            setKr();
                            if (getkr()) {

                                mc.moveLeft(false);
                                mc.setPreviousStateR(false);
                                mc.setWalking(false);
                            }
                        }
                        // D
                        if (keyEvent.key == Key.D) {
                            setKr();
                            if (getkr()) {
                                mc.moveRight(false);
                                mc.setPreviousStateR(true);
                                mc.setWalking(false);
                            }
                        }
                        // Left Shift
                        if(keyEvent.key == Key.LSHIFT)
                        {
                            /* Reset after the time slow */
                            setKrForTimeSlow();
                            finishOfTimeSlow = System.currentTimeMillis();
                            timeSlowInMiliSeconds = timeSlowInMiliSeconds - (finishOfTimeSlow - startOfTimeSlow) + previousElapsed;
                            if(timeSlowInMiliSeconds < 0)
                            {
                                timeSlowInMiliSeconds = 0;
                            }
                            if (getkrForTimeSlow()) 
                            {
                                if (ensureOnlyOne == 1) 
                                {
                                    ensureOnlyOne = 0;
                                    mc.resetSpeed();
                                    boss.resetSpeed();
                                    for (int i = 0; i < p.length; i++) 
                                    {
                                        p[i].resetPlatformSpeed();
                                    }
                                    for (int i = 0; i < 4; i++)
                                    {
                                        bullet[i].resetSpeed();
                                    }
                                }
                            }
                        }
                }
            }

            mc.setPositionOfPlayer();
            window.clear();

            // dealing with level change detection
            if(mc.getPosX() + 30 >= 1280 || mc.getPosX() <= 30)
            {
                levelChangeDetection = ls.changeLevel(mc.getPosX());
                if(levelChangeDetection)
                {
                    p = ls.getLevelPlatforms();
                    mc.setPosX(ls.getCharcterXPoint());
                    levelChangeDetection = false;
                }
            }
        } //end of the big while loop

        /* If boss is dead -> win */
        if(boss.checkIfWon())
        {
            /* Prepare player sprite for the final animation */
            mc.resizeTheSprite();
            mc.setPosX(700);
            mc.setPosY(415);
            mc.setSpritePosition();
            int jumpingExtra = 150;
            mc.returnToBasicPositionLookinLeft();
            /* Prepare victory text */
            Text victoryText = new Text("Victory!", baskerville, 200);
            victoryText.setPosition(275, 150);
            /* Playing of the Animation */
            for(int i = 0; i<65; i++) // bogus time measure
            {
                window.draw(spriteBackground);
                window.draw(victoryText);
                /* Y position pacer */
                if(i%4==0)
                {
                    mc.setPosY(mc.getPosY()-jumpingExtra/2);
                }
                else if (i%2 == 0)
                {
                    mc.setPosY(mc.getPosY()-jumpingExtra/2);
                }
                else
                {
                    mc.setPosY(mc.getPosY()+jumpingExtra/2);
                }
                mc.setSpritePosition();
                mc.jumpLeftAnimation();
                mc.redraw();
                window.draw(victoryA.winTheGame());
                window.display();
                try{
                    Thread.sleep(200); //another pacer
                }catch(InterruptedException ie)
                {
                    ie.printStackTrace();
                }
                window.clear();
            }
            mainMenu.setVisible(true); // return to main menu
            levelMusic.stop();
            menuBeat.play();
            window.close();
        }
    }

    /* Internal methods */
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