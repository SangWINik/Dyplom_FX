package com.muzach.utils.populators;

import com.muzach.preset.Preset;
import com.muzach.music.TimeSignature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultPresets {
    private static List<Preset> defaultPresets;

    static {
        defaultPresets = new ArrayList<>();

        defaultPresets.add(new Preset("Happy tune", "Happy careless music that has a little bit of dreamy feeling to it.", new TimeSignature(4, 4), 50, 25, Scales.getByName("Major Scale"), 35, 50, 30, 80, 120));
        defaultPresets.add(new Preset("Sad tune", "Slow and sad music.", new TimeSignature(4, 4), 100, 20, Scales.getByName("Minor Scale"), 35, 20, 10, 60, 90));
        defaultPresets.add(new Preset("Dramatic tune", "Emotional and dramatic straightforward music.", new TimeSignature(4, 4), 50, 60, Scales.getByName("Harmonic Minor Scale"), 50, 50, 1, 80, 100));
        defaultPresets.add(new Preset("Catchy tune", "Nice and catchy melody.", new TimeSignature(4, 4), 50, 25, Scales.getByName("Dorian Mode"), 50, 50, 1, 70, 100));
        defaultPresets.add(new Preset("Weird tune", "Unusual rhythm with a strange melody from another world.", new TimeSignature(7, 4), 50, 25, Scales.getByName("Lydian Mode"), 50, 50, 1, 70, 120));
    }

    public static List<Preset> get() {
        return defaultPresets;
    }
}
