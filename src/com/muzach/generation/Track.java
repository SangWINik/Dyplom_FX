package com.muzach.generation;

import com.muzach.music.Note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Track {
    private List<Note> notes;
    private int instrument;

    private boolean isMuted = false;

    public Track(){
        notes = new ArrayList<>();
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void mute(){
        this.isMuted = true;
    }

    public void unmute(){
        this.isMuted = false;
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public int getInstrument() {
        return instrument;
    }

    public void setInstrument(int instrument) {
        this.instrument = instrument;
    }
}
