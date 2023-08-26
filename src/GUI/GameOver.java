package GUI;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

public class GameOver
{
    private Vector2f v = new Vector2f (450, 130);
    private RectangleShape gameOverTablet;
    private Image gameOverImage;
    private Texture gameOverTexture;
    RenderWindow w;

    public GameOver(RenderWindow window)
    {
        w = window;
        gameOverTablet = new RectangleShape(v);
        gameOverImage = new Image();
        try {
            gameOverImage.loadFromFile(Paths.get("ArtAssets/game over.png"));
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        try{
           gameOverTexture = new Texture();
           gameOverTexture.loadFromImage(gameOverImage);
        }catch (TextureCreationException tce)
        {
            tce.printStackTrace();
        }
        gameOverTablet.setTexture(gameOverTexture);
        gameOverTablet.setPosition(415, 295);
    }

    public RectangleShape gameOverAnimation()
    {
        return gameOverTablet;
    }
}
