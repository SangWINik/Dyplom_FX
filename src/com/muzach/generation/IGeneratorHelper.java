package com.muzach.generation;

import com.muzach.preset.Preset;

public interface IGeneratorHelper {
    void populateMeta(Composition composition, Preset preset);
    void populateMelodyRhythm(Track melodyTrack, Preset preset, int measureCount);
    void populateMelodyPitch(Track melodyTrack, Preset preset);
    void populateHarmonyRhythm(Track harmonyTrack, Preset preset, int measureCount);
    void populateHarmonyChords(Track harmonyTrack, Preset preset);
}
