package sample;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundPlayer {

    public static void playSound(String url) {

        Thread thread1 = new Thread(() -> {

            Platform.runLater(() -> {
                File file = new File(url);
                Media media = new Media(file.toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();

            });
            });

        thread1.start();
    }

}

