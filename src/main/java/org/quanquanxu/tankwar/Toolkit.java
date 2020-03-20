package org.quanquanxu.tankwar;

import javax.swing.*;
import java.awt.*;

public class Toolkit {
    public static Image getFormatImage(String id, String prefix, String suffix){
        String filePath = "assets/images/"+ prefix + id + suffix + ".gif";
        return new ImageIcon(filePath).getImage();

    }

}
