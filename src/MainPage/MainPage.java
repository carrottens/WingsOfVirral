package MainPage;

import org.jsfml.audio.Music;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * The Start page.
 * 
 * @author: Yujie Chen
 * @date: 21-01-18
 * @version: 1.0.0
 */
public class MainPage implements ActionListener {
    private static JFrame startF = new JFrame(); // Create frame of Start.
    private JPanel startP;// Create page of Start.
    private JButton play = new JButton("START"); // Create start button.
    private JButton setting = new JButton("INSTRUCTIONS"); // Create start button.
    private JButton quit = new JButton("QUIT"); // Create exit button.
    private JLabel title = new JLabel("Wings Of Virral", SwingConstants.CENTER);
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Music menuMusic;


    public MainPage() {
        startP = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon("artAssets/OurGame.png");
                img.paintIcon(this, g, 0, 0);
            }
        };
        startF.setContentPane(startP); // Add the page into frame.
        startF.setUndecorated(true); // Let window has no border and cannot be moved.
        startP.setLayout(null); // Decide layout.
        ImageIcon icon = new ImageIcon("ArtAssets/artTest.png");
        startF.setIconImage(icon.getImage());


        title.setBounds(259, 150, 500, 55); // Give play button xposition,yposition,length,and wide.
        title.setFont(new Font("Baskerville Old Face", Font.BOLD, 50));
        title.setForeground(new Color(255, 255, 255));
        startP.add(title); // Add the start button into page.

        play.setBounds(309, 234, 400, 55); // Give play button xposition,yposition,length,and wide.
        play.setContentAreaFilled(false);
        play.setFocusPainted(false);
        play.setFont(new Font("Baskerville Old Face", Font.BOLD, 36));
        play.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        play.setForeground(new Color(255, 255, 255));
        startP.add(play); // Add the start button into page.
        play.addActionListener(this); // Let start button can be actionPerformed.

        setting.setBounds(309, 344, 400, 55); // Give play button xposition,yposition,length,and wide.
        setting.setContentAreaFilled(false);
        setting.setFocusPainted(false);
        setting.setFont(new Font("Baskerville Old Face", Font.BOLD, 36));
        setting.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setting.setForeground(new Color(255, 255, 255));
        startP.add(setting); // Add the start button into page.
        setting.addActionListener(this); // Let start button can be actionPerformed.

        quit.setBounds(309, 454, 400, 55); // Give quit button xposition,yposition,length,and wide.
        quit.setContentAreaFilled(false);
        quit.setFocusPainted(false);
        quit.setFont(new Font("Baskerville Old Face", Font.BOLD, 36));
        quit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        quit.setForeground(new Color(255, 255, 255));
        startP.add(quit); // Add the exit button into page.
        quit.addActionListener(this); // Let exit button can be actionPerformed.

        startF.setTitle("Wings of Virral"); // Add title "Main Page" into frame.
        startF.setSize(1018, 629); // Decide the frame size.
        double width = (screenSize.getWidth() - 1018) / 2;
        double height = (screenSize.getHeight() - 629) / 2;
        startF.setLocation((int)width, (int)height); // Decide where the window appears.
        startF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close all frame when clicked the close button.
        startF.setVisible(true); // Let window visible.

        menuMusic = new Music();
        try {
            menuMusic.openFromFile(Paths.get("Music/menuMusic.wav"));
        } catch (IOException e){
            e.printStackTrace();
        }
        menuMusic.play();
    }

    /**
     * Design click button.
     */
    public void actionPerformed(ActionEvent e) {
        // The effect when the start button is clicked.
        if (e.getSource() == play) {
            startF.setVisible(false); // Let window invisible.\
            SkinChoice sch = new SkinChoice(startF, menuMusic);
        }

        // The effect when the exit button is clicked.
        else if (e.getSource() == quit) {
            startF.setVisible(false); // Let window invisible.
            startF.dispose(); // Destroy window.
            System.exit(0); // Close all the window.
        }

        else if (e.getSource() == setting) {
            startF.setVisible(false); // Let window invisible.
            SettingPage controls = new SettingPage(startF);
            }

    }

}
