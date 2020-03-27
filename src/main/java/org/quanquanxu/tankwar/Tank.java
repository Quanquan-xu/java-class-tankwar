package org.quanquanxu.tankwar;

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
    private static final int MAX_HEALTH_POINTS = 100;
    private static final Random random = new Random();
    private int healthPoints = MAX_HEALTH_POINTS;
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
    public Save.TankPosition getPosition(){
        return new Save.TankPosition(this.x, this.y, this.direction);
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
            case KeyEvent.VK_ENTER:
                GameClient.getInstance().restart();
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
        if (this.isAlive){
            Missile missile = new Missile(this.x, this.y, this.direction, this.isEnemy);
            GameClient.getInstance().getMissiles().add(missile);
            Toolkit.playAudioSound("shoot",".wav");
        }

    }
    private void superFire(){
        if (this.isAlive){
            for(Direction direction: Direction.values()){
                Missile missile = new Missile(this.x, this.y, direction, this.isEnemy);
                GameClient.getInstance().getMissiles().add(missile);
            }
            String fileExtension = new Random().nextBoolean()? ".wav": ".aiff";
            Toolkit.playAudioSound("supershoot", fileExtension);
        }

    }

    public void drawTank(Graphics g) {
        int previousX = this.x;
        int previousY = this.y;
        this.determineTankDirection();
        this.moveTank();
        Rectangle tankBorder = this.getTankBorder();
        if (this.detectIntersection(tankBorder)){
            this.x = previousX;
            this.y = previousY;
        }
        if(!this.isEnemy){
            this.reviveHealthPoints();
            this.drawHealthPoints(g);
            this.drawPetCamel(g);
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
        if (this.isUp|| this.isDown|| this.isLeft|| this.isRight||this.isEnemy){
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
    private boolean detectIntersection(Rectangle elementBorder){
        boolean isIntersected = false;
        List<Wall> walls = GameClient.getInstance().getWalls();
        List<Tank> enemyTanks = GameClient.getInstance().getEnemyTanks();
        Tank playerTank = GameClient.getInstance().getPlayerTank();
        for (Wall wall : walls){
            Rectangle wallBorder = wall.getWallBorder();
            if(wallBorder.intersects(elementBorder)){
                isIntersected = true;
                break;
            }
        }
        if (!isIntersected){
            for(Tank enemyTank: enemyTanks){
                Rectangle enemyTankBorder = enemyTank.getTankBorder();
                if(enemyTank!= this && enemyTankBorder.intersects(elementBorder)){
                    isIntersected = true;
                    break;
                }
            }
            if(this.isEnemy){
                Rectangle playerTankBorder = playerTank.getTankBorder();
                if(elementBorder.intersects(playerTankBorder)){
                    isIntersected = true;
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
    public boolean isDying(){
        return this.healthPoints <= 20;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    private void drawHealthPoints(Graphics g){
        int healthPointsX = this.x;
        int healthPointsY = this.y - 10;
        Rectangle healthPointBorder = new Rectangle(healthPointsX, healthPointsY, this.getTankImage().getWidth(null), 10);
        if(this.detectIntersection(healthPointBorder)){
            healthPointsX = this.x;
            healthPointsY = this.y + this.getTankImage().getHeight(null);
            healthPointBorder = new Rectangle(healthPointsX, healthPointsY, this.getTankImage().getWidth(null), 10);
            if(this.detectIntersection(healthPointBorder)){
                return;
            }
        }
        g.setColor(Color.WHITE);
        g.fillRect(healthPointsX,healthPointsY,this.getTankImage().getWidth(null),10);
        g.setColor(Color.RED);
        int healthPointsWidth = healthPoints*this.getTankImage().getWidth(null)/100;
        g.fillRect(healthPointsX,healthPointsY,healthPointsWidth,10);
    }
    private void drawPetCamel(Graphics g){
        Image petCamelImage = Toolkit.getFormatImage("pet-camel","","",".gif");
        int petCamelX = this.x - petCamelImage.getWidth(null)-4;
        int petCamelY = this.y;
        Rectangle petCamelBorder = new Rectangle(petCamelX, petCamelY, petCamelImage.getWidth(null), petCamelImage.getHeight(null));
        if(! this.detectIntersection(petCamelBorder)){
            g.drawImage(petCamelImage, petCamelX, petCamelY, null);
        }else {
            petCamelX = this.x + this.getTankImage().getWidth(null)+4;
            petCamelY = this.y;
            petCamelBorder = new Rectangle(petCamelX, petCamelY, petCamelImage.getWidth(null), petCamelImage.getHeight(null));
            if(!this.detectIntersection(petCamelBorder)){
                g.drawImage(petCamelImage, petCamelX, petCamelY, null);
            }
        }
    }
    private void reviveHealthPoints(){
        Blood blood = GameClient.getInstance().getBlood();
        if(blood.isAlive()){
            Rectangle tankBorder = this.getTankBorder();
            Rectangle bloodBorder = blood.getBorder();
            if(tankBorder.intersects(bloodBorder)){
                this.healthPoints = MAX_HEALTH_POINTS;
                blood.destroy();
                Toolkit.playAudioSound("revive", ".wav");
            }
        }
    }
    public void removeHealthPoints(){
        this.healthPoints -= 10;
        if (this.healthPoints <= 0){
            this.destroyTank();
        }
    }
    private int step = random.nextInt(12) + 3;
    public void actRandomly() {
        Direction [] dirs = Direction.values();
        if (step==0){
            step = random.nextInt(12)+3;
            this.direction = dirs[random.nextInt(dirs.length)];
            if (random.nextBoolean()){
                this.fire();
            }
        }
        step--;
    }
}
