package com.muzach.midi.note;

import com.muzach.midi.SequenceBuilder;

public class NoteLocation {
    private static int measureLength = 4;

    private int measureNumber;
    private int tsPosition;

    private NoteLocation() {}

    public static NoteLocation getNoteLocation(int measureNumber, int beatsInMeasure, NoteDuration.Duration resolution) {
        NoteLocation nl = new NoteLocation();
        nl.tsPosition = (beatsInMeasure - 1) * NoteDuration.getTsCount(resolution);
        nl.measureNumber = measureNumber - 1;
        return nl;
    }

    public static NoteLocation getNoteLocation(int tsPosition) {
        int tsInMeasureCount = measureLength*NoteDuration.getTsCount(NoteDuration.Duration.QUARTER);

        NoteLocation location = new NoteLocation();
        location.measureNumber = tsPosition/tsInMeasureCount;
        location.tsPosition = tsPosition - location.measureNumber*tsInMeasureCount;
        return location;
    }

    public static void setMeasureLength(int measureLength) {
        NoteLocation.measureLength = measureLength;
    }

    public int getMeasureNumber() {
        return measureNumber;
    }

    public int getMeasureLength() {
        return measureLength;
    }

    public int getTick() {
        return 1 + (measureNumber * SequenceBuilder.TICKS_PER_QUARTER_NOTE * measureLength) + tsPosition * SequenceBuilder.TICKS_PER_QUARTER_NOTE / 8;
    }

    public int getTSPosition() {
        return measureNumber*measureLength*NoteDuration.getTsCount(NoteDuration.Duration.QUARTER) + this.tsPosition;
    }
}
