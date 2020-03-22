package org.quanquanxu.tankwar;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

public class Tank {
    private int x;
    private int y;
    private boolean isUp, isDown, isLeft, isRight;
    private boolean isEnemy;
    private Direction direction;
    private boolean isAlive = true;
    public Tank(int x, int y, Direction direction){
        this(x,y,direction, true);
    }
    public Tank(int x, int y, Direction direction, boolean isEnemy){
        this.x = x;
        this.y = y;
        this.isEnemy = isEnemy;
        this.direction = direction;
    }
    public Image getTankImage(){
        String name = "tank";
        String prefix = this.isEnemy? "e": "";
        return Toolkit.getFormatImage(name, prefix, this.direction.getAbbrev(), ".gif");
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
            case KeyEvent.VK_CONTROL:
                this.fire();
                break;
            case KeyEvent.VK_A:
                this.superFire();
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

    private void fire() {
        Missile missile = new Missile(this.x, this.y, this.direction, this.isEnemy);
        GameClient.getInstance().getMissiles().add(missile);
        Toolkit.playAudioSound("shoot",".wav");
    }
    private void superFire(){
        for(Direction direction: Direction.values()){
            Missile missile = new Missile(this.x, this.y, direction, this.isEnemy);
            GameClient.getInstance().getMissiles().add(missile);
        }
        String fileExtension = new Random().nextBoolean()? ".wav": ".aiff";
        Toolkit.playAudioSound("supershoot", fileExtension);
    }

    public void drawTank(Graphics g) {
        int previousX = this.x;
        int previousY = this.y;
        this.determineTankDirection();
        this.moveTank();
        boolean isCollided = this.detectIntersection();
        if (isCollided){
            this.x = previousX;
            this.y = previousY;
        }
        g.drawImage(this.getTankImage(), this.x, this.y, null);
    }
    private void determineTankDirection(){
        if (this.isUp &&  !this.isDown && !this.isLeft && !this.isRight){
            this.direction = Direction.UP;
        }else if (this.isUp && !this.isDown && this.isLeft && !this.isRight){
            this.direction = Direction.LEFT_UP;
        } else if (this.isUp && !this.isDown && !this.isLeft && this.isRight){
            this.direction = Direction.RIGHT_UP;
        }else if (!this.isUp && this.isDown && !this.isLeft && !this.isRight){
            this.direction = Direction.DOWN;
        }else if (!this.isUp && this.isDown && this.isLeft && !this.isRight){
            this.direction = Direction.LEFT_DOWN;
        }else if (!this.isUp && this.isDown && !this.isLeft && this.isRight){
            this.direction = Direction.RIGHT_DOWN;
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
    private void moveTank(){
        int moveSpeed = 5;
        if (this.isUp|| this.isDown|| this.isLeft|| this.isRight){
            this.x += this.direction.getXFactor()*moveSpeed;
            this.y += this.direction.getYFactor()*moveSpeed;
            this.borderLimit();
        }
    }

    private void borderLimit(){
        int gameBoardWidth = 800;
        int gameBoardHeight = 600;
        Image tankImage = this.getTankImage();
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
    private boolean detectIntersection(){
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
        Image tankImage = this.getTankImage();
        return new Rectangle(this.x, this.y, tankImage.getWidth(null), tankImage.getHeight(null));
    }
    public void destroyTank(){
        this.isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
