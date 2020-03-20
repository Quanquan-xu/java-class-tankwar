package org.quanquanxu.tankwar;

import javax.swing.*;
import java.awt.*;

public class Wall {
    private int x;
    private int y;
    private boolean isHorizontal;
    private int brickNumber;
    private Image brickImage;
    public Wall(int x, int y, boolean isHorizontal, int brickNumber){
        this.brickImage = new ImageIcon("assets/images/brick.png").getImage();
        this.x = x;
        this.y = y;
        this.isHorizontal = isHorizontal;
        this.brickNumber = brickNumber;
    }
    public Rectangle getWallBorder(){
        return isHorizontal? new Rectangle(this.x, this.y,
                brickNumber*brickImage.getWidth(null),
                brickImage.getHeight(null)) : new Rectangle(this.x, this.y,
                brickImage.getWidth(null), brickNumber*brickImage.getHeight(null));
    }
    public void drawWall(Graphics g){
        if (isHorizontal){
            for (int i=0; i< this.brickNumber; i++){
                g.drawImage(brickImage, x + i*brickImage.getWidth(null), y, null);
            }
        }else {
            for (int i=0; i< this.brickNumber; i++){
                g.drawImage(brickImage, x, y + i*brickImage.getHeight(null), null);
            }
        }
    }


}
