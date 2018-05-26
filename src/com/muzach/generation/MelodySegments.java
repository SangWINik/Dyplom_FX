package com.muzach.generation;

import com.muzach.midi.note.NoteDuration;
import com.muzach.midi.note.NoteLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MelodySegments {
    private static List<MelodySegment> melodySegments;

    static {
        melodySegments = new ArrayList<>();

        //quarter segments
        Map<NoteLocation, NoteDuration.Duration> notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.QUARTER), NoteDuration.Duration.QUARTER)
        );
        MelodySegment segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.EIGHTH), NoteDuration.Duration.EIGHTH),
                Map.entry(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.EIGHTH), NoteDuration.Duration.EIGHTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH),
                Map.entry(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH),
                Map.entry(NoteLocation.getNoteLocation(1, 3, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.EIGHTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.EIGHTH),
                Map.entry(NoteLocation.getNoteLocation(1, 3, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH),
                Map.entry(NoteLocation.getNoteLocation(1, 4, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH),
                Map.entry(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH),
                Map.entry(NoteLocation.getNoteLocation(1, 3, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH),
                Map.entry(NoteLocation.getNoteLocation(1, 4, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                //pause
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.EIGHTH), NoteDuration.Duration.EIGHTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.EIGHTH), NoteDuration.Duration.EIGHTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.EIGHTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.EIGHTH),
                Map.entry(NoteLocation.getNoteLocation(1, 3, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.EIGHTH),
                Map.entry(NoteLocation.getNoteLocation(1, 4, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH),
                Map.entry(NoteLocation.getNoteLocation(1, 3, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.EIGHTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH),
                Map.entry(NoteLocation.getNoteLocation(1, 3, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.EIGHTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH),
                Map.entry(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.EIGHTH),
                Map.entry(NoteLocation.getNoteLocation(1, 4, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 3, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 4, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH),
                Map.entry(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH),
                Map.entry(NoteLocation.getNoteLocation(1, 3, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH),
                Map.entry(NoteLocation.getNoteLocation(1, 4, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH),
                Map.entry(NoteLocation.getNoteLocation(1, 3, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH),
                Map.entry(NoteLocation.getNoteLocation(1, 4, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 3, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH),
                Map.entry(NoteLocation.getNoteLocation(1, 4, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH),
                Map.entry(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH),
                Map.entry(NoteLocation.getNoteLocation(1, 3, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 2, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH),
                Map.entry(NoteLocation.getNoteLocation(1, 3, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH),
                Map.entry(NoteLocation.getNoteLocation(1, 4, NoteDuration.Duration.SIXTEENTH), NoteDuration.Duration.SIXTEENTH)
        );
        segment = new MelodySegment(NoteDuration.Duration.QUARTER, notes);
        melodySegments.add(segment);

        //half segments
        notes = Map.ofEntries(
                Map.entry(NoteLocation.getNoteLocation(1, 1, NoteDuration.Duration.HALF), NoteDuration.Duration.HALF)
        );
        segment = new MelodySegment(NoteDuration.Duration.HALF, notes);
        melodySegments.add(segment);

        notes = Map.ofEntries(
                //pause
        );
        segment = new MelodySegment(NoteDuration.Duration.HALF, notes);
        melodySegments.add(segment);
    }

    public static List<MelodySegment> get(boolean hasFirstBeat, boolean halfAllowed) {
        List<MelodySegment> res = melodySegments;
        if (hasFirstBeat) {
            res = res.stream().filter(s -> s.getNotes().stream().filter(note -> note.getLocation().getTSPosition() == 0).findFirst().isPresent()).collect(Collectors.toList());
        }
        if (!halfAllowed) {
            res = res.stream().filter(s -> s.getDuration() != NoteDuration.Duration.HALF).collect(Collectors.toList());
        }
        return res;
    }
}
