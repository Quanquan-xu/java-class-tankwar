package org.quanquanxu.tankwar;

public enum  Direction {
    UP("U",0,-1),
    DOWN("D",0,1),
    LEFT("L",-1,0),
    RIGHT("R",1,0),
    LEFT_UP("LU",-1,-1),
    RIGHT_UP("RU",1,-1),
    LEFT_DOWN("LD",-1,1),
    RIGHT_DOWN("RD",1,1);
    private final String abbrev;
    private final int xFactor;
    private final int yFactor;
    Direction(String abbrev, int xFactor, int yFactor) {
        this.abbrev = abbrev;
        this.xFactor = xFactor;
        this.yFactor = yFactor;
    }

    public String getAbbrev() {
        return this.abbrev;
    }

    public int getXFactor() {
        return xFactor;
    }

    public int getYFactor() {
        return yFactor;
    }
}
