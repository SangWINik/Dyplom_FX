package com.muzach.midi;

import com.muzach.music.Composition;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class Player {
    private static Sequencer sequencer;

    // use sequencer.getMicrosecondLength(); and sequencer.getMicrosecondPosition(); in separate thread to set the marker
    public static Sequencer playComposition(Composition composition) {
        try {
            SequenceBuilder sequenceBuilder = new SequenceBuilder(composition.getTimeSignature());
            sequenceBuilder.setMelodyTrack(composition.getMelodyTrack());
            sequenceBuilder.setHarmonyTrack(composition.getHarmonyTrack());
            sequenceBuilder.setInstrument(0);
            Sequence sequence = sequenceBuilder.getSequence();
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setTempoInBPM(composition.getTempoBMP());
            sequencer.setSequence(sequence);
            if (composition.getMelodyTrack().isMuted()){
                sequencer.setTrackMute(0, true);
            }
            if (composition.getHarmonyTrack().isMuted()){
                sequencer.setTrackMute(1, true);
            }
            sequencer.start();
            return sequencer;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
