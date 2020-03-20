package org.quanquanxu.tankwar;

import javax.swing.*;
import java.awt.*;

public class Toolkit {
    public static Image getFormatImage(Direction direction, String id, String prefix){
        String suffix = "";
        switch (direction){
            case UP:
                suffix = "U";
                break;
            case DOWN:
                suffix = "D";
                break;
            case LEFT:
                suffix = "L";
                break;
            case RIGHT:
                suffix = "R";
                break;
            case UPLEFT:
                suffix = "LU";
                break;
            case UPRIGHT:
                suffix = "RU";
                break;
            case DOWNLEFT:
                suffix = "LD";
                break;
            case DOWNRIGHT:
                suffix = "RD";
                break;
        }
        String filePath = "assets/images/"+ prefix + id + suffix + ".gif";
        return new ImageIcon(filePath).getImage();

    }

}
