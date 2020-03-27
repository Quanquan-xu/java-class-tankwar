package org.quanquanxu.tankwar;

import java.awt.*;
import java.util.Random;

public class Blood {
    private int x;
    private int y;
    private int jumpOffsetX=0;
    private int jumpOffsetY=0;
    private int jumpStep = 1;
    private boolean isAlive=true;
    private static final Random random = new Random();
    public Blood(int x, int y){
        this.x = x;
        this.y = y;
    }
    private void randomPosition(){
        this.x = random.nextInt(600)+110;
        this.y = random.nextInt(400)+ 110;
    }
    public void draw(Graphics g){
        if(this.isAlive){
            switch (this.jumpStep){
                case 1:
                    this.jumpOffsetX=0;
                    this.jumpOffsetY = -1;
                    break;
                case 2:
                    this.jumpOffsetX=0;
                    this.jumpOffsetY = 1;
                    break;
                case 3:
                    this.jumpOffsetX=1;
                    this.jumpOffsetY =0;
                    break;
                case 4:
                    this.jumpOffsetX=-1;
                    this.jumpOffsetY =0;
                    this.jumpStep = 0;
                    break;
            }
            this.jumpStep += 1;
            g.drawImage(this.getImage(), this.x+this.jumpOffsetX, this.y+this.jumpOffsetY,null);
        }
    }
    private Image getImage(){
        return Toolkit.getFormatImage("blood","","", ".png");
    }
    public void destroy(){
        this.isAlive = false;
        this.randomPosition();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public Rectangle getBorder(){
        Image elementImage = this.getImage();
        return new Rectangle(this.x, this.y, elementImage.getWidth(null), elementImage.getHeight(null));
    }
}
