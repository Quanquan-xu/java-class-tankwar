package org.quanquanxu.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameClient extends JComponent {
    private static final int gameBoardWidth = 800;
    private static final int gameBoardHeight = 600;
    private Tank playerTank;
    private List<Tank> enemyTanks;
    private List<Wall> walls;
    private List<Missile> missiles = new ArrayList<>();
    private List<Explosion> explosions = new ArrayList<>();
    private static final GameClient INSTANCE = new GameClient();

    public static GameClient getInstance() {
        return INSTANCE;
    }

    private GameClient() {
        int playerTankX = gameBoardWidth / 2 - 10;
        int playerTankY = gameBoardHeight / 5;
        this.playerTank = new Tank(playerTankX, playerTankY, Direction.DOWN, false);
        this.walls = Arrays.asList(
                new Wall(170, 70, true, 16),
                new Wall(170, 540, true, 16),
                new Wall(100, 110, false, 15),
                new Wall(680, 110, false, 15)
        );
        this.initEnemyTank();
        this.setPreferredSize(new Dimension(gameBoardWidth, gameBoardHeight));
    }

    private void initEnemyTank() {
        int playerTankX = gameBoardWidth / 2 - 10;
        int playerTankY = gameBoardHeight / 5;
        int enemyTankWidthGap = 100;
        int enemyTankHeightGap = 50;
        this.enemyTanks = new ArrayList<>(12);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                this.enemyTanks.add(new Tank(playerTankX + (j - 2) * enemyTankWidthGap, playerTankY * 4 - i * enemyTankHeightGap, Direction.UP));
            }
        }
    }

    public List<Tank> getEnemyTanks() {
        return enemyTanks;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Missile> getMissiles() {
        return missiles;
    }

    public List<Explosion> getExplosions(){return explosions;}

    public Tank getPlayerTank() {
        return playerTank;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);
        super.paintComponent(g);
        this.playerTank.drawTank(g);
        for (Wall wall : this.walls) {
            wall.drawWall(g);
        }
        this.enemyTanks.removeIf(tank -> !tank.isAlive());
        if (this.enemyTanks.isEmpty()){
            this.initEnemyTank();
        }
        for (Tank tank : this.enemyTanks) {
            tank.drawTank(g);
        }
        this.missiles.removeIf(missile -> !missile.isAlive());
        for (Missile missile : this.missiles) {
            missile.drawMissile(g);
        }
        this.explosions.removeIf(explosion -> !explosion.isAlive());
        for (Explosion explosion: explosions){
            explosion.drawExplosion(g);
        }
    }

    public static void main(String[] args) {
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });
        JFrame frame = new JFrame();
        frame.setTitle("The Most Boring Tank War in the World");
        frame.setIconImage(Toolkit.getFormatImage("icon", "", "", ".png"));
        final GameClient client = GameClient.getInstance();
        frame.add(client);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                client.playerTank.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                client.playerTank.keyReleased(e);
            }
        });
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        while (true) {
            client.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
