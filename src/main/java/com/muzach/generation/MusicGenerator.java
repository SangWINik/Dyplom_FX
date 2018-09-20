package com.muzach.generation;

import com.muzach.music.NoteLocation;
import com.muzach.preset.Preset;

public class MusicGenerator implements IMusicGenerator {

    private Composition composition;
    private Preset preset;

    public MusicGenerator(Composition composition, Preset preset) {
        this.composition = composition;
        this.preset = preset;
    }

    public Composition generate(){
        IGeneratorHelper generatorHelper = new GeneratorHelper();
        composition.setTimeSignature(preset.getTimeSignature());
        NoteLocation.setMeasureLength(preset.getTimeSignature().numinator);
        generatorHelper.populateMeta(composition, preset);
        generatorHelper.populateHarmonyRhythm(composition.getHarmonyTrack(), preset, composition.getMeasureCount());
        generatorHelper.populateMelodyRhythm(composition.getMelodyTrack(), preset, composition.getMeasureCount());
        generatorHelper.populateHarmonyChords(composition.getHarmonyTrack(), preset);
        generatorHelper.populateMelodyPitch(composition.getMelodyTrack(), preset);
        return composition;
    }
}
