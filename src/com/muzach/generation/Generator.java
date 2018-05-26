package com.muzach.generation;

import com.muzach.midi.note.NoteLocation;
import com.muzach.music.Composition;

public class Generator {

    private Composition composition;
    private Preset preset;

    public Generator(Composition composition, Preset preset) {
        this.composition = composition;
        this.preset = preset;
    }

    public Composition generate(){
        IGeneratorHelper generatorHelper = new GeneratorHelper();
        composition.setTimeSignature(preset.getTimeSignature());
        NoteLocation.setMeasureLength(preset.getTimeSignature().numinator);
        generatorHelper.populateHarmonyRythm(composition.getHarmonyTrack(), preset, composition.getMeasureCount());
        generatorHelper.populateMelodyRythm(composition.getMelodyTrack(), preset, composition.getMeasureCount());
        generatorHelper.populateHarmonyChords(composition.getHarmonyTrack(), preset);
        generatorHelper.populateMelodyPitch(composition.getMelodyTrack(), preset);
        return composition;
    }
}
