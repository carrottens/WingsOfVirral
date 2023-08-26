package Item;

import javax.swing.*;
import java.awt.*;

public class Item extends JComponent {

    static int it = 0;

    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString("RESPAWN POINT", 50, 30);
        g.setColor(Color.RED);
        g.drawString("EXTRA LIFE", 50, 50);

    }

    public void setIt(int it)
    {
        this.it = it;
    }
}
