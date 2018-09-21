package com.muzach.preset;

import com.muzach.music.Scale;
import com.muzach.music.TimeSignature;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PresetTest {
    @Test
    public void presetTest() {
        String name = "name";
        String description = "description";
        TimeSignature timeSignature = new TimeSignature(4, 4);
        double noteValues = 50.0;
        double pauseFrequency = 25.0;
        Scale scale = new Scale("scale", new ArrayList<>(), new ArrayList<>());
        double pitchJumps = 75.0;
        double chordChangeFrequency = 80.0;
        double chordColor = 10.0;

        Preset preset = new Preset(name, description, timeSignature, noteValues, pauseFrequency, scale, pitchJumps, chordChangeFrequency, chordColor);

        assertEquals(name, preset.getName());
        assertEquals(description, preset.getDescription());
        assertEquals(timeSignature, preset.getTimeSignature());
        assertEquals(noteValues, preset.getNoteValues(), 0.001);
        assertEquals(pauseFrequency, preset.getPauseFrequency(), 0.001);
        assertEquals(scale, preset.getScale());
        assertEquals(pitchJumps, preset.getPitchJumps(), 0.001);
        assertEquals(chordChangeFrequency, preset.getChordChangeFrequency(), 0.001);
        assertEquals(chordColor, preset.getChordColor(), 0.001);
    }
}
