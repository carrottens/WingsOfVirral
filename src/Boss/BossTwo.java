package Boss;

import org.jsfml.graphics.Image;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author: Yujie
 * @author: Viltene
 * @version: 21-02-12, last updated on 23-08-26
 * This class extends Boss class and is responsible for
 * the boss that appears in the mannor stage
 */
public class BossTwo extends Boss 
{
    //animation variables initialisation
    private int idleTracker;
    private int hurtingAnimationTracker;
    private Texture iR;
    private Image idleRight;
    private Texture iL;
    private Image idleLeft;
    private Image shootSheet;
    private Texture shootingAnimation;
    private Image hurtingRight;
    private Image hurtingLeft;
    private Texture hR;
    private Texture hL;
    private int currentShootingFrame;
    private int animationPacer;

    public BossTwo() 
    {
        /* Initialising basic parameters */
        super.widthOfSprite = 128;
        super.heightOfSprite = 128;
        super.boss = createABoss(widthOfSprite, heightOfSprite, positionX, positionY);

        /* Variables associated with animations */
        idleTracker = 0;
        hurtingAnimationTracker = 0;
        animationPacer = 0;
        currentShootingFrame = 640;
        /* Images */
        idleRight = new Image();
        idleLeft = new Image();
        shootSheet = new Image();
        hurtingRight = new Image();
        hurtingLeft = new Image();
        try {
            idleRight.loadFromFile(Paths.get("ArtAssets/Countess_Vampire/IdleR.png"));
            idleLeft.loadFromFile(Paths.get("ArtAssets/Countess_Vampire/IdleL.png"));
            shootSheet.loadFromFile(Paths.get("ArtAssets/Countess_Vampire/Attack.png"));
            hurtingRight.loadFromFile(Paths.get("ArtAssets/Countess_Vampire/HurtR.png"));
            hurtingLeft.loadFromFile(Paths.get("ArtAssets/Countess_Vampire/HurtL.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* Textures */
        iR = new Texture();
        iL = new Texture();
        shootingAnimation = new Texture();
        hR = new Texture();
        hL = new Texture();
        try{
            iL.loadFromImage(idleLeft, new IntRect(0, 0, widthOfSprite, heightOfSprite));
            shootingAnimation.loadFromImage(shootSheet, new IntRect(currentShootingFrame, 0, widthOfSprite, heightOfSprite));
            hR.loadFromImage(hurtingRight, new IntRect(0, 0, widthOfSprite, heightOfSprite));
            hL.loadFromImage(hurtingLeft, new IntRect(256-widthOfSprite, 0, widthOfSprite, heightOfSprite));
        }catch (TextureCreationException tce){
            tce.printStackTrace();
        }

        super.boss.setTexture(iL); //set primary texture
        super.right = false;
    }

    /**
     * Implements the shooting mechanic and animation
    */
    @Override
    public void shoot() {
        if(!shooting && !hurting) //if not shooting or hurting then could shoot
        {
            int rand = random.nextInt(1000);
            if (rand >= 980)
            {
                super.shooting = true; // decided to shoot
                super.boss.setTexture(shootingAnimation);
            }
        }
        
        /* Shooting animation frame to frame */
        if(animationPacer==2 && shooting)
        {
            if(currentShootingFrame - widthOfSprite >= 0 - widthOfSprite)
            {
                try{
                    shootingAnimation.loadFromImage(shootSheet, new IntRect(currentShootingFrame, 0, widthOfSprite, heightOfSprite));
                }catch (TextureCreationException tce){
                tce.printStackTrace();}
                super.boss.setTexture(shootingAnimation);
                currentShootingFrame = currentShootingFrame - widthOfSprite;
                animationPacer = 0;
            }
            else{
                super.shooting = false;
                animationPacer = 0;
                currentShootingFrame = 640;
                try{
                    shootingAnimation.loadFromImage(shootSheet, new IntRect(currentShootingFrame, 0, widthOfSprite, heightOfSprite));
                }catch (TextureCreationException tce){
                tce.printStackTrace();}
            }
        }

        if(shooting)
        {
            animationPacer++;
        }    
    }

    /**
     * Implementation of idle/floating animation
     */
    @Override
    public void animation (){
        if (right)
        {
            try{
                iR.loadFromImage(idleRight, new IntRect(0+idleTracker*widthOfSprite, 0, widthOfSprite, heightOfSprite));
            }catch (TextureCreationException tce){
                tce.printStackTrace();}
            super.boss.setTexture(iR);
        }
        else
        {
            try{
                iL.loadFromImage(idleLeft, new IntRect(0+idleTracker*widthOfSprite, 0, widthOfSprite, heightOfSprite));
            }catch (TextureCreationException tce){
                tce.printStackTrace();}
            super.boss.setTexture(iL);
        }
        if(idleTracker==4)
        {
            idleTracker = 0;
        }
        else
        {
            idleTracker++;
        }
    }

    /**
     * Getter of shooting state
     * @return true = currently shooting || false = currently not shooting
     */
    @Override
    public boolean getShooting()
    {
        boolean tf = false;
        if(currentShootingFrame == 128 && animationPacer==2)
        {
            tf = true;
        }
        return tf;
    }

    /**
     * Implementation of the hurting animation
     */
    @Override
    public void hurtingAnimation() 
    {
        if (right && hurtingAnimationTracker < 2)
        {
            try{
                hR.loadFromImage(hurtingRight, new IntRect(0+hurtingAnimationTracker*widthOfSprite, 0, widthOfSprite, heightOfSprite));
            }catch (TextureCreationException tce){
                tce.printStackTrace();}
            super.boss.setTexture(hR);
            hurtingAnimationTracker++;
        }
        else if (hurtingAnimationTracker < 2)
        {
            try{
                hL.loadFromImage(hurtingLeft, new IntRect((256-widthOfSprite)-widthOfSprite*hurtingAnimationTracker, 0, widthOfSprite, heightOfSprite));
            }catch (TextureCreationException tce){
                tce.printStackTrace();}
            super.boss.setTexture(hL);
            hurtingAnimationTracker++;
        }
        else
        {
            super.hurting = false;
            hurtingAnimationTracker = 0;
        }

    }

    
}
