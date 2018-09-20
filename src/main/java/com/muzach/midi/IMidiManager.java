package com.muzach.midi;

import com.muzach.generation.Composition;

import javax.sound.midi.Sequence;
import java.io.File;

public interface IMidiManager {
    void saveMidiFile(Composition composition, File file) throws Exception;
    Sequence getMidiSequence(Composition composition) throws Exception;
}
