package MainPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Setting page. Responsible for displaying the story of the 
 * game and explaining the controls.
 * Works on top of MainPage window.
 * Allows getting back to the main menu (MainPage).
 *
 * @author: Yujie
 * @author: Viltene
 * @version: 21-01-18, last time updated on 23-08-18
 * 
 */
public class SettingPage implements ActionListener {

    private static JFrame startF = new JFrame(); // Create frame of Start.
    private JPanel startP = new JPanel();// Create page of Start.
    private JButton back = new JButton("BACK"); // Create back button.
    private JFrame menu = new JFrame();
    private JLabel title = new JLabel("<html><div style='text-align: center;'>" + "<br>" + "INSTRUCTIONS" + "</div></html>", SwingConstants.CENTER);
    private JLabel instructions = new JLabel("<html><center>" + "A - walk left" + "<br>" + "D - walk right" + "<br>" + "W - jump" + "<br>" + "SHIFT - slow time"+ "<br>" + "ESCAPE - exit" + "<html><center>", SwingConstants.CENTER);
    private JLabel story = new JLabel("<html><center>" + "   Help the brave young Lyra Cornelia Batterbee on her quest to complete her other wing!" + "<br>" + "   Platform your way up to success while avoiding dangerous spikes and gaping holes in the ground." + "<br>" + "   There are items to aid you in your journey but remember with great power comes great responsibility, so use them wisely" + "<html><center>");
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Constructor for setting page
     * @param mainMenu JFrame that has been initiallised in MainPage
     */
    public SettingPage(JFrame mainMenu) {
        startP = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon("artAssets/silverwood house.png");
                img.paintIcon(this, g, 0, 0);
            }
        };
        menu = mainMenu;
        startF.setContentPane(startP); // Add the page into frame.
        startF.setUndecorated(true); // Let window has no border and cannot be moved.
        startP.setLayout(new BorderLayout()); // Decide layout.
        startF.setSize(1018, 629); // Decide the frame size.
        double width = (screenSize.getWidth() - 1018) / 2;
        double height = (screenSize.getHeight() - 629) / 2;
        startF.setLocation((int)width, (int)height); // Decide where the window appears.
        startF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close all frame when clicked the close button.
        startF.setVisible(true); // Let window visible.
        ImageIcon icon = new ImageIcon("ArtAssets/artTest.png");
        startF.setIconImage(icon.getImage());
        startF.setTitle("Wings of Virral"); // Add title "Main Page" into frame

//        back.setBounds(0, 0, 200, 55); // Give play button xposition,yposition,length,and wide.
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
        back.setFont(new Font("Baskerville Old Face", Font.BOLD, 36));
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        back.setForeground(new Color(255, 255, 255));
        startP.add(back, BorderLayout.PAGE_END); // Add the start button into page.
        back.addActionListener(this); // Let exit button can be actionPerformed.

        title.setFont(new Font("Baskerville Old Face", Font.BOLD, 50));
        title.setForeground(new Color(255, 255, 255));
        title.setVerticalAlignment(SwingConstants.CENTER);
        startP.add(title, BorderLayout.PAGE_START); // Add the start button into page.

        instructions.setFont(new Font("Baskerville Old Face", Font.BOLD, 30));
        instructions.setForeground(new Color(255, 255, 255));
        instructions.setPreferredSize(new Dimension(509, 500));
        startP.add(instructions, BorderLayout.LINE_END); // Add the start button into page.

        story.setFont(new Font("Baskerville Old Face", Font.BOLD, 30));
        story.setForeground(new Color(255, 255, 255));
        story.setPreferredSize(new Dimension(509, 500));
        story.setVisible(true);
        startP.add(story, BorderLayout.LINE_START); // Add the start button into page.

        startF.setVisible(true); //refresh
    }

    /**
     * Implementation of the action listener interface.
     *  @param e action event
    */
    public void actionPerformed(ActionEvent e) {
        // The effect when the start button is clicked.
        if (e.getSource() == back) {
            startF.setVisible(false); // Let window invisible.
            startF.dispose(); // Destroy window
            menu.setVisible(true);
        }
    }

}
