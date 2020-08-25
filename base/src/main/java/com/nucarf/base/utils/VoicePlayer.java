package com.nucarf.base.utils;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * 说明：语音播放器 <br>
 * 时间：2017/6/13 13:39<br>
 * 修改记录： <br>
 */
public class VoicePlayer extends MediaPlayer {

    private String voicePath;
    public static VoicePlayer voicePlayer;

    public VoicePlayer(String voicePath) {
        this.voicePath = voicePath;
    }

    public VoicePlayer() {
    }

    public static VoicePlayer getPlayer() {
        if (voicePlayer == null) {
            synchronized (VoicePlayer.class) {
                if (voicePlayer == null) {
                    voicePlayer = new VoicePlayer();
                }
            }
        }
        return voicePlayer;
    }

    public String getVoicePath() {
        return voicePath;
    }

    public boolean isPlaying(String voicePath) {
        return this.voicePath != null && this.voicePath.equals(voicePath) && isPlaying();
    }

    public void setVoicePath(String voicePath) throws IOException {
        this.voicePath = voicePath;
        VoicePlayer.getPlayer().reset();
        VoicePlayer.getPlayer().setDataSource(voicePath);
        VoicePlayer.getPlayer().prepare();
    }
}
