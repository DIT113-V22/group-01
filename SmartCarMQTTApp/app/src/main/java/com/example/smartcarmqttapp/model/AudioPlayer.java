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
