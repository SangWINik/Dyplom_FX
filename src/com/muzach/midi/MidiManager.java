package com.muzach.midi;

import com.muzach.generation.Composition;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.File;

public class MidiManager implements IMidiManager {

    @Override
    public void saveMidiFile(Composition composition, File file) throws Exception {
        SequenceBuilder sequenceBuilder = new SequenceBuilder(composition.getTimeSignature(), composition.getMeasureCount(), composition.getToneOffset(), true);
        sequenceBuilder.setMelodyTrack(composition.getMelodyTrack());
        sequenceBuilder.setHarmonyTrack(composition.getHarmonyTrack());
        MidiSystem.write(sequenceBuilder.getSequence(), 1, file);
    }

    @Override
    public Sequence getMidiSequence(Composition composition) throws Exception {
        SequenceBuilder sequenceBuilder = new SequenceBuilder(composition.getTimeSignature(), composition.getMeasureCount(), composition.getToneOffset(), false);
        sequenceBuilder.setMelodyTrack(composition.getMelodyTrack());
        sequenceBuilder.setHarmonyTrack(composition.getHarmonyTrack());
        sequenceBuilder.setInstrument(0);
        return sequenceBuilder.getSequence();
    }
}
