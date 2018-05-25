package com.muzach.midi;

import com.muzach.music.Composition;

import javax.sound.midi.*;

public class Player {
    private static Sequencer sequencer;
    private static PlayerMetaEventListener playerMetaEventListener;

    static {
        try {
            sequencer = MidiSystem.getSequencer();
            playerMetaEventListener = new PlayerMetaEventListener(false, 120);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // use sequencer.getMicrosecondLength(); and sequencer.getMicrosecondPosition(); in separate thread to set the marker
    public static Sequencer playComposition(Composition composition, boolean loop) {
        try {
            //sequencer.close();
            if (!sequencer.isOpen()) {
                SequenceBuilder sequenceBuilder = new SequenceBuilder(composition.getTimeSignature(), composition.getMeasureCount());
                sequenceBuilder.setMelodyTrack(composition.getMelodyTrack());
                sequenceBuilder.setHarmonyTrack(composition.getHarmonyTrack());
                sequenceBuilder.setInstrument(0);
                Sequence sequence = sequenceBuilder.getSequence();
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
            //set on finish event handler
            return sequencer;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Sequencer getSequencer() {
        return sequencer;
    }

    public static PlayerMetaEventListener getPlayerMetaEventListener() {
        return playerMetaEventListener;
    }
}
