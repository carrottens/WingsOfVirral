package GUI;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author: Viltene
 * This class is responsible for player lives representation animation
 * It is used in GUI
 */
public class LivesAnimation
{
    /* Initialisation */
    private RenderWindow window;
    private RectangleShape nixieClock;
    private Image images [] = new Image [6];
    private Texture textures[] = new Texture [6];
    private int currentTexture;

    public LivesAnimation(RenderWindow window)
    {
        this.window = window; //takes from GUI
        Vector2f v = new Vector2f (122, 40);
        nixieClock = new RectangleShape(v);
        nixieClock.setPosition(60, 0);
        /* Loads images */
        for (int i = 0; i < 6; i++)
        {
            images[i] = new Image();
            textures[i] = new Texture();
        }
        try {
            images[0].loadFromFile(Paths.get("ArtAssets/vials1.png"));
            images[1].loadFromFile(Paths.get("ArtAssets/vials2.png"));
            images[2].loadFromFile(Paths.get("ArtAssets/vials3.png"));
            images[3].loadFromFile(Paths.get("ArtAssets/vials4.png"));
            images[4].loadFromFile(Paths.get("ArtAssets/vials5.png"));
            images[5].loadFromFile(Paths.get("ArtAssets/vials6.png"));
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        /* Makes the images into textures */
        try{
            for(int i = 0; i < 6; i++)
            {
                textures[i].loadFromImage(images[i]);
            }
        }catch (TextureCreationException ex){
            ex.printStackTrace();
        }
        /* set primary texture */
        nixieClock.setTexture(textures[0]);
        currentTexture = 0;
    }

    /**
     * Applies appropriate texture if a life is lost
     */
    public void changeTextureLostALife()
    {
        if(currentTexture < 5)
        {
            currentTexture++;
            nixieClock.setTexture(textures[currentTexture]);
        }

    }

    /**
     * Applies appropriate texture if life is gained
     */
    public void changeTextureGainedALife()
    {
        if(currentTexture - 1 >= 0)
        {
            currentTexture = currentTexture - 1;
            nixieClock.setTexture(textures[currentTexture]);
        }

    }

    /**
     * Redraws the representation of the nixie clock
     */
    public void redraw()
    {
        window.draw(nixieClock);
    }
}
