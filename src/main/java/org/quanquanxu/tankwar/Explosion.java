package org.quanquanxu.tankwar;

import java.awt.*;

public class Explosion {
    private int x;
    private int y;
    private int step = 0;
    private boolean isAlive=true;
    public Explosion(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void drawExplosion(Graphics g){
        if (this.step>10){
            this.destroyExplosion();
            return;
        }
        Image explosionImage = Toolkit.getFormatImage(String.valueOf(this.step++),"","",".gif");
        g.drawImage(explosionImage,this.x,this.y,null);
    }
    private void destroyExplosion(){
        this.isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
