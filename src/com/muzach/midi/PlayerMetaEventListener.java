package com.muzach.midi;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;

public class PlayerMetaEventListener implements MetaEventListener {
    public static final int END_OF_TRACK_MESSAGE = 47;

    private boolean loop = false;
    private int tempo;

    public PlayerMetaEventListener(boolean loop, int tempo) {
        this.loop = loop;
        this.tempo = tempo;
    }

    @Override
    public void meta(MetaMessage meta) {
        if (meta.getType() == END_OF_TRACK_MESSAGE) {
            if (Player.getSequencer() != null && Player.getSequencer().isOpen() && loop) {
                try {
                    Player.getSequencer().setTickPosition(0);
                    Player.getSequencer().setTempoInBPM(tempo);
                    Player.getSequencer().start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Player.getSequencer().close();
            }
        }
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }
}
