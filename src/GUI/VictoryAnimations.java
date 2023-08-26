package GUI;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Image;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;
import org.jsfml.system.Vector2f;

/**
 * @author: Viltene
 * @version: 2023-08-25
 * This class is responsible for the victory animations.
 * It is used in GUI.
 */
public class VictoryAnimations
{
    /* Set parameters */
    private int heightAndWitdth;
    private RectangleShape boss;
    private Image bossDeathImage;
    private Texture bossDeathTexture;
    private int frameOfAnimation = 0;
    private int c;

    public VictoryAnimations(int choice)
    {
        /* Initialise */
        c = choice;
        boss = new RectangleShape(new Vector2f (384, 384));
        bossDeathImage= new Image();
        frameOfAnimation = 0;
        heightAndWitdth = 128;
        /* If lab */
        if(c == 1)
        {
            try {
                bossDeathImage.loadFromFile(Paths.get("ArtAssets/Swordsman/Dead.png"));
            }catch(IOException ex) {
                ex.printStackTrace();
            }
            bossDeathTexture = new Texture();
        }
        /* If mannor */
        else
        {
            try {
                bossDeathImage.loadFromFile(Paths.get("ArtAssets/Countess_Vampire/Dead.png"));
            }catch(IOException ex) {
                ex.printStackTrace();
            }
            bossDeathTexture = new Texture();
        }

        boss.setPosition(300, 100);
    }

    /**
     * Gets the appropriate boss death textured rectangle
     * @return appropriate boss death textured rectangle
     */
    public RectangleShape winTheGame()
    {    
        try {
            bossDeathTexture.loadFromImage(bossDeathImage, new IntRect(0+heightAndWitdth*frameOfAnimation, 0, 128, 128));
        } catch (TextureCreationException e) {
            e.printStackTrace();
        }

        boss.setTexture(bossDeathTexture);

        /* If lab */
        if(frameOfAnimation != 3 && c == 1)
        {
            frameOfAnimation++;
        }
        /* If mannor */
        if(frameOfAnimation != 7 && c == 0)
        {
            frameOfAnimation++;
        }
        return boss;
    }
}
