package com.example.smartcarmqttapp.model;

import android.content.ClipData;
import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import javax.sound.sampled.*;

public class AudioPlayer {
    private MediaPlayer mp;

    /*
    Sounds under resources.raw folder:
        Link to reference the carStartingSound: https://www.fesliyanstudios.com/sound-effects-search.php?q=car
        Link to reference the disconnectedStatic: https://www.soundjay.com/tv-static-sound-effect.html#google_vignette
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
