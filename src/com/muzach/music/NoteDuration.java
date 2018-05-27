package com.muzach.music;


import com.muzach.midi.SequenceBuilder;

import java.util.EnumMap;

import static com.muzach.music.NoteDuration.Duration.*;

public class NoteDuration {

    public enum Duration {
        WHOLE, HALF, QUARTER, EIGHTH, SIXTEENTH, THIRTYSECOND
    }

    private static EnumMap<Duration, Integer> durationMap;
    private static EnumMap<Duration, Integer> tsCount;

    static {
        int quarterNoteDuration = SequenceBuilder.TICKS_PER_QUARTER_NOTE;
        durationMap = new EnumMap<Duration, Integer>(Duration.class);
        durationMap.put(WHOLE, quarterNoteDuration*4);
        durationMap.put(HALF, quarterNoteDuration*2);
        durationMap.put(QUARTER, quarterNoteDuration);
        durationMap.put(EIGHTH, quarterNoteDuration / 2);
        durationMap.put(SIXTEENTH, quarterNoteDuration / 4);
        durationMap.put(THIRTYSECOND, quarterNoteDuration / 8);

        tsCount = new EnumMap<Duration, Integer>(Duration.class);
        tsCount.put(THIRTYSECOND, 1);
        tsCount.put(SIXTEENTH, 2);
        tsCount.put(EIGHTH, 4);
        tsCount.put(QUARTER, 8);
        tsCount.put(HALF, 16);
        tsCount.put(WHOLE, 32);
    }

    public static int getTickCount(Duration duration) {
        return durationMap.get(duration);
    }
    public static int getTsCount(Duration duration) {
        return tsCount.get(duration);
    }
}

