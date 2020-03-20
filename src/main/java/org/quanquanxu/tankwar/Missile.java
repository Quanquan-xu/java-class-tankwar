package org.quanquanxu.tankwar;

import java.awt.*;

public class Missile {
    private int x;
    private int y;
    private Direction direction;
    private boolean isEnemy;
    private static final int SPEED = 20;
    private int speedX = 0;
    private int speedY = 0;
    public Missile(int x, int y, Direction direction, boolean isEnemy){
        this.calculatePosition(x, y, direction);
        this.direction = direction;
        this.isEnemy = isEnemy;
    }
    private void calculatePosition(int x, int y, Direction direction){
      switch (direction){
          case UP:
              this.x = x + 15;
              this.y = y + 15;
              this.speedX = 0;
              this.speedY = -SPEED;
              break;
          case DOWN:
              this.x = x + 15;
              this.y = y + 15;
              this.speedX = 0;
              this.speedY = SPEED;
              break;
          case LEFT:
              this.x = x + 15;
              this.y = y + 15;
              this.speedX = -SPEED;
              this.speedY = 0;
              break;
          case RIGHT:
              this.x = x + 15;
              this.y = y + 15;
              this.speedX = SPEED;
              this.speedY = 0;
              break;
          case LEFT_UP:
              this.x = x + 15;
              this.y = y + 15;
              this.speedX = -SPEED;
              this.speedY = -SPEED;
              break;

          case RIGHT_UP:
              this.x = x + 15;
              this.y = y + 15;
              this.speedX = SPEED;
              this.speedY = -SPEED;
              break;
          case LEFT_DOWN:
              this.x = x + 15;
              this.y = y + 15;
              this.speedX = -SPEED;
              this.speedY = SPEED;
              break;
          case RIGHT_DOWN:
              this.x = x + 15;
              this.y = y + 15;
              this.speedX = SPEED;
              this.speedY = SPEED;
              break;

      }
    }
    public Image getImage(){
        String id = "missile";
        String prefix = "";
        return Toolkit.getFormatImage(id,prefix,this.direction.getAbbrev());
    }
    public void drawMissile(Graphics g) {
        this.moveMissile();
        if (this.x >= 0 && this.x <= 800 && this.y >= 0 && this.y<=600){
            g.drawImage(this.getImage(), this.x, this.y, null);
        }
    }
    private void moveMissile(){
        this.x = this.x + this.speedX;
        this.y = this.y + this.speedY;
    }
}
