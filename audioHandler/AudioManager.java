package controllers.audioHandler;

import javafx.concurrent.Task;
import javafx.scene.media.AudioClip;

public class AudioManager {
    private Thread thread;
    private final AudioClip audio;
    private final Task task;
/*  ArrayList<MediaPlayer> mediaPlayers=new ArrayList<>();
    private int counter=0;*/
    // TODO: 2018-12-20 make a playlist

    private AudioClip getAudio() {
        return audio;
    }

    public AudioManager() {
    /*    File dir = new File("./src/audio");
        File[] filelist = dir.listFiles();
        for (File f: filelist) {
            //System.out.println(f..getName());
            playlist.add(new AudioClip(f.getName()));
        }*/
        audio = new AudioClip(getClass().getResource("/audio/sound.mp3").toExternalForm());
        //audio= playlist.get(2);
        //    private ArrayList<AudioClip> playlist;
       task  = new Task() {

            @Override
            protected Object call() {
                audio.setVolume(0.5f);
              //  audio.setCycleCount(AudioClip.INDEFINITE);
             audio.play();

                return this;
            }
        };
       playMusic();
    }
private void playMusic()
{
    thread = new Thread(task);
    this.thread.run();
   /* if(thread.isInterrupted())
    thread.run();*/


}
    public void stop() {
        if (thread != null) {
            this.getAudio().stop();
            thread.interrupt();
        }
    }


}
