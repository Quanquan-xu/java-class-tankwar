package org.quanquanxu.tankwar;

import javax.swing.*;
import java.awt.*;

public class Wall {
    private int x;
    private int y;
    private boolean isHorizontal;
    private int brickNumber;
    public Wall(int x, int y, boolean isHorizontal, int brickNumber){
        this.x = x;
        this.y = y;
        this.isHorizontal = isHorizontal;
        this.brickNumber = brickNumber;
    }
    public void draw(Graphics g){
        Image brickImage = new ImageIcon("assets/images/brick.png").getImage();
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
