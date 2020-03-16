package org.quanquanxu.tankwar;

import javax.swing.*;
import java.awt.*;

public class GameClient extends JComponent{
    private  GameClient(){
        this.setPreferredSize(new Dimension(800,600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("assets/images/tankD.gif").getImage(), 400,100, null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("The Most Boring Tank War in the World");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
        frame.add(new GameClient());
        //frame.setPreferredSize(new Dimension(800,600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
