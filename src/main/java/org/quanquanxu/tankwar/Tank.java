package org.quanquanxu.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;

public class Tank {
    private int x;
    private int y;
    private boolean isUp, isDown, isLeft, isRight;
    private Direction direction;
    public Tank(int x, int y, Direction direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    public Image getImage(){
        switch (this.direction){
            case UP:return new ImageIcon("assets/images/tankU.gif").getImage();
            case DOWN:return new ImageIcon("assets/images/tankD.gif").getImage();
            case LEFT:return new ImageIcon("assets/images/tankL.gif").getImage();
            case RIGHT:return new ImageIcon("assets/images/tankR.gif").getImage();
            case UPLEFT:return new ImageIcon("assets/images/tankLU.gif").getImage();
            case UPRIGHT:return new ImageIcon("assets/images/tankRU.gif").getImage();
            case DOWNLEFT:return new ImageIcon("assets/images/tankLD.gif").getImage();
            case DOWNRIGHT:return new ImageIcon("assets/images/tankRD.gif").getImage();
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
        this.determineDirection();
        this.move();
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

}
