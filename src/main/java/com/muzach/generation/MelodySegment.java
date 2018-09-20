package com.muzach.generation;

import com.muzach.music.NoteDuration;
import com.muzach.music.NoteLocation;
import com.muzach.music.Note;
import com.muzach.music.NotePitch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MelodySegment {

    private NoteDuration.Duration duration;
    private List<Note> notes;

    public MelodySegment(NoteDuration.Duration duration, Map<NoteLocation, NoteDuration.Duration> notes) {
        this.notes = new ArrayList<>();
        this.duration = duration;
        for (Map.Entry<NoteLocation, NoteDuration.Duration> n : notes.entrySet()) {
            Note note = new Note(NotePitch.C1, n.getValue(), n.getKey(), 100);
            if (note.getLocation().getTSPosition() + NoteDuration.getTsCount(note.getDuration()) <= NoteDuration.getTsCount(duration)) {
                this.notes.add(note);
            }
        }
    }

    public double getValueRate() {
        if (notes.isEmpty()){
            return 50;
        }
        double max = NoteDuration.getTsCount(duration);
        double rate = 99 / max;
        double average = 0;
        for (Note note : notes) {
            average += NoteDuration.getTsCount(note.getDuration());
        }
        if (notes.size() != 0) {
            average /= notes.size();
        }
        average = average * rate + 1;
        return average;
    }

    public double getPauseRate() {
        //assuming notes don't intersect
        int durationsSum = 0;
        for (Note note: notes) {
            durationsSum += NoteDuration.getTsCount(note.getDuration());
        }
        double pausesSum = NoteDuration.getTsCount(duration) - durationsSum;
        double pausesRate = pausesSum/NoteDuration.getTsCount(duration);
        return 99*pausesRate + 1;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public List<Note> getNotes(NoteLocation segmentLocation) {
        List<Note> correctedNotes = new ArrayList<>();
        for (Note note : notes) {
            Note newNote = new Note(note.getPitch(), note.getDuration(), NoteLocation.getNoteLocation(segmentLocation.getTSPosition() + note.getLocation().getTSPosition()), note.getVelocity());
            correctedNotes.add(newNote);
        }
        return correctedNotes;
    }

    public NoteDuration.Duration getDuration() {
        return duration;
    }
}
