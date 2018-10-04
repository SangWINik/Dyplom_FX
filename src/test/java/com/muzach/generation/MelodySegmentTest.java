package com.muzach.generation;

import com.muzach.music.Note;
import com.muzach.music.NoteDuration;
import com.muzach.music.NoteLocation;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class MelodySegmentTest {
    @Test
    public void melodySegmentTest() {
        Map<NoteLocation, NoteDuration.Duration> notes = new HashMap<>();
        notes.put(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.EIGHTH), NoteDuration.Duration.EIGHTH);
        notes.put(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.EIGHTH), NoteDuration.Duration.EIGHTH);
        notes.put(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.QUARTER), NoteDuration.Duration.QUARTER);
        notes.put(NoteLocation.getNoteLocation(1, 3, NoteDuration.Duration.QUARTER), NoteDuration.Duration.QUARTER);

        MelodySegment segment = new MelodySegment(NoteDuration.Duration.HALF, notes);

        assertEquals(3, segment.getNotes().size());
    }

    @Test
    public void getValueRateTest() {
        Map<NoteLocation, NoteDuration.Duration> notes = new HashMap<>();
        notes.put(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.EIGHTH), NoteDuration.Duration.EIGHTH);
        notes.put(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.EIGHTH), NoteDuration.Duration.EIGHTH);
        notes.put(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.QUARTER), NoteDuration.Duration.QUARTER);
        notes.put(NoteLocation.getNoteLocation(1, 3, NoteDuration.Duration.QUARTER), NoteDuration.Duration.QUARTER);
        MelodySegment segment = new MelodySegment(NoteDuration.Duration.HALF, notes);

        double actualValueRate = segment.getValueRate();

        assertEquals(34.0, actualValueRate, 0.0001);
    }

    @Test
    public void getValueRateNotesEmptyTest() {
        Map<NoteLocation, NoteDuration.Duration> notes = new HashMap<>();
        MelodySegment segment = new MelodySegment(NoteDuration.Duration.HALF, notes);

        double actualValueRate = segment.getValueRate();

        assertEquals(50.0, actualValueRate, 0.0001);
    }

    @Test
    public void getPauseRateTest() {
        Map<NoteLocation, NoteDuration.Duration> notes = new HashMap<>();
        notes.put(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.EIGHTH), NoteDuration.Duration.EIGHTH);
        notes.put(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.QUARTER), NoteDuration.Duration.QUARTER);
        notes.put(NoteLocation.getNoteLocation(1, 3, NoteDuration.Duration.QUARTER), NoteDuration.Duration.QUARTER);
        MelodySegment segment = new MelodySegment(NoteDuration.Duration.HALF, notes);

        double actualPauseRate = segment.getPauseRate();

        assertEquals(25.75, actualPauseRate, 0.0001);
    }

    @Test
    public void getPauseRateNoPausesTest() {
        Map<NoteLocation, NoteDuration.Duration> notes = new HashMap<>();
        notes.put(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.EIGHTH), NoteDuration.Duration.EIGHTH);
        notes.put(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.EIGHTH), NoteDuration.Duration.EIGHTH);
        notes.put(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.QUARTER), NoteDuration.Duration.QUARTER);
        notes.put(NoteLocation.getNoteLocation(1, 3, NoteDuration.Duration.QUARTER), NoteDuration.Duration.QUARTER);
        MelodySegment segment = new MelodySegment(NoteDuration.Duration.HALF, notes);

        double actualPauseRate = segment.getPauseRate();

        assertEquals(1.0, actualPauseRate, 0.0001);
    }

    @Test
    public void getNotesTest() {
        Map<NoteLocation, NoteDuration.Duration> notes = new HashMap<>();
        notes.put(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.EIGHTH), NoteDuration.Duration.EIGHTH);
        notes.put(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.EIGHTH), NoteDuration.Duration.EIGHTH);
        notes.put(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.QUARTER), NoteDuration.Duration.QUARTER);
        notes.put(NoteLocation.getNoteLocation(1, 3, NoteDuration.Duration.QUARTER), NoteDuration.Duration.QUARTER);
        MelodySegment segment = new MelodySegment(NoteDuration.Duration.HALF, notes);

        List<Note> actualNotes = segment.getNotes(NoteLocation.getNoteLocation(10));

        assertEquals(3, segment.getNotes().size());
    }
}
