package controllers;

import javafx.concurrent.Task;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AudioManager implements Runnable {
    private Thread thread;
    private AudioClip audio;
    private ArrayList<AudioClip> playlist;
    private final Task task;
  /*  ArrayList<MediaPlayer> mediaPlayers=new ArrayList<>();
    private int counter=0;*/
    // TODO: 2018-12-20 make a playlist

    public AudioClip getAudio() {
        return audio;
    }

    public AudioManager() {
    /*    File dir = new File("./src/audio");
        File[] filelist = dir.listFiles();
        for (File f: filelist) {
            //System.out.println(f..getName());
            playlist.add(new AudioClip(f.getName()));
        }*/
        audio = new AudioClip(getClass().getResource("../audio/sound.mp3").toExternalForm());
       //audio= playlist.get(2);
        task = new Task() {

            @Override
            protected Object call() {

                audio.setVolume(0.5f);
                audio.setCycleCount(AudioClip.INDEFINITE);
                audio.play();


                return null;
            }
        };
        thread = new Thread(task);
        this.thread.run();

    }

    public void stop() {
        if (thread != null) {
            this.getAudio().stop();
            thread.interrupt();
        }
    }

    @Override
    public void run() {

    }
}
