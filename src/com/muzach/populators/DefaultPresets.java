package com.muzach.populators;

import com.muzach.generation.Preset;
import com.muzach.music.Scale;
import com.muzach.music.TimeSignature;

import java.util.ArrayList;
import java.util.List;

public class DefaultPresets {
    private static List<Preset> defaultPresets;

    static {
        defaultPresets = new ArrayList<>();

        defaultPresets.add(new Preset("Happy tune", "Happy careless music that has a little bit of dreamy feeling to it.", new TimeSignature(4, 4), 60, 20, Scales.getByName("Major Scale"), 35, 50, 30));
        defaultPresets.add(new Preset("Sad tune", "Slow and sad music.", new TimeSignature(4, 4), 100, 20, Scales.getByName("Minor Scale"), 15, 20, 10));
        defaultPresets.add(new Preset("Dramatic tune", "Emotional and dramatic straightforward music.", new TimeSignature(4, 4), 50, 60, Scales.getByName("Harmonic Minor Scale"), 50, 50, 1));
    }

    public static List<Preset> get() {
        return defaultPresets;
    }
}
