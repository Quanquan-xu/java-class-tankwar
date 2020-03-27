package org.quanquanxu.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GameClient extends JComponent {
    private static final int gameBoardWidth = 800;
    private static final int gameBoardHeight = 600;
    private static final Random random = new Random();
    private final AtomicInteger enemyTankKilled = new AtomicInteger(0);
    private Tank playerTank;
    private List<Tank> enemyTanks;
    private List<Wall> walls;
    private List<Missile> missiles = new CopyOnWriteArrayList<>();
    private List<Explosion> explosions = new ArrayList<>();
    private Blood blood = new Blood(400, 250);
    private static final GameClient INSTANCE = new GameClient();

    public static GameClient getInstance() {
        return INSTANCE;
    }

    private GameClient() {
        int playerTankX = gameBoardWidth / 2 - 10;
        int playerTankY = gameBoardHeight / 5;
        this.playerTank = new Tank(playerTankX, playerTankY, Direction.DOWN, false);
        this.walls = Arrays.asList(
                new Wall(170, 60, true, 16),
                new Wall(170, 520, true, 16),
                new Wall(60, 110, false, 14),
                new Wall(720, 110, false, 14)
        );
        this.initEnemyTank();
        this.setPreferredSize(new Dimension(gameBoardWidth, gameBoardHeight));
    }
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);
        super.paintComponent(g);
        if (this.playerTank.isAlive()) {
            this.playerTank.drawTank(g);
            for (Wall wall : this.walls) {
                wall.drawWall(g);
            }
            int tankBefore = this.enemyTanks.size();
            this.enemyTanks.removeIf(tank -> !tank.isAlive());
            int killedTank = tankBefore - this.enemyTanks.size();
            this.enemyTankKilled.addAndGet(killedTank);
            if (this.enemyTanks.isEmpty()) {
                this.initEnemyTank();
            }
            for (Tank enemyTank : this.enemyTanks) {
                enemyTank.drawTank(g);
            }
            this.missiles.removeIf(missile -> !missile.isAlive());
            for (Missile missile : this.missiles) {
                missile.drawMissile(g);
            }
            this.explosions.removeIf(explosion -> !explosion.isAlive());
            for (Explosion explosion : explosions) {
                explosion.drawExplosion(g);
            }
            if(this.playerTank.isDying() && random.nextInt(3)==2){
                this.blood.setAlive(true);
            }
            this.blood.draw(g);
            this.drawGameInfo(g);

        } else {
            this.gameOver(g);
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

    public List<Explosion> getExplosions() {
        return explosions;
    }
    public Tank getPlayerTank() {
        return playerTank;
    }

    public Blood getBlood() {
        return blood;
    }

    private void initEnemyTank() {
        int playerTankX = gameBoardWidth / 2 - 10;
        int playerTankY = gameBoardHeight / 5;
        int enemyTankWidthGap = 100;
        int enemyTankHeightGap = 50;
        this.enemyTanks = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                this.enemyTanks.add(new Tank(playerTankX + (j - 2) * enemyTankWidthGap, playerTankY * 4 - i * enemyTankHeightGap, Direction.UP));
            }
        }
    }

    private void drawGameInfo(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(null, Font.BOLD, 12));
        g.drawString("Missiles : " + this.missiles.size(), 10, 20);
        g.drawString("Explosions : " + this.explosions.size(), 10, 40);
        g.drawString("Enemy Tanks Left : " + this.enemyTanks.size(), 10, 60);
        g.setColor(Color.RED);
        g.drawString("Player Tank HP : " + this.playerTank.getHealthPoints(), 10, 80);
        g.drawString("Enemy Tanks Killed : " + this.enemyTankKilled.get(), 10, 100);
        g.setColor(Color.WHITE);
        g.drawImage(Toolkit.getFormatImage("tree","","",".png"),720,10, null);
        g.drawImage(Toolkit.getFormatImage("tree","","",".png"),10,520, null);

    }

    private void gameOver(Graphics g) {
        this.missiles.clear();
        this.enemyTanks.clear();
        this.explosions.clear();
        g.setColor(Color.RED);
        g.setFont(new Font(null, Font.BOLD, 100));
        g.drawString("GAME OVER", 90, 200);
        g.setFont(new Font(null, Font.BOLD, 50));
        g.drawString("PRESS ENTER TO RESTART", 70, 350);
    }

    public void restart() {
        if (!this.playerTank.isAlive()) {
            int playerTankX = gameBoardWidth / 2 - 10;
            int playerTankY = gameBoardHeight / 5;
            this.playerTank = new Tank(playerTankX, playerTankY, Direction.DOWN, false);
            this.blood.setAlive(false);
            this.initEnemyTank();
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
            try {
                client.repaint();
                if (client.playerTank.isAlive()) {
                    for (Tank enemyTank : client.enemyTanks) {
                        enemyTank.actRandomly();
                    }
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
