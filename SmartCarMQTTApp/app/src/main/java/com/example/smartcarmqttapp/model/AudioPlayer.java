package com.example.smartcarmqttapp.model;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer {
    private MediaPlayer mp;

    /*
    Sounds under resources.raw folder:
        Link to reference the car start sound: https://www.fesliyanstudios.com/sound-effects-search.php?q=car
        Link to reference the static sound: https://www.soundjay.com/tv-static-sound-effect.html#google_vignette
     */
    protected AudioPlayer() {}
    public final static AudioPlayer instance = new AudioPlayer();

    public void createMP(){
        mp = new MediaPlayer();
        mp.setVolume(100, 100);
    }

    public void chooseSongerino(Context context, int id){
        stopSound();
        this.mp = MediaPlayer.create(context, id);
    }

    public void playSound(boolean looping){
        mp.start();
        mp.setLooping(looping);
    }

    public void stopSound(){
        if (this.mp != null){
            mp.stop();
            mp.release();
        }
    }

    public MediaPlayer getMp(){
        return this.mp;
    }
}
