package com.muzach.generation;

import com.muzach.music.Track;

public interface IGeneratorHelper {
    void populateMelodyRythm(Track melodyTrack, Preset preset, int measureCount);
    void populateMelodyPitch(Track melodyTrack, Preset preset);
    void populateHarmonyRythm(Track harmonyTrack,  Preset preset, int measureCount);
    void populateHarmonyChords(Track harmonyTrack, Preset preset);
}
