package com.muzach.music;

public class Composition {
    private TimeSignature timeSignature;
    private int tempoBMP;
    private Track melodyTrack;
    private Track harmonyTrack;

    public Composition(){
        timeSignature = new TimeSignature(4, 4);
        tempoBMP = 120;
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
