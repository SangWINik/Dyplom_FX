package com.muzach.music;

import com.muzach.midi.note.NoteDuration;
import com.muzach.midi.note.NoteLocation;

public class Note {
    private NotePitch pitch;
    private int velocity = 100;
    private NoteDuration.Duration duration;
    private NoteLocation location;

    public Note(){}

    public Note(NotePitch pitch, NoteDuration.Duration duration, NoteLocation location, int velocity) {
        this.pitch = pitch;
        this.velocity = velocity;
        this.duration = duration;
        this.location = location;
    }

    public NotePitch getPitch() {
        return pitch;
    }

    public void setPitch(NotePitch pitch) {
        this.pitch = pitch;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public NoteDuration.Duration getDuration() {
        return duration;
    }

    public void setDuration(NoteDuration.Duration duration) {
        this.duration = duration;
    }

    public NoteLocation getLocation() {
        return location;
    }

    public void setLocation(NoteLocation location) {
        this.location = location;
    }
}
