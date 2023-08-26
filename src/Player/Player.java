package Player;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author: Nathan
 * @author: Viltene
 * @version: 21/01/18, last time updated on 23/08/25
 * The main class responsible for player data storage, logic and representation.
 * Takes the window of the game for redrawing.
 * It is used only in GUI.
 * Implemets other classes in the package: PlayerJumpingAnimation,
 * PlayerStanding, PlayerWalkingAnimation
*/
public class Player 
{
    /* Coordinate variables */
    private int posX = 200;
    private int posY = 500;
    private int GBX = 0;
    private int GBY = 0;
    
    private RenderWindow window; // the window passed down from GUI

    /* Logic variables and state */
    private boolean onGround; // Player is on the ground
    private boolean jump = true; // Player is jumping
    private boolean left = false; // Player is facing left
    private boolean right = false; // Player is facing right
    private boolean walking = false; // Player is walking
    private boolean previousStateR = true; // Player was facing right before (only stored here and interacted with through getter and setter)
    
    /* Tracking variables */
    private int tick = 0; // control variable for jumping 
    private int velX = 0; // speed to the right
    private int velY = 0; // speed to the left
    private int groundType = 0; // ground type: 0 - spikes, 1 - ground to stand on, 2 - climbable wall, 3 - filler, 4 - item
    private int groundSubtype = 0; // ground direction: 1 - right, 2 - left
    private int groundFloor = 0; // y coordinate of the platform
    private int groundLeft = 0; // x coordinate of the platform
    private int groundRight = 0; // boundary x of the platform
    private int collSide = 0; // 1 - to the left of the player, 2 - to the right of the player
    private int timerForStanding = 0;
    private int pacerForStanding = 6;
    private int timerForJumping = 0;
    private int pacerForJumping = 5;
    private int lives = 6; // player lives
    
    /* Player representation variables */
    private Sprite player;
    /* Animations */
    private PlayerWalkingAnimation pwa = new PlayerWalkingAnimation();
    private PlayerStanding ps = new PlayerStanding();
    private PlayerJumpingAnimation pja = new PlayerJumpingAnimation();
    /* Images */
    private Image imageR = new Image(); // player basic position facing to the right
    private Image imageL = new Image(); // player basic position facing to the left
    /* Textures */
    private Texture frame = new Texture();

    /**
     * Constructor of the Player
     * @param window the main game window which is passed down from GUI
     */
    public Player(RenderWindow window)
    {
        this.window = window;
        /* Trying to load basic player textures */
        try{
            imageR.loadFromFile(Paths.get("ArtAssets/Base R1.png"));
            imageL.loadFromFile(Paths.get("ArtAssets/Base L1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            frame.loadFromImage(imageR); // Player spawns facing right
        } catch (TextureCreationException e) {
            e.printStackTrace();
        }

        /* Spawn settings: texture, position */
        player = new Sprite(frame);
        player.setOrigin(Vector2f.div(new Vector2f(frame.getSize()), 2));
        player.setPosition(this.posX, this.posY);
        window.draw(player);     
    }

    /**
     * Redraws the sprite
     */
    public void redraw()
    {
        window.draw(this.player);
    }

    /**
     * Checks against game logic and sets new position of the player
     */
    public void setPositionOfPlayer()
    {
        /* adds the velocity to the current position */
        this.posX = this.posX + this.velX;
        this.posY = this.posY + this.velY;

        /* important for sticky keys */
        if (this.velX != 0 && !this.left && !this.right)
        {
            if (velX > 0) {
                this.velX = this.velX - 1;
            }
            if (velX < 0) {
                this.velX = this.velX + 1;
            }
        }
        /* considers jumping, otherwise gravity is too strong*/
        if (this.tick == 3)
        {
            this.tick = 0;
            
            /* if the player is in the air and or jumping */
            if (!onGround || velY < 0) {
                if (velY < 8) {
                    this.velY = 8;
                } else { //faling
                    velY = velY + 1; // otherwise the player is falling
                }
            }
            else if(velY < 0){
                //filler
            }
            else
            {
                //if on the ground
                if (this.groundType == 1)
                {
                    velY = 0; // not falling but can jump
                    this.posY = this.groundFloor - 22;
                }

                // if on spikes
                else if (this.groundType == 0)
                {
                    looseALife();
                }

                //if climable wall
                else if (this.groundType == 2)
                {
                    
                    //if collsion is to the left of the player
                    if (this.collSide == 1)
                    {
                        this.posX = this.groundRight+15;
                        this.velY = 0; // not falling but can jump
                        velX = 1;
                    }
                    //if collided to the right of the player
                    if (this.collSide == 3)
                    {
                        this.posX = this.groundLeft-15;
                        this.velY = 0; // not falling but can jump
                        velX = -1;
                    }
                }
                /* if filler of the wall. This part of the code does not work perfectly*/
                else if (this.groundType == 3)
                {
                    onGround = false; // not on ground
                    if(right)
                    {
                        this.posX = this.groundLeft-40;
                    }
                    if(left)
                    {
                        this.posX = this.groundRight+40;
                    }
                }
            }
        }
        else
        {
            this.tick++;
        }
        setSpritePosition();
    }

    /**
     * Logic to move left
     * @param state true = moving left || false = not moving left
     */
    public void moveLeft(boolean state)
    {
        this.left = state;
        if (this.left)
        {
            if(groundType!=2 || groundType != 3)
            {
                velX = -10;
            }
        }
    }

    /**
     * Logic to move right
     * @param state true = moving right || false = not moving right
     */
    public void moveRight(boolean state)
    {
        this.right = state;
        if (this.right)
        {
            if(groundType!=2 || groundType != 3)
            {
                velX = +10;
            }
        }
    }

    /**
     * Setter for the value if the player was facing right before
     * @param tf true = the player was looking right || false = the player was looking left
     */
    public void setPreviousStateR(boolean tf)
    {
        this.previousStateR = tf;
    }

    /**
     * Getter of the value if the player was facing right before
     * @return tf true = the player was looking right || false = the player was looking left
     */
    public boolean getPreviousStateR()
    {
        return previousStateR;
    }

    /**
     * Jumping logic
     */
    public void jump()
    {
        if (this.onGround)
        {
            this.jump = true;
            this.velY = -10;
            this.tick = -7;
        }
        else if (this.jump == true)
        {
            this.velY = -10;
            this.jump = false;
            this.tick = -7;
        }
    }

    /**
     * Collision detection
     * @param x1 platform x coordinate
     * @param y1 platform y coordinate
     * @param x2 platform ending x coordinate
     * @param y2 platform ending y coordinate
     * @param type platform type
     * @param subtype platform subtype (direction)
     * @see <a href= "resources/structureOfTheLevelFiles.txt"> More information on types </a> 
     * @return true = the player is on the ground || false = the player is not on the ground
      */
    public boolean onGround(int x1, int y1, int x2, int y2, int type, int subtype)
    {
        this.getGlobalBounds();
        
        if(this.posX <= x2+20 && this.GBX >= x1+10 && this.posY <= y2 && this.GBY >= y1)
        {
            this.onGround = true;
            this.groundFloor = y1;
            this.groundLeft = x1;
            this.groundRight = x2;
            this.groundType = type;
            this.groundSubtype = subtype;
            if (this.groundSubtype == 1)
            {
                collSide = 1;
            }
            if(this.groundSubtype == 2)
            {
                collSide = 3;
            }
        }
        else
        {
            this.onGround = false;
        }
        return this.onGround;
    }

    /**
     * Getter of the player x position
     * @return x position of the player
     */
    public int getPosX()
    {
        return posX;
    }

    /**
     * Getter of the player y position
     * @return y position of the player
     */
    public int getPosY()
    {
        return posY;
    }

    /**
     * Setter for the player x position
     * @param x position of the player
     */
    public void setPosX(int x)
    {
        posX = x;
    }

    /**
     * Setter of the player y position
     * @param y position of the player
     */
    public void setPosY (int y)
    {
        posY = y;
    }


    /**
     * Sets current x and y positions as the position of the sprite
     */
    public void setSpritePosition()
    {
        player.setPosition(new Vector2f(posX, posY));
    }

    /**
     * Calculates the end points of the sprite on x and y axis
     */
    public void getGlobalBounds()
    {
        this.GBX = this.posX + 32;
        this.GBY = this.posY + 51;
    }

    /**
     * Getter of end point of the sprite on x axis
     * @return end x of the player sprite
     */
    public int getGlobalX()
    {
        getGlobalBounds();
        return GBX;
    }

    /**
     * Getter of end point of the sprite on y axis
     * @return end y of the player sprite
     */
    public int getGlobalY()
    {
        getGlobalBounds();
        return GBY;
    }

    /**
     * Getter of velocity on x axis
     * @return player velocity on x axis
     */
    public int getVelX()
    {
        return  velX;
    }

    /**
     * Getter of velocity on y axis
     * @return player velocity on y axis
     */
    public int getVelY()
    {
        return velY;
    }

    /**
     * Sets the texture for walking right
     */
    public void walkRightAnimation()
    {
        player.setTexture(pwa.getWalkingRightTexture());
        player.setTextureRect(pwa.getCurrentFrameWalkingRight());
    }

    /**
     * Sets the texture for walking left
     */
    public void walkLeftAnimation()
    {
        player.setTexture(pwa.getWalkingLeftTexture());
        player.setTextureRect(pwa.getCurrentFrameWalkingLeft());
    }

    /**
     * Sets the texture for standing facing right
     */
    public  void standRightAnimation()
    {
        timerForStanding++;
        if(timerForStanding >= pacerForStanding) {
            player.setTexture(ps.getStandingRightTexture());
            player.setTextureRect(ps.getCurrentFrameStandingRight());
            timerForStanding = 0;
        }
    }

    /**
     * Sets the texture for standing facing left
     */
    public  void standLeftAnimation()
    {
        timerForStanding++;
        if(timerForStanding >= pacerForStanding) {
            player.setTexture(ps.getStandingLeftTexture());
            player.setTextureRect(ps.getCurrentFrameStandingLeft());
            timerForStanding = 0;
        }
    }

    /**
     * Sets the texture for jumping to the right
     */
    public  void jumpRightAnimation()
    {
        timerForJumping++;
        if(timerForJumping >= pacerForJumping) {
            player.setTexture(pja.getJumpRightTexture());
            player.setTextureRect(pja.getCurrentFrameJumpingRight());
            timerForJumping = 0;
        }
    }

    /**
     * Sets the texture for jumping to the left
     */
    public  void jumpLeftAnimation()
    {
        timerForJumping++;
        if(timerForJumping >= pacerForJumping) {
            player.setTexture(pja.getJumpLeftTexture());
            player.setTextureRect(pja.getCurrentFrameJumpingLeft());
            timerForJumping = 0;
        }
    }

    /**
     * Sets the basic texture of the player looking to the right 
     */
    public void returnToBasicPositionLookinRight()
    {
        Texture basicPosition = new Texture();
        try {
            basicPosition.loadFromImage(imageR);
        } catch (TextureCreationException e) {
            e.printStackTrace();
        }
        player.setTexture(basicPosition);
    }

    /**
     * Sets the basic texture of the player looking to the right 
     */
    public void returnToBasicPositionLookinLeft()
    {
        Texture basicPosition = new Texture();
        try {
            basicPosition.loadFromImage(imageL);
        } catch (TextureCreationException e) {
            e.printStackTrace();
        }
        player.setTexture(basicPosition);
    }

    /**
     * Getter for boolean if player is facing right
     * @return true = player is facing right || false = player is not facing right
     */
    public boolean getRight()
    {
        return right;
    }

    /**
     * Getter for boolean if player is facing left
     * @return true = player is facing left || false = player is not facing left
     */
    public boolean getLeft()
    {
        return left;
    }

    /**
     * Getter for boolean if the player is walking
     * @return true = player is walking || false = player is not walking
     */
    public boolean getWalking()
    {
        return walking;
    }

    /**
     * Setter for boolean if the player is walking
     * @param tf true = player is walking || false = player is not walking
     */
    public void setWalking(boolean tf)
    {
        walking = tf;
    }

    
    /**
     * Getter of boolean if player is on the ground
     * @return true = the player is on the ground || false = player is not on the ground
     */
    public boolean getOnGround()
    {
        return onGround;
    }

    /**
     * Getter for boolean if player is jumping
     * @return true = the player is jumping || false = player is not jumping
     */
    public boolean getJumpState()
    {
        return jump;
    }

    /**
     * Deals with logic of player loosing a life
     */
    public void looseALife()
    {
        lives = lives - 1;
        posX = 100;
        posY = 100;
    }

    /**
     * Deals with logic if player has gained a life
     */
    public void getALife()
    {
        lives++;
    }

    /**
     * Getter for remaining player lives
     * @return number of lives left
     */
    public int checkOnLives()
    {
        return lives;
    }

    /**
     * Cuts player speed in half
     * Used to implement slow mechanics
     */
    public void halfSpeed()
    {
        velX = velX / 2;
        velY = velY / 2;
        pwa.increasePacer();
        pacerForStanding = pacerForStanding * 2;
        pacerForJumping = pacerForJumping * 2;
    }

    /**
     * Resets player speed back to normal
     * Used to come back to normal after slow mechanics
     */
    public void resetSpeed()
    {
        velX = velX * 2;
        velY = velY * 2;
        pwa.decreasePacer();
        pacerForStanding = pacerForStanding / 2;
        if(pacerForStanding > timerForStanding)
        {
            timerForStanding = 0;
        }
        pacerForJumping = pacerForJumping / 2;
        if(pacerForJumping > timerForJumping)
        {
            timerForJumping = 0;
        }
    }

    /**
     * Makes the player sprite bigger. Used only for the victory animation.
     */
    public void resizeTheSprite()
    {
        player.scale(new Vector2f(2, 2));
    }
}
