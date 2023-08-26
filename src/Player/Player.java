//author Nathan
//the main player class

package Player;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.io.IOException;
import java.nio.file.Paths;


public class Player 
{
    private int posX = 320;
    private int posY = 240;
    private RenderWindow window;
    private int GBX = 0;
    private int GBY = 0;
    private boolean onGround;
    private boolean jump = true;
    private int tick = 0;
    private int velX = 0;
    private int velY = 0;
    private int moveVar = 0;
    private int groundType = 0;
    private int groundFloor = 0;
    private int groundBot = 0;
    private int groundLeft = 0;
    private int groundRight = 0;
    private int collSide = 0; //0 up, 1 right 2 down 3 lft
    private Sprite player;
    private boolean left = false;
    private boolean right = false;
    private boolean walking = false;
    //Sorry for this Nathan! Just trying to make the ends meet
    private Image imageR = new Image();
    private Image imageL = new Image();
    private PlayerWalkingAnimation pwa = new PlayerWalkingAnimation();
    private PlayerStanding ps = new PlayerStanding();
    private PlayerJumpingAnimation pja = new PlayerJumpingAnimation();
    private int timerForStanding = 0;
    private int pacerForStanding = 6;
    private int timerForJumping = 0;
    private int pacerForJumping = 5;
    private boolean previousStateR = true;
    private boolean isCurrentlyJumping = false;
    private int lives = 6;

    public Player(RenderWindow window)
    {
        this.window = window;
        try{
            imageR.loadFromFile(Paths.get("ArtAssets/Base R1.png"));
            imageL.loadFromFile(Paths.get("ArtAssets/Base L1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Again sorry
//        Texture frame = loadTexture("Player/resources/Base R1.png");
        Texture frame = null;
        try {
            frame = loadTexture(imageR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player = new Sprite(frame);

        player.setOrigin(Vector2f.div(new Vector2f(frame.getSize()), 2));
        player.setPosition(this.posX, this.posY);

        window.draw(player);
        
    }

	public void sayHi()
    {
        System.out.println("hi");
    }

    public void redraw()
    {
        window.draw(this.player);
    }

    //and then again sorry
//    public Texture loadTexture(String texturePath)
    public Texture loadTexture(Image image) throws IOException {
    Texture frame = new Texture();

        //another sorry notice
//        frame.loadFromFile(Paths.get(texturePath));
        try {
            frame.loadFromImage(image);
        } catch (TextureCreationException e) {
            e.printStackTrace();
        }

        Vector2i size = frame.getSize();

        return frame;
    }

//    public void setIcon(String path)
    public void setIcon(Image image)
    {

        Texture tex = null;
        try {
            tex = loadTexture(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.player.setTexture(tex);
    }

    public void setPositionOfPlayer()
    {

        this.posX = this.posX + this.velX;
        this.posY = this.posY + this.velY;

        if (this.velX != 0 && !this.left && !this.right)
        {
            if (velX > 0) {
                this.velX = this.velX - 1;
            }
            if (velX < 0) {
                this.velX = this.velX + 1;
            }
        }
        if (this.tick == 3)
        {
            this.tick = 0;

            if (!onGround || velY < 0) {
                if (velY < 8) {
                    this.velY = 8;
                } else {
                    velY = velY + 1;
                }
            }
            else if(velY < 0){
                this.velY = this.velY;
            }
            else
            {
                if (this.groundType == 1)
                {
                    velY = 0;
                    this.posY = this.groundFloor - 28;
                }

                else if (this.groundType == 0)
                {
                    looseALife();
                }

                else if (this.groundType == 2)
                {
                    if (this.collSide == 1)
                    {

                        this.posX = this.groundLeft -15;

                        this.velY = 0;
                    }
                    if (this.collSide == 3)
                    {
                        this.posX = this.groundRight;
                        this.velY = 0;
                    }
                }
                else if (this.groundType == 3)
                {
                    if (this.collSide == 2)
                    {
                        System.out.println("bot collision");
                        this.posY = this.groundBot;
                    }
                }

            }
        }
        else{
            this.tick++;
        }
        this.player.setPosition(this.posX, this.posY);
    }

    public void moveLeft(boolean state)
    {
        this.left = state;
        System.out.println(this.left);
        if (this.left)
        {
            setIcon(imageL);
            velX = -10;
        }


    }

    public void moveRight(boolean state)
    {
        this.right = state;
        if (this.right)
        {
            velX = +10;
        }
    }


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

    public boolean onGround(int x1, int y1, int x2, int y2, int type)
    {
        this.getGlobalBounds();

        if (this.posX >= x2 || x1 >= this.GBX)
        {
            this.onGround = false;
            return false;
        }

        else if (this.posY >= y2 || y1 >= this.GBY)
        {
            this.onGround = false;
            return false;
        }

        if (x1 <= this.GBX && (this.posY <= y2 || y1 <= this.GBY) )
        {

            this.collSide = 1;
        }
        else if (this.posX < x1 && (this.posY <= y2 || y1 <= this.GBY) )
        {

            this.collSide = 3;
        }
        else if (this.posY >= y2)
        {
            this.collSide = 2;
        }
        else if (y1 <= this.GBY)
        {
            this.collSide = 0;
        }

        this.onGround = true;
        this.groundFloor = y1;
        this.groundBot = y2;
        this.groundLeft = x1;
        this.groundRight = x2;
        this.groundType = type;
        return true;
    }

    public boolean onGround()
    {
        return true;
    }

    public int getPosX()
    {
        return posX;
    }
    public void setPosX(int x)
    {
        posX = x;
    }
    public int getPosY()
    {
        return posY;
    }
    public int getVelX()
    {
        return  velX;
    }
    public int getVelY()
    {
        return velY;
    }

    public void walkRightAnimation()
    {
        player.setTexture(pwa.getWalkingRightTexture());
        player.setTextureRect(pwa.getCurrentFrameWalkingRight());
    }

    public void walkLeftAnimation()
    {
        player.setTexture(pwa.getWalkingLeftTexture());
        player.setTextureRect(pwa.getCurrentFrameWalkingLeft());
    }

    public  void standRightAnimation()
    {
        timerForStanding++;
        if(timerForStanding >= pacerForStanding) {
            player.setTexture(ps.getStandingRightTexture());
            player.setTextureRect(ps.getCurrentFrameStandingRight());
            timerForStanding = 0;
        }
    }

    public  void standLeftAnimation()
    {
        timerForStanding++;
        if(timerForStanding >= pacerForStanding) {
            player.setTexture(ps.getStandingLeftTexture());
            player.setTextureRect(ps.getCurrentFrameStandingLeft());
            timerForStanding = 0;
        }
    }

    public  void jumpRightAnimation()
    {
        timerForJumping++;
        if(timerForJumping >= pacerForJumping) {
            player.setTexture(pja.getJumpRightTexture());
            player.setTextureRect(pja.getCurrentFrameJumpingRight());
            timerForJumping = 0;
        }
    }

    public  void jumpLeftAnimation()
    {
        timerForJumping++;
        if(timerForJumping >= pacerForJumping) {
            player.setTexture(pja.getJumpLeftTexture());
            player.setTextureRect(pja.getCurrentFrameJumpingLeft());
            timerForJumping = 0;
        }
    }

    public void getGlobalBounds()
    {
        this.GBX = this.posX + 35;
        this.GBY = this.posY + 58;
    }

    public void returnToBasicPositionLookinRight()
    {
        setIcon(imageR);
    }

    public boolean getRight()
    {
        return right;
    }

    public boolean getLeft()
    {
        return left;
    }

    public boolean getWalking()
    {
        return walking;
    }

    public void setWalking(boolean tf)
    {
        walking = tf;
    }

    public boolean getOnGround()
    {
        return onGround;
    }


    public void setPreviousStateR(boolean tf)
    {
        previousStateR = tf;
    }
    public boolean getPreviousStateR()
    {
        return previousStateR;
    }

    public void looseALife()
    {
        lives = lives - 1;
        posX = 15;
        posY = 0;
    }

    public void getALife()
    {
        lives++;
    }

    public int checkOnLives()
    {
        return lives;
    }

    public void halfSpeed()
    {
        velX = velX / 2;
        velY = velY / 2;
        pwa.increasePacer();
        pacerForStanding = pacerForStanding * 2;
        pacerForJumping = pacerForJumping * 2;
    }

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

}
