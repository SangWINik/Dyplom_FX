package com.muzach.music;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NoteTest {
    @Test
    public void noteTest() {
        NotePitch pitch = NotePitch.C1;
        NoteDuration.Duration duration = NoteDuration.Duration.HALF;
        NoteLocation location = NoteLocation.getNoteLocation(14);
        int velocity = 75;

        Note note = new Note(pitch, duration, location, velocity);

        assertEquals(pitch, note.getPitch());
        assertEquals(duration, note.getDuration());
        assertEquals(location, note.getLocation());
        assertEquals(velocity, note.getVelocity());
    }
}
