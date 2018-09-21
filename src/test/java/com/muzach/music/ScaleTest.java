package com.muzach.music;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScaleTest {
    @Test
    public void scaleTest() {
        String name = "scale";
        List<NotePitch> pitches = new ArrayList<>();
        List<NotePitch> keyNotes = new ArrayList<>();

        Scale scale = new Scale(name, pitches, keyNotes);

        assertEquals(name, scale.getName());
        assertEquals(pitches, scale.getNotePitches());
        assertEquals(keyNotes, scale.getKeyNotes());
    }
}