package org.quanquanxu.tankwar;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Save {
    private boolean isGameContinued;
    private TankPosition playerTankPosition;
    private List<TankPosition> enemyTankPositions;

    public Save(boolean isGameContinued, TankPosition playerTankPosition, List<TankPosition> enemyTankPositions) {
        this.isGameContinued = isGameContinued;
        this.playerTankPosition = playerTankPosition;
        this.enemyTankPositions = enemyTankPositions;
    }

    public boolean isGameContinued() {
        return this.isGameContinued;
    }

    @Data
    @NoArgsConstructor
    public static class TankPosition{
        private int x;
        private int y;
        private Direction direction;
        public TankPosition(int x, int y, Direction direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }
}
