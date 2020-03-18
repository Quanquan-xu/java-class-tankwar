package org.quanquanxu.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameClient extends JComponent{
    private Tank playerTank;
    private List<Tank> enemyTanks;
    private  GameClient(){
        int gameBoardWidth = 800;
        int gameBoardHeight = 600;
        int playerTankX = gameBoardWidth / 2 - 10;
        int playerTankY = gameBoardHeight / 5;
        int enemyTankWidthGap = 100;
        int enemyTankHeightGap = 50;
        this.playerTank = new Tank(playerTankX,playerTankY, Direction.DOWN, false);
        this.enemyTanks = new ArrayList<>(12);
        for(int i=0; i<3; i++){
            for (int j=0; j<5; j++){
                this.enemyTanks.add(new Tank(playerTankX + (j-2)*enemyTankWidthGap,playerTankY * 4 -i*enemyTankHeightGap, Direction.UP));
            }
        }
        this.setPreferredSize(new Dimension(gameBoardWidth,gameBoardHeight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.playerTank.drawTank(g);
        for (Tank tank: this.enemyTanks){
            tank.drawTank(g);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("The Most Boring Tank War in the World");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
        final GameClient client = new GameClient();
        frame.add(client);
        //frame.setPreferredSize(new Dimension(800,600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                client.playerTank.keyPressed(e);
                client.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                client.playerTank.keyReleased(e);
                client.repaint();
            }
        });
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
