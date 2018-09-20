package com.muzach.music;

import java.io.Serializable;
import java.util.List;

public class Scale implements Serializable {
    private String name;
    private List<NotePitch> notePitches;
    private List<NotePitch> keyNotes;

    public Scale(String name, List<NotePitch> notePitches, List<NotePitch> keyNotes) {
        this.name = name;
        this.notePitches = notePitches;
        this.keyNotes = keyNotes;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NotePitch> getNotePitches() {
        return notePitches;
    }

    public List<NotePitch> getKeyNotes() {
        return keyNotes;
    }
}
