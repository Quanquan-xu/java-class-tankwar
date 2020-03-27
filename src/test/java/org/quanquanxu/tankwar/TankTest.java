package org.quanquanxu.tankwar;

import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;

class TankTest {

    @org.junit.jupiter.api.Test
    void getTankImage() {
        for(Direction direction: Direction.values()){
            Tank playerTank = new Tank(0,0,direction,false);
            assertTrue(playerTank.getTankImage().getWidth(null)>0, "PlayerTank: " + direction + " Image Width is something wrong");
            Tank enemyTank = new Tank(0,0,direction, true);
            assertTrue(enemyTank.getTankImage().getWidth(null)>0, "EnemyTank: " + direction + " Image Width is something wrong");

        }
    }
}