package audioHandler;

import javafx.concurrent.Task;
import javafx.scene.media.AudioClip;

public class AudioManager {
    private Thread thread;
    private final AudioClip audio;
    private final Task task;


    private AudioClip getAudio() {
        return audio;
    }

    public AudioManager() {

        audio = new AudioClip(getClass().getResource("/audio/sound.mp3").toExternalForm());

       task  = new Task() {

            @Override
            protected Object call() {
                audio.setVolume(0.5f);

             audio.play();

                return this;
            }
        };
       playMusic();
    }
private void playMusic()
{
    thread = new Thread(task);
    thread.run();
}
    public void stop() {
        if (thread != null) {
            getAudio().stop();
            thread.interrupt();
        }
    }


}
