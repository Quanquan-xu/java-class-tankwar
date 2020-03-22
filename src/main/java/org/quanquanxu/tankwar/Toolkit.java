package org.quanquanxu.tankwar;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Toolkit {
    public static Image getFormatImage(String name, String prefix, String suffix, String extension){
        String filePath = "assets/images/"+ prefix + name + suffix + extension;
        return new ImageIcon(filePath).getImage();
    }
    public static String getAudiosFile(String name, String extension){
        String audioFilePath = "assets/audios/" + name + extension;
        return new File(audioFilePath).toURI().toString();
    }

}
