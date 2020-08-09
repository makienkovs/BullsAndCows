package com.makienkovs.bullsandcows;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

class Sound {
    private boolean sound;
    private SoundPool sounds;
    private int cowSound;
    private int loseSound;
    private int tapSound;
    private int messageSound;
    private Context context;

    Sound(Context context, boolean sound) {
        this.context = context;
        this.sound = sound;
        createSoundPool();
    }

    private void createSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        sounds = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .setMaxStreams(10)
                .build();

        cowSound = sounds.load(context, R.raw.cow, 1);
        loseSound = sounds.load(context, R.raw.lose, 1);
        tapSound = sounds.load(context, R.raw.tap, 2);
        messageSound = sounds.load(context, R.raw.message, 1);
    }

    private void play(int s) {
        if (s > 0 && sound) {
            float volume = 1;
            sounds.play(s, volume, volume, 1, 0, 1);
        }
    }

    void release() {
        sounds.release();
        sounds = null;
    }

    void playWin() {
        try {
            play(cowSound);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void playLose() {
        try {
            play(loseSound);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void playTap() {
        try {
            play(tapSound);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void playMessage() {
        try {
            play(messageSound);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
