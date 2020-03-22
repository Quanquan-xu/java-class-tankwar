package org.quanquanxu.tankwar;

import java.awt.*;
import java.util.List;

public class Missile {
    private int x;
    private int y;
    private Direction direction;
    private boolean isEnemy;
    private boolean isAlive = true;
    private final int moveSpeed = 20;
    private final int xOffsetOfMissileToTank = 15;
    private final int yOffsetOfMissileToTank = 15;

    public Missile(int x, int y, Direction direction, boolean isEnemy) {
        this.x = x + this.xOffsetOfMissileToTank;
        this.y = y + this.yOffsetOfMissileToTank;
        this.direction = direction;
        this.isEnemy = isEnemy;
    }

    public Image getMissileImage() {
        String name = "missile";
        String prefix = "";
        return Toolkit.getFormatImage(name, prefix, this.direction.getAbbrev(), ".gif");
    }

    public void drawMissile(Graphics g) {
        this.moveMissile();
        boolean isCollided = this.detectIntersection();
        if (!isCollided && (this.x >= 0 && this.x <= 800 && this.y >= 0 && this.y <= 600)) {
            g.drawImage(this.getMissileImage(), this.x, this.y, null);
        } else {
            this.destroyMissile();
        }
    }

    private void moveMissile() {
        this.x = this.x + this.direction.getXFactor() * this.moveSpeed;
        this.y = this.y + this.direction.getYFactor() * this.moveSpeed;
    }

    private boolean detectIntersection() {
        boolean isIntersected = false;
        Rectangle missileBorder = this.getMissileBorder();
        List<Wall> walls = GameClient.getInstance().getWalls();
        if (this.isEnemy) {
            Tank playerTank = GameClient.getInstance().getPlayerTank();
            Rectangle playerTankBorder = playerTank.getTankBorder();
            if (playerTankBorder.intersects(missileBorder)) {
                isIntersected = true;
            }
        }
        else {
            List<Tank> enemyTanks = GameClient.getInstance().getEnemyTanks();
            for (Tank enemyTank : enemyTanks) {
                Rectangle enemyTankBorder = enemyTank.getTankBorder();
                if (enemyTankBorder.intersects(missileBorder)) {
                    isIntersected = true;
                    enemyTank.destroyTank();
                    break;
                }
            }

        }
        if (!isIntersected){
            for (Wall wall : walls){
                Rectangle wallBorder = wall.getWallBorder();
                if(wallBorder.intersects(missileBorder)){
                    isIntersected = true;
                    break;
                }
            }
        }
        return isIntersected;
    }


    public Rectangle getMissileBorder() {
        Image missileImage = this.getMissileImage();
        return new Rectangle(this.x, this.y, missileImage.getWidth(null), missileImage.getHeight(null));
    }
    public void destroyMissile(){
        this.isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }
}