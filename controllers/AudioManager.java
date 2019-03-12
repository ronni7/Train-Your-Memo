package controllers;

import javafx.concurrent.Task;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.AudioSystem;
import java.util.ArrayList;

public class AudioManager implements Runnable {
private    Thread thread;
private    AudioClip audio;
private     final Task task;
  /*  ArrayList<MediaPlayer> mediaPlayers=new ArrayList<>();
    private int counter=0;*/
    // TODO: 2018-12-20 make a playlist
    public Thread getThread() {
        return thread;
    }

    public AudioClip getAudio() {
        return audio;
    }

    public Task getTask() {
        return task;
    }
/*   public void AddTrack()
    {
        if(counter>6)
            counter=0;
        counter++;
        mediaPlayers.add(new MediaPlayer(new Media(getClass().getResource("../audio/sound"+".mp3").toExternalForm())));
    }*/
    public AudioManager() {

        audio = new AudioClip(getClass().getResource("../audio/sound.mp3").toExternalForm());
      //  AddTrack();
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
    //        if (this.audio.isPlaying()) System.out.println("i work but don't stop music :c ");
            thread.interrupt();
        }
    }

    @Override
    public void run() {

    }
}
