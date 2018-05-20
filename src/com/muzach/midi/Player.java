package com.muzach.midi;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class Player {
    private static Sequencer sequencer;

    public static void playSequence(Sequence sequence, int tempo) {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setTempoInBPM(tempo);
            sequencer.setSequence(sequence);
            sequencer.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
