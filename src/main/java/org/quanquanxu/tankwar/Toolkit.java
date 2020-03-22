package org.quanquanxu.tankwar;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Toolkit {
    public static Image getFormatImage(String name, String prefix, String suffix, String extension){
        String filePath = "assets/images/"+ prefix + name + suffix + extension;
        return new ImageIcon(filePath).getImage();
    }
    public static void playAudioSound(String name, String extension){
        String audioFilePath = "assets/audios/" + name + extension;
        String soundFile = new File(audioFilePath).toURI().toString();
        Media sound = new Media(soundFile);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

}
