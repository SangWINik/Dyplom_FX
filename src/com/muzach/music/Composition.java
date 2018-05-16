package com.muzach.music;

import java.util.List;

public class Composition {
    private TimeSignature timeSignature;
    private int tempoBMP;
    private List<Track> tracks;

    public TimeSignature getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(TimeSignature timeSignature) {
        this.timeSignature = timeSignature;
    }

    public int getTempoBMP() {
        return tempoBMP;
    }

    public void setTempoBMP(int tempoBMP) {
        this.tempoBMP = tempoBMP;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
