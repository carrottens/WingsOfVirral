package MainPage;

import GUI.GUI;
import org.jsfml.audio.Music;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author: Viltene
 * @version: last time updated on 23/08/18
 * 
 * Implements action listener which allows the player choose the look they prefer.
 * Generally, it only has impact on textures and music.
 * Works on top of MainPage window.
 * Allows getting back to the main menu (MainPage).
 * 
 * When the choice is made passes down mainPage parameters and the choice to GUI
 */
public class SkinChoice implements ActionListener 
{
    private static JFrame startF = new JFrame(); // Create frame of Start.
    private JPanel startP = new JPanel();// Create page of Start.
    private JButton wooden = new JButton("<html><center>"+"Silverwood"+"<br>"+"Manor"+"</center></html>"); // Create back button.
    private JButton mountains = new JButton("<html><center>" + "Laboratory" + "<br>" + "of Enriched" + "<br>" + "Apiology"+ "<html><center>"); // Create back button.
    private JButton back = new JButton("<html><center>"+ "BACK" +"</center></html>"); // Create back button.
    private JFrame menu = new JFrame();
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Music menuMusic; // main menu music is passed down from MainPage
    private Music mansonMusic; // Silverwood Manor music
    private Music labMusic; // Laboratory music

    /**
     * Constructor for SkinChoice
     * @param mainMenu JFrame that has been initiallised in MainPage
     * @param m music of the main menu
     */
    public SkinChoice(JFrame mainMenu, Music m) {
        startP = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon("artAssets/mix.png");
                img.paintIcon(this, g, 0, 0);
//                ImageIcon im = new ImageIcon("artAssets/File.jpg");
//                im.paintIcon(this, g, 509, 0);
            }};
        menuMusic = m;
        menu = mainMenu;
        mansonMusic = new Music();
        labMusic = new Music();
        try {
            mansonMusic.openFromFile(Paths.get("Music/mansion.wav")); //define mansion music
            labMusic.openFromFile(Paths.get("Music/snowy.wav")); // define laboratory music
        } catch (IOException e){
            e.printStackTrace();
        }
        menuMusic.setVolume(25);
        menuMusic.setLoop(true);
        menuMusic.play();

        startF.setContentPane(startP); // Add the page into frame.
        startF.setUndecorated(true); // Let window has no border and cannot be moved.
        startP.setLayout(null); // Decide layout.
        startF.setTitle("Wings of Virral"); // Add title "Main Page" into frame.
        startF.setSize(1018, 629); // Decide the frame size.
        double width = (screenSize.getWidth() - 1018) / 2;
        double height = (screenSize.getHeight() - 629) / 2;
        startF.setLocation((int)width, (int)height); // Decide where the window appears.
        startF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close all frame when clicked the close button.
        startF.setVisible(true); // Let window visible.
        ImageIcon icon = new ImageIcon("ArtAssets/artTest.png");
        startF.setIconImage(icon.getImage());

        back.setBounds(0, 600, 100, 40); // Give quit button xposition,yposition,length,and wide.
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
        back.setFont(new Font("Baskerville Old Face", Font.BOLD, 20));
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        back.setForeground(new Color(255, 255, 255));
        startP.add(back); // Add the exit button into page.
        back.addActionListener(this); // Let exit button can be actionPerformed.

        mountains.setBounds(104, 112, 301, 205); // Give play button xposition,yposition,length,and wide.
        mountains.setContentAreaFilled(false);
        mountains.setFocusPainted(false);
        mountains.setFont(new Font("Baskerville Old Face", Font.BOLD, 36));
        mountains.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mountains.setForeground(new Color(255, 255, 255));
        startP.add(mountains); // Add the start button into page.
        mountains.addActionListener(this); // Let exit button can be actionPerformed.

        wooden.setBounds(613, 312, 301, 205); // Give play button xposition,yposition,length,and wide.
        wooden.setContentAreaFilled(false);
        wooden.setFocusPainted(false);
        wooden.setFont(new Font("Baskerville Old Face", Font.BOLD, 36));
        wooden.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        wooden.setForeground(new Color(255, 255, 255));
        startP.add(wooden); // Add the start button into page.
        wooden.addActionListener(this); // Let exit button can be actionPerformed.
    }

    /**
     * Implementation of the action listener interface.
     *  @param e action event
    */
    /* Depending on the player's choice pass their choice to the GUI */
    public void actionPerformed(ActionEvent e) {
        // The effect when laoratory is chosen
        if (e.getSource() == mountains) {
            startF.setVisible(false); // Let window invisible.
            startF.dispose(); // Destroy window
            menuMusic.stop();
            GUI mainStuff = new GUI(1, menu, menuMusic, labMusic);
        }

        // The effect when mansion is chosen
        if (e.getSource() == wooden) {
            startF.setVisible(false); // Let window invisible.
            startF.dispose(); // Destroy window
            menuMusic.stop();
            GUI mainStuff = new GUI(0, menu, menuMusic, mansonMusic);
        }

        // The effect if back 'button' is pressed
        if (e.getSource() == back) {
            startF.setVisible(false); // Let window invisible.
            startF.dispose(); // Destroy window
            menu.setVisible(true);
        }
    }

}
