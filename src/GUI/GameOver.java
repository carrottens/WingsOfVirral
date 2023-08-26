package GUI;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author: Viltene
 * This class is responsible for game over graphics
 * It is used in GUI
 */
public class GameOver
{
    /* Parameters of the Game Over tablet */
    private Vector2f v = new Vector2f (450, 130);
    private RectangleShape gameOverTablet;
    private Image gameOverImage;
    private Texture gameOverTexture;

    public GameOver()
    {
        /* Initialisation */
        gameOverTablet = new RectangleShape(v);
        gameOverImage = new Image();
        try {
            gameOverImage.loadFromFile(Paths.get("ArtAssets/game over.png"));
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        gameOverTexture = new Texture();
        try {           
           gameOverTexture.loadFromImage(gameOverImage);
        } catch (TextureCreationException tce) {
            tce.printStackTrace();
        }

        gameOverTablet.setTexture(gameOverTexture);
        gameOverTablet.setPosition(415, 295); // center
    }

    /**
     * Getter of the Game Over tablet
     * @return game over tablet
     */
    public RectangleShape gameOverAnimation()
    {
        return gameOverTablet;
    }
}
