package com.muzach.midi.note;

import com.muzach.midi.SequenceBuilder;

public class NoteLocation {
    private static int measureLength = 4;

    private int measureNumber;
    private int beatsInMeasure;
    private NoteDuration.Duration resolution;

    private NoteLocation(){}

    public static NoteLocation getNoteLocation(int measureNumber, int beatsInMeasure, NoteDuration.Duration resolution) {
        NoteLocation nl = new NoteLocation();
        nl.measureNumber = measureNumber;
        nl.beatsInMeasure = beatsInMeasure;
        nl.resolution = resolution;
        return nl;
    }

    public static void setMeasureLength(int measureLength) {
        NoteLocation.measureLength = measureLength;
    }

    public int getMeasureNumber() {
        return measureNumber;
    }

    public int getBeatsInMeasure() {
        return beatsInMeasure;
    }

    public int getMeasureLength() {
        return measureLength;
    }

    public NoteDuration.Duration getResolution() {
        return resolution;
    }

    public int getTick() {
        return 1 + ((measureNumber - 1) * SequenceBuilder.TICKS_PER_QUARTER_NOTE * measureLength) + (beatsInMeasure * NoteDuration.getTickCount(resolution));
    }

    public int getTSPosition() {
        return (getTick()/SequenceBuilder.TICKS_PER_QUARTER_NOTE)*NoteDuration.getTsCount(NoteDuration.Duration.QUARTER);
    }
}
