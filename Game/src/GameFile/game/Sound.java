package GameFile.game;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    private Clip clip; // on another thread
    private int loopCount;

    public Sound(Clip c) {
        this.clip = c;
        this.loopCount = 0;
    }

    public Sound(Clip c, int loopCount) {
        this.clip = c;
        this.loopCount = 0;
        this.clip.loop(this.loopCount);
    }

    public synchronized void play() {
        if(clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.start(); // starts new thread with .start
    }

    public synchronized void stop() {
        this.clip.stop();
    }

    public synchronized void loopContinuously() {
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0); // Reset before looping
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }


    public void setVolume(float level) {
        FloatControl volume = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(20.0f * (float) Math.log10(level));
    }
}