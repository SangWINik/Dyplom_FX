package com.muzach.generation;

import com.muzach.music.TimeSignature;

public class Composition {
    private int measureCount;
    private TimeSignature timeSignature;
    private int tempoBMP;
    private Track melodyTrack;
    private Track harmonyTrack;

    public Composition(){
        timeSignature = new TimeSignature(4, 4);
        tempoBMP = 120;
        melodyTrack = new Track();
        harmonyTrack = new Track();
    }

    public int getMeasureCount() {
        return measureCount;
    }

    public void setMeasureCount(int measureCount) {
        this.measureCount = measureCount;
    }

    public TimeSignature getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(TimeSignature timeSignature) {
        this.timeSignature = timeSignature;
    }

    public Track getMelodyTrack() {
        return melodyTrack;
    }

    public void setMelodyTrack(Track melodyTrack) {
        this.melodyTrack = melodyTrack;
    }

    public Track getHarmonyTrack() {
        return harmonyTrack;
    }

    public void setHarmonyTrack(Track harmonyTrack) {
        this.harmonyTrack = harmonyTrack;
    }

    public int getTempoBMP() {
        return tempoBMP;
    }

    public void setTempoBMP(int tempoBMP) {
        this.tempoBMP = tempoBMP;
    }

}
