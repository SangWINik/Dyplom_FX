package com.muzach.playback;

import com.muzach.generation.Composition;
import com.muzach.midi.IMidiManager;
import com.muzach.midi.MidiManager;
import com.muzach.music.NotePitch;

import javax.sound.midi.*;

public class Player {
    private static Sequencer sequencer;
    private static PlayerMetaEventListener playerMetaEventListener;
    //used to preview notes in editor
    private static Synthesizer midiSynth;

    private Player() {}

    static {
        try {
            sequencer = MidiSystem.getSequencer();
            playerMetaEventListener = new PlayerMetaEventListener(false, 120);

            midiSynth = MidiSystem.getSynthesizer();
            midiSynth.loadInstrument(midiSynth.getDefaultSoundbank().getInstruments()[0]);
            midiSynth.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //used to play melodies
    public static void playComposition(Composition composition, boolean loop) {
        try {
            if (!sequencer.isOpen()) {
                IMidiManager midiManager = new MidiManager();
                Sequence sequence = midiManager.getMidiSequence(composition);
                sequencer.setSequence(sequence);
                sequencer.open();
            }
            playerMetaEventListener.setTempo(composition.getTempoBMP());
            playerMetaEventListener.setLoop(loop);
            sequencer.addMetaEventListener(playerMetaEventListener);
            sequencer.setTempoInBPM(composition.getTempoBMP());
            if (composition.getMelodyTrack().isMuted()){
                sequencer.setTrackMute(0, true);
            }
            if (composition.getHarmonyTrack().isMuted()){
                sequencer.setTrackMute(1, true);
            }
            sequencer.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void pauseComposition() {
        try {
            if (sequencer.isOpen()) {
                long currentPostion = sequencer.getTickPosition();
                sequencer.stop();
                sequencer.setTickPosition(currentPostion);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void stopComposition() {
        try {
            if (sequencer.isOpen()) {
                sequencer.stop();
                sequencer.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //used in editor
    public static void playOneNote(NotePitch pitch, int toneOffset) {
        Thread thread = new Thread(() -> {
            try {
                midiSynth.getChannels()[0].noteOn(pitch.getMidiNote(toneOffset), 100);
                Thread.sleep(500);
                midiSynth.getChannels()[0].noteOff(pitch.getMidiNote(toneOffset));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public static Sequencer getSequencer() {
        return sequencer;
    }

    public static PlayerMetaEventListener getPlayerMetaEventListener() {
        return playerMetaEventListener;
    }
}
