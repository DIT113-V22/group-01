package com.example.smartcarmqttapp.model;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer {
    private MediaPlayer mp;
    private MediaPlayer mpBlinker;

    /*
    Sounds under resources.raw folder:
        Link to reference the car start sound: https://www.fesliyanstudios.com/sound-effects-search.php?q=car
        Link to reference the static sound: https://www.soundjay.com/tv-static-sound-effect.html#google_vignette
     */
    protected AudioPlayer() {}
    public final static AudioPlayer instance = new AudioPlayer();

    public void createMP(){
        try {
            mp = new MediaPlayer();
            mp.setVolume(100, 100);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void chooseSongerino(Context context, int id){
        try {
            stopSound();
            this.mp = MediaPlayer.create(context, id);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void playSound(boolean looping){
        try {
            mp.setLooping(looping);
            mp.start();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stopSound(){
        try {
            if (mp != null){
                mp.stop();
                mp.release();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void initBlinker(Context context, int id){
        try {
            mpBlinker = MediaPlayer.create(context, id);
            mpBlinker.setVolume(100, 100);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void enableBlinker(){
        try {
            mpBlinker.setLooping(true);
            mpBlinker.start();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void disableBlinker(){
        try {
            if (mpBlinker.isPlaying()) {
                mpBlinker.pause();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public MediaPlayer getMp(){
        return this.mp;
    }
}
