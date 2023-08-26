package GUI;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

public class LivesAnimation
{
    private RenderWindow window;
    private RectangleShape nixieClock;
    private Image images [] = new Image [6];
    private Texture textures[] = new Texture [6];
    private int currentText;

    public LivesAnimation(RenderWindow window)
    {
        this.window = window;
        Vector2f v = new Vector2f (122, 40);
        nixieClock = new RectangleShape(v);
        nixieClock.setPosition(0, 0);
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
        try{
            for(int i = 0; i < 6; i++)
            {
                textures[i].loadFromImage(images[i]);
            }
        }catch (TextureCreationException ex){
            ex.printStackTrace();
        }
        nixieClock.setTexture(textures[0]);
        currentText = 0;
    }

    public void changeTextureLostALife()
    {
        if(currentText < 5)
        {
            currentText++;
            nixieClock.setTexture(textures[currentText]);
        }

    }

    public void changeTextureGainedALife()
    {
        if(currentText - 1 >= 0)
        {
            currentText = currentText - 1;
            nixieClock.setTexture(textures[currentText]);
        }

    }

    public void redraw()
    {
        window.draw(nixieClock);
    }
}
