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

    public void playSound(Context context, int idOfFile, boolean looping){
        mp = MediaPlayer.create(context, idOfFile);
        mp.start();
        mp.setLooping(true);
    }

    public void stopSound(){
        if(mp.isPlaying() || mp.isLooping()){
            mp.stop();
            mp.release();
        }
    }


}
