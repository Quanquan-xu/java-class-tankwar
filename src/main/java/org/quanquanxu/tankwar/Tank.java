package org.quanquanxu.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class Tank {
    private int x;
    private int y;
    private boolean isUp, isDown, isLeft, isRight;
    private boolean isEnemy;
    private Direction direction;
    public Tank(int x, int y, Direction direction){
        this(x,y,direction, true);
    }
    public Tank(int x, int y, Direction direction, boolean isEnemy){
        this.x = x;
        this.y = y;
        this.isEnemy = isEnemy;
        this.direction = direction;
    }
    public Image getImage(){
        String tankPrefix = this.isEnemy? "e": "";
        switch (this.direction){
            case UP:return new ImageIcon("assets/images/"+ tankPrefix + "tankU.gif").getImage();
            case DOWN:return new ImageIcon("assets/images/"+ tankPrefix + "tankD.gif").getImage();
            case LEFT:return new ImageIcon("assets/images/"+ tankPrefix + "tankL.gif").getImage();
            case RIGHT:return new ImageIcon("assets/images/"+ tankPrefix + "tankR.gif").getImage();
            case UPLEFT:return new ImageIcon("assets/images/"+ tankPrefix + "tankLU.gif").getImage();
            case UPRIGHT:return new ImageIcon("assets/images/"+ tankPrefix + "tankRU.gif").getImage();
            case DOWNLEFT:return new ImageIcon("assets/images/"+ tankPrefix + "tankLD.gif").getImage();
            case DOWNRIGHT:return new ImageIcon("assets/images/"+ tankPrefix + "tankRD.gif").getImage();
        }
        return null;
    }
    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                this.isUp = true;
                break;
            case KeyEvent.VK_DOWN:
                this.isDown = true;
                break;
            case KeyEvent.VK_LEFT:
                this.isLeft = true;
                break;
            case KeyEvent.VK_RIGHT:
                this.isRight = true;
                break;
        }

    }
    public void keyReleased(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                this.isUp = false;
                break;
            case KeyEvent.VK_DOWN:
                this.isDown = false;
                break;
            case KeyEvent.VK_LEFT:
                this.isLeft = false;
                break;
            case KeyEvent.VK_RIGHT:
                this.isRight = false;
                break;
        }


    }
    public void drawTank(Graphics g) {
        int previousX = this.x;
        int previousY = this.y;
        this.determineDirection();
        this.move();
        boolean isCollided = this.intersectionDetect();
        if (isCollided){
            this.x = previousX;
            this.y = previousY;
        }
        g.drawImage(this.getImage(), this.x, this.y, null);
    }
    private void move(){
        if (this.isUp){
            this.y -= 5;
        }
        if (this.isDown){
            this.y += 5;
        }
        if (this.isLeft){
            this.x -=5;
        }
        if (this.isRight){
            this.x += 5;
        }
        this.borderLimit();
    }
    private void determineDirection(){
        if (this.isUp &&  !this.isDown && !this.isLeft && !this.isRight){
            this.direction = Direction.UP;
        }else if (this.isUp && !this.isDown && this.isLeft && !this.isRight){
            this.direction = Direction.UPLEFT;
        } else if (this.isUp && !this.isDown && !this.isLeft && this.isRight){
            this.direction = Direction.UPRIGHT;
        }else if (!this.isUp && this.isDown && !this.isLeft && !this.isRight){
            this.direction = Direction.DOWN;
        }else if (!this.isUp && this.isDown && this.isLeft && !this.isRight){
            this.direction = Direction.DOWNLEFT;
        }else if (!this.isUp && this.isDown && !this.isLeft && this.isRight){
            this.direction = Direction.DOWNRIGHT;
        }else if (!this.isUp && !this.isDown && this.isLeft && !this.isRight){
            this.direction = Direction.LEFT;
        }else if (!this.isUp && !this.isDown && !this.isLeft && this.isRight){
            this.direction = Direction.RIGHT;
        }else {
            this.isUp = false;
            this.isDown = false;
            this.isLeft = false;
            this.isRight = false;
        }
    }
    private void borderLimit(){
        int gameBoardWidth = 800;
        int gameBoardHeight = 600;
        Image tankImage = this.getImage();
        int tankWidth = tankImage.getWidth(null);
        int tankHeight = tankImage.getHeight(null);
        int tankXMaxBorder = gameBoardWidth - tankWidth;
        int tankYMaxBorder = gameBoardHeight - tankHeight;
        if (this.x < 0){
            this.x = 0;
        }else if (this.x > tankXMaxBorder){
            this.x = tankXMaxBorder;
        }
        if (this.y < 0){
            this.y = 0;
        }else if (this.y > tankYMaxBorder){
            this.y = tankYMaxBorder;
        }
    }
    private boolean intersectionDetect(){
        boolean isIntersected = false;
        Rectangle tankBorder = this.getTankBorder();
        List<Wall> walls = GameClient.getInstance().getWalls();
        List<Tank> enemyTanks = GameClient.getInstance().getEnemyTanks();
        for (Wall wall : walls){
            Rectangle wallBorder = wall.getWallBorder();
            if(wallBorder.intersects(tankBorder)){
                isIntersected = true;
                break;
            }
        }
        if (!isIntersected){
            for(Tank enemyTank: enemyTanks){
                Rectangle enemyTankBorder = enemyTank.getTankBorder();
                if(enemyTankBorder.intersects(tankBorder)){
                    isIntersected = true;
                    break;
                }
            }
        }

        return isIntersected;
    }

    public Rectangle getTankBorder(){
        Image tankImage = this.getImage();
        return new Rectangle(this.x, this.y, tankImage.getWidth(null), tankImage.getHeight(null));
    }

}
